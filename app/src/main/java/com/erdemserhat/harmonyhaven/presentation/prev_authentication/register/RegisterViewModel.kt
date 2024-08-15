package com.erdemserhat.harmonyhaven.presentation.prev_authentication.register

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erdemserhat.harmonyhaven.domain.model.RegisterFormModel
import com.erdemserhat.harmonyhaven.domain.model.toUserInformationSchema
import com.erdemserhat.harmonyhaven.domain.usecase.user.UserUseCases
import com.erdemserhat.harmonyhaven.domain.validation.areStringsEqual
import com.erdemserhat.harmonyhaven.dto.requests.UserInformationSchema
import com.erdemserhat.harmonyhaven.presentation.prev_authentication.register.state.RegisterState
import com.erdemserhat.harmonyhaven.presentation.prev_authentication.register.state.RegisterValidationState
import com.erdemserhat.harmonyhaven.presentation.prev_authentication.register.state.getValidationStateByErrorCode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val userUseCases: UserUseCases
) : ViewModel() {

    private val _registerState = mutableStateOf(RegisterState()) //--->ViewModel Specific
    val registerState: State<RegisterState> = _registerState //--->View Specific

    fun onRegisterClicked(formModel: RegisterFormModel) {
        _registerState.value = _registerState.value.copy(
            isLoading = true
        )
        if (!areStringsEqual(formModel.password, formModel.confirmPassword)) {
            _registerState.value = _registerState.value.copy(
                registerValidationState = RegisterValidationState(isPasswordValid = false),
                registerWarning = "Şifreler uyuşmuyor.",
                isLoading = false
            )
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            delay(400)
            registerUser(formModel.toUserInformationSchema())

        }

    }

    private suspend fun registerUser(userInformationSchema: UserInformationSchema) {
        viewModelScope.launch(Dispatchers.IO) {
            val responseDeferred =  async { userUseCases.registerUser.executeRequest(userInformationSchema.apply { userInformationSchema.surname="default" }) }
            val response = responseDeferred.await()

            if (response == null) {
                //network error
                _registerState.value = _registerState.value.copy(
                    registerWarning = "Bağlantı Hatasu :(",
                    isLoading = false
                )

                return@launch
            }


            if (!response.formValidationResult.isValid) {
                //form not valid
                val errorCode = response.formValidationResult.errorCode
                _registerState.value = _registerState.value.copy(
                    registerValidationState = RegisterValidationState().getValidationStateByErrorCode(errorCode),
                    registerWarning = when (errorCode) {
                        201 -> "İsim çok kısa. En az 2 karakter uzunluğunda olmalıdır."
                        202 -> "İsim yalnızca harfler içermelidir."
                        203 -> "Soyad çok kısa. En az 2 karakter uzunluğunda olmalıdır."
                        204 -> "Soyad yalnızca harfler içermelidir."
                        205 -> "Geçersiz e-posta formatı."
                        206 -> "Bu e-posta ile zaten kayıtlı bir kullanıcı var."
                        207 -> "Şifre en az 8 karakter uzunluğunda olmalıdır."
                        208 -> "Şifre en az bir büyük harf, bir küçük harf ve bir rakam içermelidir."
                        209 -> "Şifre kullanıcı adını içermemelidir. Lütfen farklı bir şifre seçin."
                        210 -> "Şifre soyadı içermemelidir. Lütfen farklı bir şifre seçin."
                        211 -> "Şifre e-posta adresini içermemelidir. Lütfen farklı bir şifre seçin."
                        else -> "Bilinmeyen bir hata oluştu."
                    },
                    isLoading = false
                )
                return@launch
            }

            if(!response.isRegistered){
                _registerState.value = _registerState.value.copy(
                    registerWarning = "Bağlantı hatası :(",
                    isLoading = false
                )
                return@launch
            }

            //user's information's are valid
            //passwords are match
            //there is no problem in this line

            _registerState.value = _registerState.value.copy(
                canNavigateTo = true
            )


        }

    }
}