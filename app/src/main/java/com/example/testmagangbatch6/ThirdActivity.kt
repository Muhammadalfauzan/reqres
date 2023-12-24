package com.example.testmagangbatch6

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testmagangbatch6.adapter.UserAdapter
import com.example.testmagangbatch6.api.ApiClient
import com.example.testmagangbatch6.model.Data
import com.example.testmagangbatch6.model.DataUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ThirdActivity : AppCompatActivity() {
    private lateinit var userAdapter: UserAdapter
    private lateinit var recyclerView: RecyclerView

    private var currentPage = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)

        val btnBack: ImageButton = findViewById(R.id.btn_backThird)


        btnBack.setOnClickListener {
            onBackPressed()
        }

        recyclerView = findViewById(R.id.rv_user)


        recyclerView.layoutManager = LinearLayoutManager(this)

        userAdapter = UserAdapter(emptyList()) // Initially set an empty list
        recyclerView.adapter = userAdapter

        userAdapter.setOnItemClickListener(object : UserAdapter.OnUserItemClickListener {
            override fun onUserItemClick(selectedUser: Data?) {
                // Send the selected user name back to SecondActivity
                val resultIntent = Intent()
                resultIntent.putExtra("SELECTED_USER_NAME", "${selectedUser?.first_name} ${selectedUser?.last_name}")
                setResult(RESULT_OK, resultIntent)
                finish()
            }
        })

        loadUserData()


        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (visibleItemCount + firstVisibleItemPosition >= totalItemCount) {

                    loadUserData()
                }
            }
        })
    }

    private fun loadUserData() {
        val apiService = ApiClient.instance
        val call = apiService.getUsers(currentPage)
        call.enqueue(object : Callback<DataUser> {
            override fun onResponse(call: Call<DataUser>, response: Response<DataUser>) {
                if (response.isSuccessful) {
                    val users = response.body()?.data
                    users?.let {
                        runOnUiThread {

                            if (currentPage == 1) {
                                userAdapter.setData(it)
                            } else {
                                userAdapter.addData(it)
                            }
                        }


                        for (user in it) {
                            Log.d("User", "Name: ${user?.first_name} ${user?.last_name}, Email: ${user?.email}")
                        }

                        currentPage++
                    }
                } else {
                    Log.e("API Error", "Unsuccessful response: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<DataUser>, t: Throwable) {
                // Handle failure
                Log.e("API Error", t.message.toString())
            }
        })
    }


}
