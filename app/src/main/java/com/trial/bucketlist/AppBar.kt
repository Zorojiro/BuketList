package com.trial.bucketlist

import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun AppBarView(
    title: String,
    onBackNavClicked:  () -> Unit = {},
    wishViewModel: WishViewModel
) {
    val navigationIcon: (@Composable () -> Unit)? = {
         if(!title.contains("Bucket List")){
             IconButton(onClick = {onBackNavClicked()})
             {
                 Icon(
                     imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                     contentDescription = "Back",
                     tint = Color.White
                 )
             }
         }
        else{
             IconButton(onClick = {(wishViewModel.signout())}) {
                 Icon(painter = painterResource(id = R.drawable.topappbaricon),
                 contentDescription =null,
                 tint = Color.Unspecified,
                 modifier = Modifier.size(60.dp).padding(start = 10.dp)
             )
             }
         }
    }



    TopAppBar(
        title = {
            Text(
                text = title,
                color = colorResource(id = R.color.white),
                modifier = Modifier
                    .heightIn(max = 24.dp)
            )
        },
            elevation = 3.dp,
            backgroundColor = colorResource(id = R.color.app_bar_color),
            navigationIcon = navigationIcon

    )
            
}