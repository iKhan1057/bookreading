package com.bookreaderapp.screens.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.bookreaderapp.components.InputField
import com.bookreaderapp.components.ReaderAppBar
import com.bookreaderapp.model.Item
import com.bookreaderapp.navigation.ReaderScreens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReaderBookSearch(
    navController: NavHostController,
    viewModel: BookSearchViewModel = hiltViewModel()
) {
    Scaffold(topBar = {
        ReaderAppBar(
            title = "Search",
            icon = Icons.Default.ArrowBack,
            showProfile = false,
            navController = navController
        ) {
            navController.navigate(ReaderScreens.ReaderHomeScreen.name)
        }
    }) { padding ->
        Surface(modifier = Modifier.padding(padding)) {
            Column {
                SearchForm(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(2.dp),
                    viewModel = viewModel
                ) { query ->
                    viewModel.searchBooks(query)
                }
                Spacer(modifier = Modifier.height(13.dp))
                BookList(navController = navController, viewModel)
            }
        }
    }
}

@Composable
fun BookList(navController: NavHostController, viewModel: BookSearchViewModel) {
    val listOfBooks =viewModel.list
    if (viewModel.isLoading) {
        Row(
            modifier = Modifier.padding(end = 2.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically) {
            LinearProgressIndicator()
            Text(text = "Loading...")
        }
    } else {
        LazyColumn(modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(all = 16.dp)) {
            items(items = listOfBooks) { Book ->
                BookRow(Book, navController)
            }
        }
    }

//    val listOfBooks = listOf(
//        MBook(
//            id = "dadfa",
//            title = "Android Development",
//            authors = "Mark L Murpphy",
//            notes = null,
//            photoUrl = "https://cdn-media-1.freecodecamp.org/images/vR7aWSla12f76YFhyeZ6oracp62orQxC5oVD"
//        ),
//        MBook(
//            id = "dadfa",
//            title = "Starting with Android",
//            authors = "Dr M.M. Sharma",
//            notes = null,
//            photoUrl = "https://images-na.ssl-images-amazon.com/images/I/610Y5U1huwL.jpg"
//        ),
//        MBook(
//            id = "dadfa",
//            title = "Android Apprentice",
//            authors = "Raywenderlick Team",
//            notes = null,
//            photoUrl = "https://assets.alexandria.raywenderlich.com/books/aa/images/6ed47f2833ff6684a1467d7ec76f8ab1adb5f59b8575780cbd930991a6b5c240/w594.png"
//        ),
//        MBook(
//            id = "dadfa",
//            title = "Android App",
//            authors = "Michael Burton",
//            notes = null,
//            photoUrl = "https://images-na.ssl-images-amazon.com/images/I/51c3IlRqfVL.jpg"
//        ),
//        MBook(
//            id = "dadfa",
//            title = "Black Book",
//            authors = "Pradip Kothari",
//            notes = null,
//            photoUrl = "https://m.media-amazon.com/images/I/51gIKUrTO3L.jpg"
//        )
//    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookRow(book: Item, navController: NavHostController) {
    ElevatedCard(
        modifier = Modifier
            .clickable {
                navController.navigate(ReaderScreens.DetailsScreen.name + "/${book.id}")
            }
            .fillMaxWidth()
            .height(100.dp)
            .padding(3.dp),
        shape = RectangleShape
    ) {
        Row(
            modifier = Modifier.padding(5.dp),
            verticalAlignment = Alignment.Top
       ) {
            val imageUrl: String = if (book.volumeInfo.imageLinks.smallThumbnail.isEmpty())
                "https://images.unsplash.com/photo-1541963463532-d68292c34b19?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=80&q=80"
            else {
                book.volumeInfo.imageLinks.smallThumbnail
            }
            Image(
                painter = rememberImagePainter(data = imageUrl),
                contentDescription = "book image",
                modifier = Modifier
                    .width(80.dp)
                    .fillMaxHeight()
                    .padding(end = 4.dp),
            )
            Column {
                Text(text = book.volumeInfo.title,
                    overflow = TextOverflow.Ellipsis)

                Text(
                    text = "Author: ${book.volumeInfo.authors}",
                    overflow = TextOverflow.Clip,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.labelSmall)

                Text(
                    text = "Date: ${book.volumeInfo.publishedDate}",
                    overflow = TextOverflow.Clip,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.labelSmall)

                Text(
                    text = "${book.volumeInfo.categories}",
                    overflow = TextOverflow.Clip,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.labelSmall)
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchForm(
    modifier: Modifier = Modifier,
    viewModel: BookSearchViewModel,
    loading: Boolean = false,
    hint: String = "Search",
    onSearch: (String) -> Unit = {}
) {
    Column {
        val searchQueryState = rememberSaveable { mutableStateOf("") }
        val keyboardController = LocalSoftwareKeyboardController.current
        val valid = remember(searchQueryState.value) {
            searchQueryState.value.trim().isNotEmpty()
        }
        InputField(valueState = searchQueryState,
            labelId = "Search",
            enabled = true,
            imeAction = ImeAction.Done,
            onAction = KeyboardActions {
                if (!valid) return@KeyboardActions
                onSearch(searchQueryState.value.trim())
                searchQueryState.value = ""
                keyboardController?.hide()
            })
    }
}
