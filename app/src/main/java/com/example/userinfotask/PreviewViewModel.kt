package com.example.userinfotask

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import java.io.IOException

class PreviewViewModel : ViewModel() {
    private val userInfo = UserInfoRepository.userInfo

    fun sendUserInfoByEmail(context: Context, projectName: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_SUBJECT, "$projectName: profile data ")
        intent.putExtra(Intent.EXTRA_TEXT, "\rEmail: ${userInfo.email}\n\rPhone: ${userInfo.phone}\n\r")

        try {
            val file = UserInfoRepository.savePictureToFile(context)
            val u = FileProvider.getUriForFile(
                context,
                "com.example.fileprovider",
                file
            )
            intent.putExtra(Intent.EXTRA_STREAM, u)
        } catch (ex: IOException) {
            ex.printStackTrace()
            Log.d(PreviewViewModel::class.simpleName, "Cannot attach picture")
        }

        context.startActivity(Intent.createChooser(intent, "Email to"))
    }

    fun getPicture() = userInfo.picture
    fun getEmail() = userInfo.email
    fun getPhone() = userInfo.phone
    fun getPassword() = userInfo.password

}