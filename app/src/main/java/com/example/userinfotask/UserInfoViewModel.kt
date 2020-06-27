package com.example.userinfotask

import android.graphics.Bitmap
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserInfoViewModel : ViewModel() {
    private val _userInfo = MutableLiveData<UserInfoState>()
    val userInfo: LiveData<UserInfoState> = _userInfo

    fun userInputChanged(
        isPhotoAttached: Boolean? = null,
        email: String? = null,
        phone: String? = null,
        password: String? = null
    ) {
        val state = _userInfo.value ?: UserInfoState()

        isPhotoAttached?.let { state.isPhotoAttached = it }
        email?.let { state.isEmailValid = isEmailValid(it) }
        phone?.let { state.isPhoneValid = isPhoneValid(it) }
        password?.let { state.isPasswordValid = isPasswordValid(it) }
        _userInfo.value = state
    }

    fun saveUserInfo(
        picture: Bitmap,
        email: String,
        phone: String,
        password: String
    ) {
        UserInfoRepository.userInfo = UserInfo(picture, email, phone, password)
    }

    private fun isEmailValid(email: String) = Patterns.EMAIL_ADDRESS.matcher(email).matches()

    private fun isPhoneValid(phone: String) = phone.length == 12 && phone.startsWith("+")

    private fun isPasswordValid(password: String) = password.length >= 6 && password.contains(Regex("[A-Za-z0-9]"))
}