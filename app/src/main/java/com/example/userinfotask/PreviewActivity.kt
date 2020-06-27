package com.example.userinfotask

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.ViewModelProviders


class PreviewActivity : AppCompatActivity() {
    private lateinit var viewModel: PreviewViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preview)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel = ViewModelProviders.of(this).get(PreviewViewModel::class.java)
        findViewById<TextView>(R.id.email).text = viewModel.getEmail()
        findViewById<TextView>(R.id.phone).text = viewModel.getPhone()
        findViewById<TextView>(R.id.password).text = viewModel.getPassword()
        findViewById<AppCompatImageView>(R.id.picture).setImageBitmap(viewModel.getPicture())
        findViewById<Button>(R.id.sendEmail).setOnClickListener{
            val projectName = applicationInfo.loadLabel(packageManager).toString()
            viewModel.sendUserInfoByEmail(this, projectName)

        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

}