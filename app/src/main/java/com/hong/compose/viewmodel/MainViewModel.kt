package com.hong.compose.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.*
import com.hong.compose.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    repository: MainRepository
) : ViewModel() {

    val username = mutableStateOf(TextFieldValue("admin"))
    val password = mutableStateOf(TextFieldValue("123"))
    val article = repository.getIndexArticles()


}