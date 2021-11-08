package com.example.retrofitroom.view

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet.GONE
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.retrofitroom.R
import com.example.retrofitroom.constants.Constants.GENDER
import com.example.retrofitroom.constants.Constants.NAME_FIRST
import com.example.retrofitroom.constants.Constants.NAME_LAST
import com.example.retrofitroom.constants.Constants.NAME_TITLE
import com.example.retrofitroom.constants.Constants.PICTURE_LARGE
import com.example.retrofitroom.databinding.FragmentSomeUserBinding

class SomeUserFragment : Fragment() {

    private lateinit var binding: FragmentSomeUserBinding

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
        binding.apply {
            context?.let {
                showAvatar(requireArguments().getString(PICTURE_LARGE))
            }
            gender.text = requireArguments().getString(GENDER)
            nameTitle.text = requireArguments().getString(NAME_TITLE)
            nameFirst.text = requireArguments().getString(NAME_FIRST)
            nameLast.text = requireArguments().getString(NAME_LAST)
        }
    }

    private fun showAvatar(url: String?) {
        Glide.with(this)
            .load(url)
            .listener(object: RequestListener<Drawable>{
                @SuppressLint("WrongConstant")
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.progress.visibility = GONE
                    return false
                }

                @SuppressLint("WrongConstant")
                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.progress.visibility = GONE
                    return false
                }
            })
            .into(binding.avatar)
    }
}

