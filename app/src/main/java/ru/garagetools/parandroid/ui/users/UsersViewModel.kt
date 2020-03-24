package ru.garagetools.parandroid.ui.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import ru.garagetools.parandroid.models.User

class UsersViewModel (): ViewModel() {

    private val userRepository = UserRepository()

    var userList: LiveData<List<User>> = userRepository.getUsers()

    fun getListUsers() = userList
}