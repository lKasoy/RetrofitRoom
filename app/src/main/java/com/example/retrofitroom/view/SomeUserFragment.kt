package com.example.retrofitroom.view

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.retrofitroom.R
import com.example.retrofitroom.constants.Constants.UUID
import com.example.retrofitroom.data.model.entity.UsersTable
import com.example.retrofitroom.databinding.FragmentSomeUserBinding
import com.example.retrofitroom.mvvm.viewModel.SomeUserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SomeUserFragment : Fragment() {

    private lateinit var binding: FragmentSomeUserBinding

    @Inject
    lateinit var someUserViewModelFactory: SomeUserViewModel.SomeUserViewModelFactory

    private val uuid by lazy {
        requireArguments().getString(UUID) ?: throw IllegalStateException("No uuid")
    }
    private val someUserViewModel: SomeUserViewModel by viewModels {
        SomeUserViewModel.provideFactory(someUserViewModelFactory, uuid)
    }

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

        someUserViewModel.selectedUser.observe(viewLifecycleOwner, {
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
        someUserViewModel.getSelectedUser()
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

