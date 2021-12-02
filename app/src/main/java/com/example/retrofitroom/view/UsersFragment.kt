package com.example.retrofitroom.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.ViewModelProvider
import com.example.retrofitroom.R
import com.example.retrofitroom.constants.Constants.UUID
import com.example.retrofitroom.dagger.App
import com.example.retrofitroom.data.model.entity.UsersTable
import com.example.retrofitroom.data.model.repository.DecoratorRepository
import com.example.retrofitroom.databinding.FragmentItemListBinding
import com.example.retrofitroom.mvvm.viewModel.UsersViewModel
import com.example.retrofitroom.mvvm.viewModel.UsersViewModelFactory
import com.example.retrofitroom.services.UsersAdapter
import javax.inject.Inject

class UsersFragment : Fragment() {

    private lateinit var binding: FragmentItemListBinding
    private var usersAdapter = UsersAdapter(
        onCLick = { user: UsersTable ->
            val userBundle = bundleOf(
                UUID to user.uuid
            )
            parentFragmentManager.commit {
                setReorderingAllowed(true)
                replace<SomeUserFragment>(R.id.container, args = userBundle)
                addToBackStack(null)
            }
        }, onEndReached = {
            fragmentListViewModel.fetchData()
        }
    )

    @Inject
    lateinit var repository: DecoratorRepository

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
        (requireContext().applicationContext as App).component.inject(this)
        val factory = UsersViewModelFactory(repository)
        fragmentListViewModel = ViewModelProvider(this, factory).get(UsersViewModel::class.java)
        subscribeData()
    }

    private fun subscribeData() {
        fragmentListViewModel.data.observe(viewLifecycleOwner, {
            it.let {
                usersAdapter.submitList(it)
            }
        })
    }
}