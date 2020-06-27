package com.example.userinfotask

import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream

object UserInfoRepository {
    lateinit var userInfo: UserInfo

    @Throws(IOException::class)
    fun savePictureToFile(context: Context) : File {
        val storageDirectory =
            context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val outStream: OutputStream
        val file = File.createTempFile(
            "user_picture",
            ".png",
            storageDirectory
        )

        outStream = FileOutputStream(file)
        userInfo.picture.compress(Bitmap.CompressFormat.PNG, 100, outStream)
        outStream.flush()
        outStream.close()
        return file
    }



}