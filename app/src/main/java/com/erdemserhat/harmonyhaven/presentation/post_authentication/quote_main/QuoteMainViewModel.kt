package com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erdemserhat.harmonyhaven.domain.usecase.GetQuotesUseCase
import com.erdemserhat.harmonyhaven.domain.usecase.user.UserUseCases
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
    private val getQuotesUseCase: GetQuotesUseCase,
    @ApplicationContext private val context: Context,
    private val userUseCases: UserUseCases
) : ViewModel() {
    private val _quotes =
        MutableStateFlow<List<com.erdemserhat.harmonyhaven.dto.responses.Quote>>(emptyList())
    val quotes: StateFlow<List<com.erdemserhat.harmonyhaven.dto.responses.Quote>> = _quotes

    // StateFlow ile UX Dialog gösterecek mi
    private val _shouldShowUxDialog1 = MutableStateFlow(true)
    private val _shouldShowUxDialog2 = MutableStateFlow(false)
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

    fun tryLoad(){
        loadQuotes()
        initializeUxDialogState()
        initializeUxDialogState2()
        checkConnection()


    }


    private fun checkConnection(){
        viewModelScope.launch {

            val authStatus = async {
                userUseCases.checkUserAuthenticationStatus.executeRequest()
            }
            _authState.value = authStatus.await()
            Log.d("AuthStats",_authState.value.toString())


        }

    }

    //load notification via offset
    private fun loadQuotes() {
        viewModelScope.launch {
            try {
                _quotes.value =
                    getQuotesUseCase.executeRequest()

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
        initializeUxDialogState()

    }

    //Permission operations
    private val sharedPreferences =
        context.getSharedPreferences("PermissionPreferences", Context.MODE_PRIVATE)


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
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
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

        }catch (e:Exception){

        }


    }


}