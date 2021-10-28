package com.example.meetup.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.meetup.R
import com.example.meetup.model.FirestoreUser
import kotlinx.android.synthetic.main.database_user_row.view.*

class DatabaseAdapter(private val userList: ArrayList<FirestoreUser>,
                      private val onClickItem: (FirestoreUser) -> Unit
) :
    RecyclerView.Adapter<DatabaseAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val first: TextView = itemView.databaseFirstNameTextView
        val last: TextView = itemView.databaseLastNameTextView
        val email: TextView = itemView.databaseEmailTextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DatabaseAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.database_user_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: DatabaseAdapter.ViewHolder, position: Int) {
        val user : FirestoreUser = userList[position]
        holder.first.text = user.first
        holder.last.text = user.last
        holder.email.text = user.email
        holder.itemView.setOnClickListener {
            onClickItem(user)
        }
    }

    override fun getItemCount(): Int = userList.size
}