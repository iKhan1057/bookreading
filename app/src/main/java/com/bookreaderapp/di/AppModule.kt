package com.bookreaderapp.di

import com.bookreaderapp.network.BooksApi
import com.bookreaderapp.repository.BookRepository
import com.bookreaderapp.repository.FirebaseRepository
import com.bookreaderapp.utils.Constants.BASE_URL
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideBookRepos(booksApi: BooksApi) = BookRepository(booksApi)

    @Singleton
    @Provides
    fun provideBooksApi(): BooksApi =
        Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(BooksApi::class.java)

    @Singleton
    @Provides
    fun providesFireBookRepository() = FirebaseRepository(queryBook = FirebaseFirestore.getInstance().collection("books"))
}