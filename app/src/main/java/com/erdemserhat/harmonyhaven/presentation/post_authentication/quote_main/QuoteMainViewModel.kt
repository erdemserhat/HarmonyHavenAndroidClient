package com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erdemserhat.harmonyhaven.data.local.repository.QuoteRepository
import com.erdemserhat.harmonyhaven.domain.ErrorTraceFlags
import com.erdemserhat.harmonyhaven.domain.usecase.quote.QuoteUseCases
import com.erdemserhat.harmonyhaven.domain.usecase.user.UserUseCases
import com.erdemserhat.harmonyhaven.dto.responses.Quote
import com.google.android.gms.tasks.Task
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.ktx.messaging
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import com.erdemserhat.harmonyhaven.domain.model.rest.FilteredQuoteRequest
import com.erdemserhat.harmonyhaven.dto.responses.QuoteForOrderModel
import com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.generic_card.CategorySelectionModel
import com.erdemserhat.harmonyhaven.di.SessionManager

import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class QuoteMainViewModel @Inject constructor(
    private val quoteUseCases: QuoteUseCases,
    @ApplicationContext private val context: Context,
    private val userUseCases: UserUseCases,
    private val sessionManager: SessionManager,
    @Named("CategorySelection") private val categoryPreferences: SharedPreferences,
    @Named("Permission") private val permissionPreferences: SharedPreferences,
    @Named("UserTutorial") private val userTutorialPreferences: SharedPreferences,
    private var quoteRepository: QuoteRepository
) : ViewModel() {
    private var _shouldScrollToStart = mutableStateOf(false)
    val shouldScrollToStart:State<Boolean> = _shouldScrollToStart

    private val _quotes =
        MutableStateFlow<Set<Quote>>(mutableSetOf())
    val quotes: StateFlow<Set<Quote>> = _quotes


    //user tutorial
    private val _shouldShowUxDialog1 = MutableStateFlow(true)
    private val _shouldShowUxDialog2 = MutableStateFlow(true)
    val shouldShowUxDialog1: StateFlow<Boolean> = _shouldShowUxDialog1
    private var _isRefreshing= MutableStateFlow(false)

    val isRefreshing: StateFlow<Boolean> = _isRefreshing

    var isLikedListEmpty = MutableStateFlow(true)
    private var _selectedQuote = mutableStateOf(QuoteForOrderModel())
    private val _authState = MutableStateFlow(1)

    //For pagination
    private val _page = MutableStateFlow(1)
    private val _pageSize = 21
    private var _seed = sessionManager.getSeed()


    init {
        tryLoad()
        updateFcmToken()
    }

    fun updateSelectedQuote(selectedQuoteArg: QuoteForOrderModel) {
        _selectedQuote.value = selectedQuoteArg
    }

    private fun tryLoad() {
        checkConnection()
        prepareList()
        initializeScrollTutorial()


    }

    fun shouldScrollToStart(){
        _shouldScrollToStart.value = true
    }

    fun scrolledToStart(){
        _shouldScrollToStart.value = false
    }


    private fun checkConnection() {
        try {
            viewModelScope.launch {

                val authStatus = async {
                    userUseCases.checkUserAuthenticationStatus.executeRequest()
                }
                _authState.value = authStatus.await()
                Log.d("AuthStats", _authState.value.toString())


            }
        } catch (e: Exception) {
            Log.d(ErrorTraceFlags.POST_FLOW_TRACE.flagName, e.message.toString())
        }


    }

    fun deleteQuoteById(id: Int) {
        try {
            viewModelScope.launch {
                quoteUseCases.deleteQuoteById.executeRequest(id)
            }
        } catch (e: Exception) {
            Log.d(ErrorTraceFlags.POST_FLOW_TRACE.flagName, e.message.toString())

        }

    }

    fun likeQuote(quoteId: Int) {
        viewModelScope.launch {
            try {
                quoteUseCases.likeQuote.executeRequest(quoteId)

                launch(Dispatchers.IO) {
                    val a = quoteRepository.updateLikeStatusIfQuoteExist(quoteId,true)
                    Log.d(ErrorTraceFlags.POST_DETAIL_TRACE.flagName,a.toString())


                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }

    fun removeLikeQuote(quoteId: Int) {
        viewModelScope.launch {
            try {
                quoteUseCases.removeLike.executeRequest(quoteId)
                launch(Dispatchers.IO) {
                    val a = quoteRepository.updateLikeStatusIfQuoteExist(quoteId,false)
                    Log.d(ErrorTraceFlags.POST_DETAIL_TRACE.flagName,a.toString())
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }


    fun loadCategorizedQuotes(selectedCategories: CategorySelectionModel) {
        viewModelScope.launch {
            try {
                _quotes.value = setOf()
                sessionManager.resetSeed()
                _seed = sessionManager.getSeed()
                _page.value = 1
                val categories = selectedCategories.convertToIdListModel()
                //load quotes with pagination from default page
                val requestedQuotes = quoteUseCases.getQuote.executeRequest2(
                    filteredQuoteRequest = FilteredQuoteRequest(
                        categories = categories,
                        page = _page.value,
                        pageSize = _pageSize,
                        seed = _seed
                    )
                )

                _quotes.value = requestedQuotes.toSet()

                withContext(Dispatchers.IO) {
                    quoteRepository.clearCachedQuotes()
                    quoteRepository.addCachedQuotes(
                        requestedQuotes.takeLast(4).map { it.convertToEntity() })

                }
                checkLikedList()

            } catch (_: Exception) {

            }

        }


    }

    fun loadMoreQuote() {
        viewModelScope.launch {
            try {
                _page.value++
                val categories = getCategorySelection().convertToIdListModel()
                //load quotes with pagination from default page

                val requestedQuotes = quoteUseCases.getQuote.executeRequest2(
                    filteredQuoteRequest = FilteredQuoteRequest(
                        categories = categories,
                        page = _page.value,
                        pageSize = _pageSize,
                        seed = _seed
                    )
                )

                _quotes.value += requestedQuotes

                withContext(Dispatchers.IO) {
                    quoteRepository.clearCachedQuotes()
                    quoteRepository.addCachedQuotes(
                        requestedQuotes.takeLast(4).map { it.convertToEntity() })

                }

                Log.d(ErrorTraceFlags.POST_DETAIL_TRACE.flagName,"Loaded More")
                Log.d(ErrorTraceFlags.POST_DETAIL_TRACE.flagName,_quotes.value.map { it.id }.toString())

                checkLikedList()

            } catch (_: Exception) {

            }

        }


    }

    private fun checkLikedList() {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val categoryPreferences = getCategorySelection()
                if (categoryPreferences.isOnlyLikedSelected() && _quotes.value.isEmpty()) {
                    withContext(Dispatchers.Main) {
                        _quotes.value = setOf(
                            Quote(
                                quote = "Beğendiğiniz Herhangi Bir Gönderi Bulunmuyor",
                                isLiked = false,
                                quoteCategory = -1
                            )
                        )
                    }


                }


            }

        } catch (e: Exception) {

        }

    }

     fun refreshList() {
        try {
            _isRefreshing.value = true
            Log.d(ErrorTraceFlags.POST_DETAIL_TRACE.flagName,"refreshList()  List Called")

            viewModelScope.launch {
                withContext(Dispatchers.IO){
                    try {
                        _seed = sessionManager.randomizeSeed()
                        val categories = getCategorySelection().convertToIdListModel()
                        val requestedQuotes = quoteUseCases.getQuote.executeRequest2(
                            filteredQuoteRequest = FilteredQuoteRequest(
                                categories = categories,
                                page = _page.value,
                                pageSize = _pageSize,
                                seed = _seed
                            )
                        )




                        _quotes.value = requestedQuotes.toSet()
                        checkLikedList()





                        quoteRepository.clearCachedQuotes()
                        quoteRepository.addCachedQuotes(
                            requestedQuotes.takeLast(4).map { it.convertToEntity() }
                        )
                        Log.d(ErrorTraceFlags.POST_DETAIL_TRACE.flagName,"Cached Updated")
                        _isRefreshing.value = false


                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }



            }

        } catch (e: Exception) {

        }
    }






    //load notification via offset
    private fun prepareList() {
        try {
            val idList = _quotes.value.map { it.id }.toString()
            Log.d(ErrorTraceFlags.POST_DETAIL_TRACE.flagName,"Prepare List Called")
            Log.d(ErrorTraceFlags.POST_DETAIL_TRACE.flagName,idList.toString())

            viewModelScope.launch {
                withContext(Dispatchers.IO){
                    try {
                        val cachedQuotes = quoteRepository.getCachedQuotes()
                        if(cachedQuotes.isNotEmpty())
                            _quotes.value = cachedQuotes.map { it.convertToQuote() }.toSet()

                        Log.d(ErrorTraceFlags.POST_DETAIL_TRACE.flagName,"Cached Quotes Loaded")
                        Log.d(ErrorTraceFlags.POST_DETAIL_TRACE.flagName,_quotes.value.map { it.id }.toString())

                        val categories = getCategorySelection().convertToIdListModel()
                        val requestedQuotes = quoteUseCases.getQuote.executeRequest2(
                            filteredQuoteRequest = FilteredQuoteRequest(
                                categories = categories,
                                page = _page.value,
                                pageSize = _pageSize,
                                seed = _seed
                            )
                        )

                        Log.d(ErrorTraceFlags.POST_DETAIL_TRACE.flagName,"Requested First Page")
                        Log.d(ErrorTraceFlags.POST_DETAIL_TRACE.flagName,"Requested First Page :${requestedQuotes.map { it.id }.toString()}")
                        Log.d(ErrorTraceFlags.POST_DETAIL_TRACE.flagName,_quotes.value.map { it.id }.toString())



                        _quotes.value += requestedQuotes
                        checkLikedList()
                        Log.d(ErrorTraceFlags.POST_DETAIL_TRACE.flagName,"First Call Completed")
                        Log.d(ErrorTraceFlags.POST_DETAIL_TRACE.flagName,_quotes.value.map { it.id }.toString())





                        quoteRepository.clearCachedQuotes()
                        quoteRepository.addCachedQuotes(
                            requestedQuotes.takeLast(4).map { it.convertToEntity() }
                        )
                        Log.d(ErrorTraceFlags.POST_DETAIL_TRACE.flagName,"Cached Updated")
                        Log.d(ErrorTraceFlags.POST_DETAIL_TRACE.flagName,quoteRepository.getCachedQuotes().map { it.quoteId }.toString())


                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }



            }

        } catch (e: Exception) {

        }
    }


    private fun initializeScrollTutorial() {
        val showDialog = userTutorialPreferences.getBoolean("shouldShowScrollTutorial", true)
        _shouldShowUxDialog1.value = showDialog
    }


    fun markScrollTutorialDone(show: Boolean) {
        userTutorialPreferences.edit().putBoolean("shouldShowScrollTutorial", show).apply()
        initializeScrollTutorial()
    }


    fun saveCategorySelection(model: CategorySelectionModel) {
        try {
            categoryPreferences.edit().apply {
                val selectionMap = mapOf(
                    "isGeneralSelected" to model.isGeneralSelected,
                    "isLikedSelected" to model.isLikedSelected,
                    "isBeYourselfSelected" to model.isBeYourselfSelected,
                    "isConfidenceSelected" to model.isConfidenceSelected,
                    "isSelfImprovementSelected" to model.isSelfImprovementSelected,
                    "isLifeSelected" to model.isLifeSelected,
                    "isStrengthSelected" to model.isStrengthSelected,
                    "isPositivitySelected" to model.isPositivitySelected,
                    "isAnxietySelected" to model.isAnxietySelected,
                    "isSelfEsteemSelected" to model.isSelfEsteemSelected,
                    "isSadnessSelected" to model.isSadnessSelected,
                    "isWorkSelected" to model.isWorkSelected,
                    "isToxicRelationshipsSelected" to model.isToxicRelationshipsSelected,
                    "isContinuingLifeSelected" to model.isContinuingLifeSelected,
                    "isSeparationSelected" to model.isSeparationSelected,
                    "isCourageSelected" to model.isCourageSelected,
                    "isSportSelected" to model.isSportSelected,
                    "isLoveSelected" to model.isLoveSelected,
                    "isShortVideosSelected" to model.isShortVideosSelected
                )
                selectionMap.forEach { (key, value) -> putBoolean(key, value) }
                apply()
            }

        } catch (e: Exception) {

        }
    }

    fun getCategorySelection(): CategorySelectionModel {

        val defaultValues = CategorySelectionModel() // Use the default values from the data class

        return CategorySelectionModel(
            isGeneralSelected = categoryPreferences.getBoolean(
                "isGeneralSelected",
                defaultValues.isGeneralSelected
            ),
            isLikedSelected = categoryPreferences.getBoolean(
                "isLikedSelected",
                defaultValues.isLikedSelected
            ),
            isBeYourselfSelected = categoryPreferences.getBoolean(
                "isBeYourselfSelected",
                defaultValues.isBeYourselfSelected
            ),
            isConfidenceSelected = categoryPreferences.getBoolean(
                "isConfidenceSelected",
                defaultValues.isConfidenceSelected
            ),
            isSelfImprovementSelected = categoryPreferences.getBoolean(
                "isSelfImprovementSelected",
                defaultValues.isSelfImprovementSelected
            ),
            isLifeSelected = categoryPreferences.getBoolean(
                "isLifeSelected",
                defaultValues.isLifeSelected
            ),
            isStrengthSelected = categoryPreferences.getBoolean(
                "isStrengthSelected",
                defaultValues.isStrengthSelected
            ),
            isPositivitySelected = categoryPreferences.getBoolean(
                "isPositivitySelected",
                defaultValues.isPositivitySelected
            ),
            isAnxietySelected = categoryPreferences.getBoolean(
                "isAnxietySelected",
                defaultValues.isAnxietySelected
            ),
            isSelfEsteemSelected = categoryPreferences.getBoolean(
                "isSelfEsteemSelected",
                defaultValues.isSelfEsteemSelected
            ),
            isSadnessSelected = categoryPreferences.getBoolean(
                "isSadnessSelected",
                defaultValues.isSadnessSelected
            ),
            isContinuingLifeSelected = categoryPreferences.getBoolean(
                "isContinuingLifeSelected",
                defaultValues.isContinuingLifeSelected
            ),
            isWorkSelected = categoryPreferences.getBoolean(
                "isWorkSelected",
                defaultValues.isWorkSelected
            ),
            isToxicRelationshipsSelected = categoryPreferences.getBoolean(
                "isToxicRelationshipsSelected",
                defaultValues.isToxicRelationshipsSelected
            ),
            isSeparationSelected = categoryPreferences.getBoolean(
                "isSeparationSelected",
                defaultValues.isSeparationSelected
            ),
            isCourageSelected = categoryPreferences.getBoolean(
                "isCourageSelected",
                defaultValues.isCourageSelected
            ),
            isSportSelected = categoryPreferences.getBoolean(
                "isSportSelected",
                defaultValues.isSportSelected
            ),
            isLoveSelected = categoryPreferences.getBoolean(
                "isLoveSelected",
                defaultValues.isLoveSelected
            ),
            isShortVideosSelected = categoryPreferences.getBoolean(
                "isShortVideosSelected",
                defaultValues.isShortVideosSelected
            )
        )
    }

    fun updatePermissionStatus(value: Boolean = false) {
        val key: String = "notificationPref"
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                permissionPreferences.edit().putBoolean(key, value).apply()
            }
        }
    }

    fun isPermissionGranted(): Boolean {
        val key: String = "notificationPref"
        return permissionPreferences.getBoolean(key, false)
    }


    private fun updateFcmToken() {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                FirebaseMessaging.getInstance().subscribeToTopic("everyone")
                    .addOnCompleteListener { task: Task<Void?> ->
                        Log.d("erdem", task.result.toString())
                        if (task.isSuccessful) {
                            Log.d("spec1", "Successfully subscribed to topic")
                        } else {
                            Log.e("spec1", "Failed to subscribe to topic", task.exception)
                        }
                    }
                val localToken = Firebase.messaging.token.await()
                //send your fcm id to server
                Log.d("erdem1212", localToken.toString())
                val fcmToken = localToken.toString()

                val response = userUseCases.fcmEnrolment.executeRequest(fcmToken)

                Log.d("fcmtestResults", response.message)


            }

        } catch (e: Exception) {

        }


    }


}