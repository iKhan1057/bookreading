package com.bookreaderapp.screens.details

import androidx.lifecycle.ViewModel
import com.bookreaderapp.data.Resource
import com.bookreaderapp.model.Item
import com.bookreaderapp.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReaderBookDetailsViewModel @Inject constructor(private val bookRepository: BookRepository):ViewModel() {
    suspend fun getBookInfo(bookId: String): Resource<Item> {
        return bookRepository.getBookInfo(bookId)
    }
}