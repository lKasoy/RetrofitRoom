package com.example.retrofitroom.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.ViewModelProvider
import com.example.retrofitroom.R
import com.example.retrofitroom.constants.Constants.UUID
import com.example.retrofitroom.data.model.entity.UsersTable
import com.example.retrofitroom.databinding.FragmentItemListBinding
import com.example.retrofitroom.di.DI
import com.example.retrofitroom.mvvm.viewModel.UsersViewModel
import com.example.retrofitroom.mvvm.viewModel.UsersViewModelFactory
import com.example.retrofitroom.services.Status
import com.example.retrofitroom.services.UsersAdapter

class UsersFragment : Fragment() {

    private lateinit var binding: FragmentItemListBinding
    private var usersAdapter = UsersAdapter(
        onCLick = { user: UsersTable ->
            val userBundle = bundleOf(
                UUID to user.uuid
            )
            Log.d("MyApp", user.large)

            parentFragmentManager.commit {
                setReorderingAllowed(true)
                replace<SomeUserFragment>(R.id.container, args = userBundle)
                addToBackStack(null)
            }
        }, onEndReached = {
            fragmentListViewModel.getUsers()
        }
    )

    private lateinit var fragmentListViewModel: UsersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentItemListBinding.bind(
            inflater.inflate(
                R.layout.fragment_item_list,
                container,
                false
            )
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.list.adapter = usersAdapter
        val factory = UsersViewModelFactory(DI.repository)
        fragmentListViewModel = ViewModelProvider(this, factory).get(UsersViewModel::class.java)
        observeGetPosts()
    }

    private fun observeGetPosts() {
        fragmentListViewModel.simpleLiveData.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.LOADING -> viewOneLoading()
                Status.SUCCESS -> viewOneSuccess(it.data)
                Status.ERROR ->  viewFromDb(it.data)
            }
        })
    }

    private fun viewOneLoading() {
        Toast.makeText(context, "LOADING", Toast.LENGTH_SHORT).show()
    }

    private fun viewOneSuccess(data: List<UsersTable>?) {
        Toast.makeText(context, "LOAD FROM API", Toast.LENGTH_SHORT).show()
        data.let {
            usersAdapter.submitList(it)
        }
    }

    private fun viewFromDb(data: List<UsersTable>?) {
        Toast.makeText(context, "INTERNET CONNECTION IS FAILED", Toast.LENGTH_SHORT)
            .show()
        Toast.makeText(context, "LOAD FROM DB", Toast.LENGTH_SHORT).show()
        data.let {
            usersAdapter.submitList(it)
        }
    }
}