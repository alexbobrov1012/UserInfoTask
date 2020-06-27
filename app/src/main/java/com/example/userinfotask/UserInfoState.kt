package com.example.userinfotask

data class UserInfoState(
    var isPhotoAttached: Boolean = false,
    var isEmailValid: Boolean = false,
    var isPhoneValid: Boolean = false,
    var isPasswordValid: Boolean = false
) {
    val isDataValid: Boolean
        get() {
            return isPhotoAttached && isEmailValid && isPhoneValid && isPasswordValid
        }
}