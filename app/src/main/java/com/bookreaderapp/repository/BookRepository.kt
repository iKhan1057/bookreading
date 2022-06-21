package com.bookreaderapp.repository

import com.bookreaderapp.data.Resource
import com.bookreaderapp.model.Item
import com.bookreaderapp.network.BooksApi
import javax.inject.Inject

//class BookRepository @Inject constructor(private val booksApi: BooksApi) {
//    private val dataOrException = DataOrException<List<Item>, Boolean, Exception>()
//    suspend fun getBook(searchquery: String): DataOrException<List<Item>, Boolean, Exception> {
//        try {
//            dataOrException.loading = true
//            dataOrException.data = booksApi.getAllBooks(searchquery).items
//            if (dataOrException.data!!.isNotEmpty()) dataOrException.loading = false
//        } catch (execption:Exception) {
//            dataOrException.loading = false
//            dataOrException.e = execption
//        }
//        return dataOrException
//    }
//
//    private val bookInfoDataOrException = DataOrException<Item, Boolean, Exception>()
//    suspend fun getBookInfo(bookId: String): DataOrException<Item, Boolean, Exception> {
//        try {
//            bookInfoDataOrException.loading = true
//            bookInfoDataOrException.data = booksApi.getBookInfo(bookId)
//            if (bookInfoDataOrException.data.toString()
//                    .isNotEmpty()
//            ) bookInfoDataOrException.loading = false
//            bookInfoDataOrException.loading = false
//        } catch (ex: Exception) {
//            bookInfoDataOrException.loading = false
//            bookInfoDataOrException.e = ex
//        }
//        return bookInfoDataOrException
//    }
//}

class BookRepository @Inject constructor(private val api: BooksApi) {
    suspend fun getBooks(searchQuery: String): Resource<List<Item>> {
        return try {
            Resource.Loading(data = true)
            val itemList = api.getAllBooks(searchQuery).items
            if (itemList.isNotEmpty()) Resource.Loading(data = false)
            Resource.Success(data = itemList)
        } catch (exception: Exception) {
            Resource.Error(message = exception.message.toString())
        }
    }

    suspend fun getBookInfo(bookId: String): Resource<Item> {
        val response = try {
            Resource.Loading(data = true)
            api.getBookInfo(bookId)
        } catch (exception: Exception) {
            return Resource.Error(message = "An error occurred ${exception.message.toString()}")
        }
        Resource.Loading(data = false)
        return Resource.Success(data = response)
    }
}