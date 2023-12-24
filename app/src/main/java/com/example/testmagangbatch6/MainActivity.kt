package com.example.testmagangbatch6

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {

    private lateinit var name: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnCheck: MaterialButton = findViewById(R.id.btn_login)
        val btnNext: MaterialButton = findViewById(R.id.btn_next)
        name = findViewById(R.id.ed_name)
        val inputText: TextInputEditText = findViewById(R.id.et_password)

        btnCheck.setOnClickListener {
            val sentence = inputText.text.toString()
            checkPalindrome(sentence)
        }

        btnNext.setOnClickListener {
            val name = name.text.toString()
            navigateToSecondActivity(name)
        }
    }

    private fun checkPalindrome(s: String) {
        val cleanString = s.replace("\\s".toRegex(), "").toLowerCase()
        val length = cleanString.length
        var isPalindrome = true

        for (i in 0 until length / 2) {
            if (cleanString[i] != cleanString[length - i - 1]) {
                isPalindrome = false
                break
            }
        }

        val message = if (isPalindrome) "isPalindrome" else "not palindrome"

        showDialog(message)
    }

    private fun showDialog(message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }

        val dialog = builder.create()
        dialog.show()
    }

    private fun navigateToSecondActivity(name: String) {
        val intent = Intent(this, SecondActivity::class.java)
        intent.putExtra("NAME_EXTRA", name)
        startActivity(intent)
    }
}