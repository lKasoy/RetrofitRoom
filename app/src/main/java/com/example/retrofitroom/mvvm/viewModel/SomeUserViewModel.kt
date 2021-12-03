package com.example.retrofitroom.mvvm.viewModel

import androidx.lifecycle.*
import com.example.retrofitroom.data.model.entity.UsersTable
import com.example.retrofitroom.data.model.repository.DecoratorRepository
import dagger.Module
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import kotlinx.coroutines.launch

class SomeUserViewModel @AssistedInject constructor(
    private val decoratorRepository: DecoratorRepository,
    @Assisted private val id: String
) : ViewModel() {

    private var _selectedUser: MutableLiveData<UsersTable> = MutableLiveData()
    val selectedUser: LiveData<UsersTable> = _selectedUser


    fun getSelectedUser() {
        viewModelScope.launch {
            _selectedUser.value = decoratorRepository.getUserById(id)
        }
    }

    @AssistedFactory
    interface SomeUserViewModelFactory {
        fun create(id: String): SomeUserViewModel
    }

    @Suppress("UNCHECKED_CAST")
    companion object {
        fun provideFactory(
            assistedFactory: SomeUserViewModelFactory,
            noteId: String
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(noteId) as T
            }
        }
    }
}

