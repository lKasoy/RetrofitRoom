package com.example.retrofitroom.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.retrofitroom.R
import com.example.retrofitroom.constants.Constants.GENDER
import com.example.retrofitroom.constants.Constants.NAME_FIRST
import com.example.retrofitroom.constants.Constants.NAME_LAST
import com.example.retrofitroom.constants.Constants.NAME_TITLE
import com.example.retrofitroom.constants.Constants.PICTURE_LARGE
import com.example.retrofitroom.data.model.AppDatabase
import com.example.retrofitroom.data.model.entity.UsersTable
import com.example.retrofitroom.data.model.network.ApiService
import com.example.retrofitroom.data.model.repository.UserRepository
import com.example.retrofitroom.databinding.FragmentItemListBinding
import com.example.retrofitroom.mvvm.viewModel.BaseViewModel
import com.example.retrofitroom.mvvm.viewModel.UsersViewModelFactory
import com.example.retrofitroom.services.Status
import com.example.retrofitroom.services.UsersAdapter

class UsersFragment : Fragment() {

    private lateinit var binding: FragmentItemListBinding
    private var usersAdapter = UsersAdapter{
            user : UsersTable ->
        val userBundle = bundleOf(
            PICTURE_LARGE to user.large,
            GENDER to user.gender,
            NAME_TITLE to user.title,
            NAME_FIRST to user.first,
            NAME_LAST to user.last
        )
        parentFragmentManager.commit {
            setReorderingAllowed(true)
            replace<SomeUserFragment>(R.id.container, args = userBundle)
            addToBackStack(null)
        }
    }
    private lateinit var fragmentListViewModel: BaseViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentItemListBinding.bind(inflater.inflate(R.layout.fragment_item_list,container,false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.list.adapter = usersAdapter
        val dao = AppDatabase.getInstance(view.context).getUserDao
        val usersApi = ApiService.getInstance()
        val repository = UserRepository(dao, usersApi)
        val factory = UsersViewModelFactory(repository)
        fragmentListViewModel = ViewModelProvider(this,factory).get(BaseViewModel::class.java)
        observeGetPosts()
        fragmentListViewModel.getUsers()
    }

    private fun observeGetPosts() {
        fragmentListViewModel.simpleLiveData.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.LOADING -> viewOneLoading()
                Status.SUCCESS -> {
                    viewOneSuccess(it.data)
                    Toast.makeText(context,"LOAD FROM API",Toast.LENGTH_SHORT).show()}
                Status.DAO -> {
                    viewFromDb(it.data)
                    Toast.makeText(context,"LOAD FROM DB",Toast.LENGTH_SHORT).show()}

            }
        })
    }
    private fun viewOneLoading() {
        Toast.makeText(context,"LOADING",Toast.LENGTH_SHORT).show()
    }

    private fun viewOneSuccess(data: List<UsersTable>?) {
        data.let {
            usersAdapter.submitList(it)
        }
    }

    private fun viewFromDb(data: List<UsersTable>?){
        data.let {
            usersAdapter.submitList(it)
        }

    }
}