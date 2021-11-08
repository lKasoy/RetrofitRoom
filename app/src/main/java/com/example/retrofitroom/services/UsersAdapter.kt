package com.example.retrofitroom.services

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitroom.data.model.entity.Users
import com.example.retrofitroom.data.model.entity.UsersTable
import com.example.retrofitroom.databinding.FragmentItemBinding

class UsersAdapter(
    private val onCLick: (UsersTable) -> Unit
) : ListAdapter<UsersTable, UsersAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FragmentItemBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val result: UsersTable = currentList[position]
        holder.bind(result, onCLick)
    }
    companion object DiffCallback : DiffUtil.ItemCallback<UsersTable>() {
        override fun areItemsTheSame(oldItem: UsersTable, newItem: UsersTable): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: UsersTable, newItem: UsersTable): Boolean {
            return  oldItem == newItem
        }
    }
    class ViewHolder(private val binding: FragmentItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(result: UsersTable, onItemClick: (UsersTable) -> Unit) {
            binding.apply {
                firstName.text = result.first
                root.setOnClickListener {
                    onItemClick(result)
                }
            }
        }
    }
}