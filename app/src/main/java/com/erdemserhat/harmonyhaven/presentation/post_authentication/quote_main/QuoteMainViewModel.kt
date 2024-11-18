package com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class QuoteMainViewModel @Inject constructor(
    private val quoteUseCases: QuoteUseCases,
    @ApplicationContext private val context: Context,
    private val userUseCases: UserUseCases
) : ViewModel() {
    private val _quotes =
        MutableStateFlow<List<com.erdemserhat.harmonyhaven.dto.responses.Quote>>(emptyList())
    val quotes: StateFlow<List<com.erdemserhat.harmonyhaven.dto.responses.Quote>> = _quotes
    private var allQuotes: List<Quote> = emptyList()

    // StateFlow ile UX Dialog gösterecek mi
    private val _shouldShowUxDialog1 = MutableStateFlow(true)
    private val _shouldShowUxDialog2 = MutableStateFlow(true)
    var isLikedListEmpty = MutableStateFlow(true)
    val shouldShowUxDialog1: StateFlow<Boolean> = _shouldShowUxDialog1
    val shouldShowUxDialog2: StateFlow<Boolean> = _shouldShowUxDialog2


    private val _authState = MutableStateFlow(1)
    val authStatus = _authState
    //1 successfully
    //2 should re login
    //0 internet connection error


    init {
        tryLoad()
        updateFcmToken()
    }

    fun tryLoad() {
        loadQuotes()
        initializeUxDialogState()
        initializeUxDialogState2()
        checkConnection()
        isLikedListEmpty()


    }


    private fun checkConnection() {
        viewModelScope.launch {

            val authStatus = async {
                userUseCases.checkUserAuthenticationStatus.executeRequest()
            }
            _authState.value = authStatus.await()
            Log.d("AuthStats", _authState.value.toString())


        }

    }

    fun deleteQuoteById(id: Int) {
        viewModelScope.launch {
            try {
                quoteUseCases.deleteQuoteById.executeRequest(id)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }

    fun likeQuote(quoteId: Int) {
        viewModelScope.launch {
            try {
                markAsLikedInternally(quoteId)
                quoteUseCases.likeQuote.executeRequest(quoteId)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }

    fun removeLikeQuote(quoteId: Int) {
        viewModelScope.launch {
            try {
                removeLikedInternally(quoteId)
                quoteUseCases.removeLike.executeRequest(quoteId)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }


    fun getLikedQuotes() {
        viewModelScope.launch {
            try {
                val list = quoteUseCases.getLikedQuotes.executeRequest()
                Log.d("dsadsafgsdf", list.toString())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    fun filterQuotes(
        categorySelectionModel: CategorySelectionModel,
        shouldShuffle: Boolean = true
    ) {

        val filteredQuotes: MutableSet<Quote> = mutableSetOf()
        _quotes.value = filteredQuotes.toList()


        if (categorySelectionModel.isGeneralSelected) {
            _quotes.value = allQuotes
            return
        }

        if (categorySelectionModel.isBeYourselfSelected) {
            allQuotes.filter { it.quoteCategory == 2 }.forEach {
                filteredQuotes.add(it)
            }
        }

        // Diğer kategoriler için filtreleme
        if (categorySelectionModel.isConfidenceSelected) {
            allQuotes.filter { it.quoteCategory == 3 }.forEach {
                filteredQuotes.add(it)
            }
        }


        if (categorySelectionModel.isSelfImprovementSelected) {
            allQuotes.filter { it.quoteCategory == 6 }.forEach {
                filteredQuotes.add(it)
            }
        }

        if (categorySelectionModel.isLifeSelected) {
            allQuotes.filter { it.quoteCategory == 7 }.forEach {
                filteredQuotes.add(it)
            }
        }

        if (categorySelectionModel.isStrengthSelected) {
            allQuotes.filter { it.quoteCategory == 8 }.forEach {
                filteredQuotes.add(it)
            }
        }

        if (categorySelectionModel.isPositivitySelected) {
            allQuotes.filter { it.quoteCategory == 9 }.forEach {
                filteredQuotes.add(it)
            }
        }

        if (categorySelectionModel.isAnxietySelected) {
            allQuotes.filter { it.quoteCategory == 10 }.forEach {
                filteredQuotes.add(it)
            }
        }

        if (categorySelectionModel.isSelfEsteemSelected) {
            allQuotes.filter { it.quoteCategory == 11 }.forEach {
                filteredQuotes.add(it)
            }
        }

        if (categorySelectionModel.isSadnessSelected) {
            allQuotes.filter { it.quoteCategory == 13 }.forEach {
                filteredQuotes.add(it)
            }
        }

        if (categorySelectionModel.isLikedSelected) {
            allQuotes.filter { it.isLiked }.forEach {
                filteredQuotes.add(it)
            }
        }

        if (categorySelectionModel.isContinuingLifeSelected) {
            allQuotes.filter { it.quoteCategory == 14 }.forEach {
                filteredQuotes.add(it)
            }
        }


        if (categorySelectionModel.isWorkSelected) {
            allQuotes.filter { it.quoteCategory == 15 }.forEach {
                filteredQuotes.add(it)
            }
        }


        if (categorySelectionModel.isToxicRelationshipsSelected) {
            allQuotes.filter { it.quoteCategory == 16 }.forEach {
                filteredQuotes.add(it)
            }
        }

        if (categorySelectionModel.isSeparationSelected) {
            allQuotes.filter { it.quoteCategory == 17 }.forEach {
                filteredQuotes.add(it)
            }
        }


        if (categorySelectionModel.isCourageSelected) {
            allQuotes.filter { it.quoteCategory == 18 }.forEach {
                filteredQuotes.add(it)
            }
        }
        if (categorySelectionModel.isSportSelected) {
            allQuotes.filter { it.quoteCategory == 19 }.forEach {
                filteredQuotes.add(it)
            }
        }


        if (categorySelectionModel.isLoveSelected) {
            allQuotes.filter { it.quoteCategory == 20 }.forEach {
                filteredQuotes.add(it)
            }
        }


        _quotes.value = if (shouldShuffle) filteredQuotes.shuffled() else filteredQuotes.toList()
        _quotes.value.forEach{
            if(it.isLiked){
                isLikedListEmpty.value = false
                return@forEach

            }

        }


    }

    private fun markAsLikedInternally(quoteId: Int) {
        viewModelScope.launch {
            try {
                _quotes.value.find { it.id == quoteId }!!.isLiked = true
                allQuotes.find { it.id == quoteId }!!.isLiked = true
                isLikedListEmpty.value = false
            } catch (e: Exception) {
                e.printStackTrace()

            }
        }
    }


    fun removeLikedInternally(quoteId: Int) {
        viewModelScope.launch {
            try {
                _quotes.value.find { it.id == quoteId }!!.isLiked = false
                allQuotes.find { it.id == quoteId }!!.isLiked = false
                if(allQuotes.none { it.isLiked }) isLikedListEmpty.value = true

            } catch (e: Exception) {
                e.printStackTrace()

            }
        }
    }

    //load notification via offset
    private fun loadQuotes() {
        viewModelScope.launch {
            try {
                _quotes.value =
                    quoteUseCases.getQuote.executeRequest()

                allQuotes = _quotes.value
                allQuotes.forEach {
                    if(it.isLiked){
                        isLikedListEmpty.value = false
                        return@forEach
                    }
                }


            } catch (e: Exception) {
                e.printStackTrace()
            } finally {

            }
        }
    }


    private fun initializeUxDialogState() {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val showDialog = sharedPreferences.getBoolean("shouldShowUxDialog1", true)
        _shouldShowUxDialog1.value = showDialog
    }

    private fun initializeUxDialogState2() {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val showDialog = sharedPreferences.getBoolean("shouldShowUxDialog2", true)
        _shouldShowUxDialog2.value = showDialog
    }

    fun setShouldShowUxDialog1(show: Boolean) {
        _shouldShowUxDialog1.value = show
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().putBoolean("shouldShowUxDialog1", show).apply()
        initializeUxDialogState()

    }

    fun setShouldShowUxDialog2(show: Boolean) {
        _shouldShowUxDialog1.value = show
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().putBoolean("shouldShowUxDialog2", show).apply()
        initializeUxDialogState2()

    }

    //Permission operations
    private val sharedPreferences =
        context.getSharedPreferences("PermissionPreferences", Context.MODE_PRIVATE)

    private val sharedPreferencesForCategorySelection =
        context.getSharedPreferences("CategorySelection", Context.MODE_PRIVATE)


    fun saveCategorySelection(model: CategorySelectionModel) {
        sharedPreferencesForCategorySelection.edit().apply {
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
                "isLoveSelected" to model.isLoveSelected
            )
            selectionMap.forEach { (key, value) -> putBoolean(key, value) }
            apply()
        }
    }


    fun getCategorySelection(): CategorySelectionModel {
        val defaultValues = CategorySelectionModel() // Use the default values from the data class

        return CategorySelectionModel(
            isGeneralSelected = sharedPreferencesForCategorySelection.getBoolean("isGeneralSelected", defaultValues.isGeneralSelected),
            isLikedSelected = sharedPreferencesForCategorySelection.getBoolean("isLikedSelected", defaultValues.isLikedSelected),
            isBeYourselfSelected = sharedPreferencesForCategorySelection.getBoolean("isBeYourselfSelected", defaultValues.isBeYourselfSelected),
            isConfidenceSelected = sharedPreferencesForCategorySelection.getBoolean("isConfidenceSelected", defaultValues.isConfidenceSelected),
            isSelfImprovementSelected = sharedPreferencesForCategorySelection.getBoolean("isSelfImprovementSelected", defaultValues.isSelfImprovementSelected),
            isLifeSelected = sharedPreferencesForCategorySelection.getBoolean("isLifeSelected", defaultValues.isLifeSelected),
            isStrengthSelected = sharedPreferencesForCategorySelection.getBoolean("isStrengthSelected", defaultValues.isStrengthSelected),
            isPositivitySelected = sharedPreferencesForCategorySelection.getBoolean("isPositivitySelected", defaultValues.isPositivitySelected),
            isAnxietySelected = sharedPreferencesForCategorySelection.getBoolean("isAnxietySelected", defaultValues.isAnxietySelected),
            isSelfEsteemSelected = sharedPreferencesForCategorySelection.getBoolean("isSelfEsteemSelected", defaultValues.isSelfEsteemSelected),
            isSadnessSelected = sharedPreferencesForCategorySelection.getBoolean("isSadnessSelected", defaultValues.isSadnessSelected),
            isContinuingLifeSelected = sharedPreferencesForCategorySelection.getBoolean("isContinuingLifeSelected", defaultValues.isContinuingLifeSelected),
            isWorkSelected = sharedPreferencesForCategorySelection.getBoolean("isWorkSelected", defaultValues.isWorkSelected),
            isToxicRelationshipsSelected = sharedPreferencesForCategorySelection.getBoolean("isToxicRelationshipsSelected", defaultValues.isToxicRelationshipsSelected),
            isSeparationSelected = sharedPreferencesForCategorySelection.getBoolean("isSeparationSelected", defaultValues.isSeparationSelected),
            isCourageSelected = sharedPreferencesForCategorySelection.getBoolean("isCourageSelected", defaultValues.isCourageSelected),
            isSportSelected = sharedPreferencesForCategorySelection.getBoolean("isSportSelected", defaultValues.isSportSelected),
            isLoveSelected = sharedPreferencesForCategorySelection.getBoolean("isLoveSelected", defaultValues.isLoveSelected)
        )
    }


    private fun isLikedListEmpty(): Boolean {
        isLikedListEmpty.value = allQuotes.none { it.isLiked }
        return isLikedListEmpty.value

    }


    fun updatePermissionStatus(value: Boolean = false) {
        val key: String = "notificationPref"
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                sharedPreferences.edit().putBoolean(key, value).apply()
            }
        }
    }

    fun isPermissionGranted(): Boolean {
        val key: String = "notificationPref"
        return sharedPreferences.getBoolean(key, false)
    }

    // Belirli aralıklarla internet bağlantısını kontrol et
    private fun startPeriodicConnectionCheck() {
        viewModelScope.launch(Dispatchers.IO) {
            while (true) { // Sonsuz döngü
                checkConnection() // Bağlantıyı kontrol et
                delay(30000) // 30 saniye bekle (30000 milisaniye)
            }
        }
    }

    fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork =
                connectivityManager.getNetworkCapabilities(network) ?: return false
            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            @Suppress("DEPRECATION")
            val networkInfo = connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
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