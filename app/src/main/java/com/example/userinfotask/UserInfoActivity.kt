package com.example.userinfotask

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.textfield.TextInputLayout


class UserInfoActivity : AppCompatActivity() {
    companion object {
        private const val REQUEST_CODE_PICTURE = 1
    }
    private lateinit var viewModel: UserInfoViewModel
    private lateinit var picture: AppCompatImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_input)

        val email = findViewById<EditText>(R.id.email)
        val phone = findViewById<EditText>(R.id.phone)
        val password = findViewById<EditText>(R.id.password)
        val emailContainer = findViewById<TextInputLayout>(R.id.emailContainer)
        val phoneContainer = findViewById<TextInputLayout>(R.id.phoneContainer)
        val passwordContainer = findViewById<TextInputLayout>(R.id.passwordContainer)
        val hintPicture = findViewById<TextView>(R.id.pictureHint)
        picture = findViewById(R.id.picture)
        viewModel = ViewModelProviders.of(this).get(UserInfoViewModel::class.java)

        val preview = findViewById<Button>(R.id.preview).also{
            it.setOnClickListener {
                viewModel.saveUserInfo(
                    (picture.drawable as BitmapDrawable).bitmap,
                    email.text.toString(),
                    phone.text.toString(),
                    password.text.toString()
                )
                startActivity(Intent(this, PreviewActivity::class.java))
            }
        }
        picture.setOnClickListener { dispatchTakePictureIntent() }

        viewModel.userInfo.observe(this, Observer {
            val inputState = it ?: return@Observer
            preview.isEnabled = inputState.isDataValid

            if (!inputState.isEmailValid) {
                emailContainer.error = getString(R.string.invalid_email)
            } else {
                emailContainer.error = null
            }
            if (!inputState.isPhoneValid) {
                phoneContainer.error = getString(R.string.invalid_phone)
            } else {
                phoneContainer.error = null
            }
            if (!inputState.isPasswordValid) {
                passwordContainer.error = getString(R.string.invalid_password)
            } else {
                passwordContainer.error = null
            }
            if (!inputState.isPhotoAttached) {
                hintPicture.visibility = View.VISIBLE
            } else {
                hintPicture.visibility = View.GONE
            }
        })

        email.setTextChangedListener { viewModel.userInputChanged(email = it) }
        phone.setTextChangedListener{ viewModel.userInputChanged(phone = it) }
        password.setTextChangedListener{ viewModel.userInputChanged(password = it) }
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_CODE_PICTURE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_PICTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            picture.setImageBitmap(imageBitmap)
            viewModel.userInputChanged(
                isPhotoAttached = true
            )
        }
    }
}

fun EditText.setTextChangedListener(v: (String) -> (Unit)) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(text: Editable?) {
             v(text.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    })
}