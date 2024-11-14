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
                quoteUseCases.likeQuote.executeRequest(quoteId)
                markAsLikedInternally(quoteId)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }

    fun removeLikeQuote(quoteId: Int) {
        viewModelScope.launch {
            try {
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
        val categoryMap = mapOf(
            1 to "Genel",
            2 to "Kendin Ol",
            3 to "Özgüven",
            5 to "Kısa Alıntılar",
            6 to "Kişisel Gelişim",
            7 to "Yaşam",
            8 to "Güç",
            9 to "Pozitif Düşünmek",
            10 to "Kaygıyla Başetme",
            11 to "Öz Saygı",
            12 to "İzzetinefis",
            13 to "Üzüntü",
            14 to "Kalbi Kırık",
            15 to "İş",
            16 to "Toksik İlişkiler"
        )

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

        if (categorySelectionModel.isShortQuotesSelected) {
            allQuotes.filter { it.quoteCategory == 5 }.forEach {
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

        if (categorySelectionModel.isSelfLoveSelected) {
            allQuotes.filter { it.quoteCategory == 12 }.forEach {
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

        if (categorySelectionModel.isHeartBrokenSelected) {
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


        _quotes.value = if (shouldShuffle) filteredQuotes.shuffled() else filteredQuotes.toList()


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
            putBoolean("isGeneralSelected", model.isGeneralSelected)
            putBoolean("isLikedSelected", model.isLikedSelected)
            putBoolean("isBeYourselfSelected", model.isBeYourselfSelected)
            putBoolean("isConfidenceSelected", model.isConfidenceSelected)
            putBoolean("isShortQuotesSelected", model.isShortQuotesSelected)
            putBoolean("isSelfImprovementSelected", model.isSelfImprovementSelected)
            putBoolean("isLifeSelected", model.isLifeSelected)
            putBoolean("isStrengthSelected", model.isStrengthSelected)
            putBoolean("isPositivitySelected", model.isPositivitySelected)
            putBoolean("isAnxietySelected", model.isAnxietySelected)
            putBoolean("isSelfEsteemSelected", model.isSelfEsteemSelected)
            putBoolean("isSelfLoveSelected", model.isSelfLoveSelected)
            putBoolean("isSadnessSelected", model.isSadnessSelected)
            putBoolean("isHeartBrokenSelected", model.isHeartBrokenSelected)
            putBoolean("isWorkSelected", model.isWorkSelected)
            putBoolean("isToxicRelationshipsSelected", model.isToxicRelationshipsSelected)
            apply()
        }
    }


    fun getCategorySelection(): CategorySelectionModel {
        return CategorySelectionModel(
            isGeneralSelected = sharedPreferencesForCategorySelection.getBoolean("isGeneralSelected", true),
            isLikedSelected = sharedPreferencesForCategorySelection.getBoolean("isLikedSelected", false),
            isBeYourselfSelected = sharedPreferencesForCategorySelection.getBoolean("isBeYourselfSelected", false),
            isConfidenceSelected = sharedPreferencesForCategorySelection.getBoolean("isConfidenceSelected", false),
            isShortQuotesSelected = sharedPreferencesForCategorySelection.getBoolean("isShortQuotesSelected", false),
            isSelfImprovementSelected = sharedPreferencesForCategorySelection.getBoolean("isSelfImprovementSelected", false),
            isLifeSelected = sharedPreferencesForCategorySelection.getBoolean("isLifeSelected", false),
            isStrengthSelected = sharedPreferencesForCategorySelection.getBoolean("isStrengthSelected", false),
            isPositivitySelected = sharedPreferencesForCategorySelection.getBoolean("isPositivitySelected", false),
            isAnxietySelected = sharedPreferencesForCategorySelection.getBoolean("isAnxietySelected", false),
            isSelfEsteemSelected = sharedPreferencesForCategorySelection.getBoolean("isSelfEsteemSelected", false),
            isSelfLoveSelected = sharedPreferencesForCategorySelection.getBoolean("isSelfLoveSelected", false),
            isSadnessSelected = sharedPreferencesForCategorySelection.getBoolean("isSadnessSelected", false),
            isHeartBrokenSelected = sharedPreferencesForCategorySelection.getBoolean("isHeartBrokenSelected", false),
            isWorkSelected = sharedPreferencesForCategorySelection.getBoolean("isWorkSelected", false),
            isToxicRelationshipsSelected = sharedPreferencesForCategorySelection.getBoolean("isToxicRelationshipsSelected", false)
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