package com.bookreaderapp.screens.home

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.bookreaderapp.components.FABContent
import com.bookreaderapp.components.ListCard
import com.bookreaderapp.components.ReaderAppBar
import com.bookreaderapp.components.TitleSection
import com.bookreaderapp.model.MBook
import com.bookreaderapp.navigation.ReaderScreens
import com.bookreaderapp.ui.theme.blueprint
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReaderHomeScreen(navController: NavHostController, viewModel: HomeScreenViewModel) {
    Scaffold(topBar = {
        ReaderAppBar(title = "A.Reader", navController = navController)
    },
        floatingActionButton = {
            FABContent {
                navController.navigate(ReaderScreens.SearchScreen.name)
            }
        }) { padding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            HomeContent(navController,viewModel)
        }
    }
}

@Composable
fun HomeContent(navController: NavHostController, viewModel: HomeScreenViewModel) {

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

    var listOfBooks = emptyList<MBook>()
    val currentUser = FirebaseAuth.getInstance().currentUser
    if (!viewModel.data.value.data.isNullOrEmpty()) {
        listOfBooks = viewModel.data.value.data!!.toList().filter { mBook ->
            mBook.userId == currentUser?.uid.toString()
        }
        Log.d("Books", "HomeContent: ${listOfBooks.toString()}")
    }


    val email = FirebaseAuth.getInstance().currentUser?.email
    val currentUserName = if (!email.isNullOrEmpty())
        FirebaseAuth.getInstance().currentUser?.email?.split("@")
            ?.get(0) else
        "N/A"
    Column(
        modifier = Modifier.padding(all = 10.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Row(modifier = Modifier.align(alignment = Alignment.Start)) {
            TitleSection(label = "Your reading\nactivity right now...")
            Spacer(modifier = Modifier.fillMaxWidth(0.7f))
            Column(modifier = Modifier.clickable {
                navController.navigate(ReaderScreens.ReaderStatsScreen.name)
            }) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Profile",
                    modifier = Modifier
                        .size(size = 45.dp)
                        .align(alignment = Alignment.CenterHorizontally),
                    tint = blueprint
                )
                Text(
                    text = currentUserName!!,
                    modifier = Modifier
                        .padding(all = 2.dp)
                        .align(alignment = Alignment.CenterHorizontally),
                    style = MaterialTheme.typography.titleMedium,
                    color = blueprint,
                    fontSize = 15.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Clip
                )
                Divider()
            }
        }
        ReadingRightNowArea(listOfBooks = listOfBooks, navController = navController)
        TitleSection(label = "Reading List")
        BookListArea(
            listOfBooks = listOfBooks,
            navController = navController
        )
    }


}

@Composable
fun BookListArea(
    listOfBooks: List<MBook>,
    navController: NavController
) {
    val addedBooks = listOfBooks.filter { mBook ->
        mBook.startedReading == null && mBook.finishedReading == null
    }
    HorizontalScrollableComponent(addedBooks) {
        navController.navigate(ReaderScreens.UpdateScreen.name + "/$it")
    }
}

@Composable
fun HorizontalScrollableComponent(
    listOfBooks: List<MBook>,
    viewModel: HomeScreenViewModel = hiltViewModel(),
    onCardPressed: (String) -> Unit
) {
    val scrollState = rememberScrollState()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(280.dp)
            .horizontalScroll(scrollState)
    ) {
        for (book in listOfBooks) {
            ListCard(book) {
                onCardPressed(book.googleBookId.toString())
            }
        }
//        if (viewModel.data.value.loading == true) {
//            LinearProgressIndicator()
//        } else {
//            if (listOfBooks.isNullOrEmpty()) {
//                Surface(modifier = Modifier.padding(23.dp)) {
//                    Text(
//                        text = "No books found. Add a Book",
//                        style = TextStyle(
//                            color = Color.Red.copy(alpha = 0.4f),
//                            fontWeight = FontWeight.Bold,
//                            fontSize = 14.sp
//                        )
//                    )
//                }
//            } else {
//                for (book in listOfBooks) {
//                    ListCard(book) {
//                        onCardPressed(book.id.toString()/*book.googleBookId.toString()*/)
//                    }
//                }
//            }
//        }
    }
}


@Composable
fun ReadingRightNowArea(
    listOfBooks: List<MBook>,
    navController: NavController
) {
    //Filter books by reading now
    val readingNowList = listOfBooks.filter { mBook ->
        mBook.startedReading != null && mBook.finishedReading == null
    }
    HorizontalScrollableComponent(readingNowList) {
        Log.d("TAG", "BoolListArea: $it")
        navController.navigate(ReaderScreens.UpdateScreen.name + "/$it")
    }
}

