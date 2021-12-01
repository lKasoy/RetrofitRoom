package com.example.retrofitroom

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.retrofitroom.databinding.ActivityMainBinding
import com.example.retrofitroom.view.UsersFragment
import dagger.hilt.android.AndroidEntryPoint
import dagger.internal.DaggerCollections

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        initFragment(savedInstanceState)
    }

    private fun initFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                replace<UsersFragment>(R.id.container)
                setReorderingAllowed(true)
            }
        }
    }
}