package com.example.testmagangbatch6.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testmagangbatch6.R
import com.example.testmagangbatch6.model.Data
import de.hdodenhof.circleimageview.CircleImageView


class UserAdapter(private var userList: List<Data?>) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private var onUserItemClickListener: OnUserItemClickListener? = null

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val profileImage: CircleImageView = itemView.findViewById(R.id.profile_image)
        val itemNameTextView: TextView = itemView.findViewById(R.id.tv_nameRv)
        val emailTextView: TextView = itemView.findViewById(R.id.tv_email)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UserViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentUser = userList[position]

        // Set data ke view holder
        holder.itemNameTextView.text = "${currentUser?.first_name} ${currentUser?.last_name}"
        holder.emailTextView.text = currentUser?.email

        // Menggunakan Glide untuk memuat gambar dari URL ke CircleImageView
        Glide.with(holder.profileImage.context)
            .load(currentUser?.avatar)
            .into(holder.profileImage)

        // Menambahkan onClickListener untuk setiap item
        holder.itemView.setOnClickListener {

            onUserItemClickListener?.onUserItemClick(currentUser)
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun setData(userList: List<Data?>) {
        this.userList = userList
        notifyDataSetChanged()
    }

    fun addData(newData: List<Data?>) {
        val updatedList = mutableListOf<Data?>()
        updatedList.addAll(userList)
        updatedList.addAll(newData)
        userList = updatedList
        notifyDataSetChanged()
    }

    interface OnUserItemClickListener {
        fun onUserItemClick(selectedUser: Data?)
    }

    fun setOnItemClickListener(listener: OnUserItemClickListener) {
        this.onUserItemClickListener = listener
    }
}

