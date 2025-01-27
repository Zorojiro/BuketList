package com.trial.bucketlist

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.DismissDirection
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.trial.bucketlist.data.NWish


@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeView(
    viewModel: WishViewModel,
    navController: NavController
) {
    val authState = viewModel.authState.observeAsState()

    LaunchedEffect(key1 = authState.value) {
        when(authState.value){
            is AuthState.Unauthenticated -> navController.navigate("login")
            else -> Unit
        }
    }

    val context = LocalContext.current

    Scaffold (
        topBar =
        { AppBarView(
            title = "Bucket List",
            wishViewModel = viewModel)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Screen.AddScreen.route + "/0L")},
                modifier = Modifier.padding(all = 20.dp),
                contentColor = Color.White,
                backgroundColor = Color.Black,

            )
            {
                Icon(imageVector = Icons.Default.Add, contentDescription = null )

            }
        }
    ){
        val wishlist = viewModel.getAllWishes.collectAsState(initial = listOf())
        LazyColumn (
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ){
            items(wishlist.value){
                wish->
                val dismissState = rememberDismissState(
                    confirmStateChange = {
//
                            viewModel.deleteWish(wish)
//
                        false
                    }
                )
                
                SwipeToDismiss(
                    state = dismissState,
                    background = {
                        val color by animateColorAsState(
                            if (dismissState.dismissDirection ==
                                DismissDirection.EndToStart
                            ) Color.Red else Color.Transparent, label = ""
                        )

                        val alignment = Alignment.CenterEnd
                        Box(
                            Modifier
                                .fillMaxSize()
                                .background(color),
                            contentAlignment = alignment
                        ) {
                            Icon(imageVector =Icons.Default.Delete,
                                contentDescription ="Delete Icon",
                                tint = Color.White)
                        }
                    },
                    dismissContent = {
                        WishItem(wish = wish) {
                            val id = wish.id
                            navController.navigate(Screen.AddScreen.route + "/$id")
                        }
                    },
                    dismissThresholds = { FractionalThreshold(0.25f) },
                    directions = setOf(DismissDirection.EndToStart),
                )
                
                
            }
        }
    }
}

@Composable
fun WishItem(wish: NWish, onClick: () -> Unit) {
    Card (
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 8.dp)
            .clickable { onClick() },
        elevation = 10.dp,
        backgroundColor = Color.White

    ){
        Column (
            modifier = Modifier.padding(16.dp)){
            Text(text = wish.title, fontWeight = FontWeight.ExtraBold)
            Text(text = wish.description, fontWeight = FontWeight.Normal)
        }

    }

}