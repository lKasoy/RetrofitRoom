package com.example.retrofitroom.view

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.retrofitroom.R
import com.example.retrofitroom.constants.Constants.UUID
import com.example.retrofitroom.data.model.entity.UsersTable
import com.example.retrofitroom.databinding.FragmentSomeUserBinding
import com.example.retrofitroom.di.DI
import com.example.retrofitroom.mvvm.viewModel.SomeUserViewModel
import com.example.retrofitroom.mvvm.viewModel.SomeUserViewModelFactory

class SomeUserFragment : Fragment() {

    private lateinit var binding: FragmentSomeUserBinding
    private lateinit var someUserViewModel: SomeUserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_some_user, container, false)
        binding = FragmentSomeUserBinding.bind(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = SomeUserViewModelFactory(DI.repository)
        someUserViewModel = ViewModelProvider(this, factory).get(SomeUserViewModel::class.java)
        val uuid = requireArguments().getString(UUID)

        someUserViewModel.selectedUser.observe(viewLifecycleOwner, Observer {
            it?.let { user: UsersTable ->
                binding.apply {
                    showAvatar(user.large)
                    gender.text = user.gender
                    nameTitle.text = user.title
                    nameFirst.text = user.first
                    nameLast.text = user.last
                }
            }
        })
        someUserViewModel.getSelectedUser(uuid!!)
    }

    private fun showAvatar(url: String) {
        Glide.with(this)
            .load(url)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.progress.visibility = View.GONE
                    return false
                }
                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.progress.visibility = View.GONE
                    return false
                }
            })
            .into(binding.avatar)
    }
}

