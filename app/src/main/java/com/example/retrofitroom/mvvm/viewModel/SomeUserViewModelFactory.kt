package com.example.retrofitroom.mvvm.viewModel

import dagger.assisted.AssistedFactory

@AssistedFactory
interface SomeUserViewModelFactory {
    fun create(id: String): SomeUserViewModel
}