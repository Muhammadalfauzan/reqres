package com.example.testmagangbatch6

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class SecondActivity : AppCompatActivity() {
    private lateinit var textViewWelcome: TextView
    private lateinit var textViewUserName: TextView
    private lateinit var textViewSelected: TextView
    private lateinit var materialButton: MaterialButton

    private var selectedUserName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        // Inisialisasi view
        textViewWelcome = findViewById(R.id.textView)
        textViewUserName = findViewById(R.id.tv_name)
        textViewSelected = findViewById(R.id.tv_selected)
        materialButton = findViewById(R.id.btn_chose)

        // Mendapatkan data dari Intent
        val name = intent.getStringExtra("NAME_EXTRA")

        // Menampilkan data di TextView
        textViewUserName.text = getString(R.string.name)
        textViewUserName.text = name

        // Tambahkan onClickListener untuk MaterialButton
        materialButton.setOnClickListener {
            // Start the ThirdActivity to choose a user
            val intent = Intent(this, ThirdActivity::class.java)
            startActivityForResult(intent, THIRD_ACTIVITY_REQUEST_CODE)
        }

        val btnBack: ImageButton = findViewById(R.id.btn_back)

        btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    // This method will be called when the ThirdActivity finishes
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == THIRD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            // Get the selected user name from ThirdActivity
            val selectedUserName = data?.getStringExtra("SELECTED_USER_NAME")
            selectedUserName?.let {
                setSelectedUserName(it)
            }
        }
    }

    // This method sets the selected user name and updates the UI
    private fun setSelectedUserName(userName: String) {
        selectedUserName = userName
        // Update the TextView to show the selected user name
        textViewSelected.text = userName
    }

    companion object {
        const val THIRD_ACTIVITY_REQUEST_CODE = 1
    }
}
