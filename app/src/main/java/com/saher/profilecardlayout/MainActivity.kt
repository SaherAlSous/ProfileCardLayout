package com.saher.profilecardlayout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.saher.profilecardlayout.ui.theme.ProfileCardLayoutTheme
import com.saher.profilecardlayout.ui.theme.lightGreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProfileCardLayoutTheme{
                MainScreen()
            }

        }
    }
}

@Composable
fun MainScreen() {
    /**
     * We can use [Scaffold] to pass an AppBar,
     * we add the main Surface within it.
     */
    Scaffold(topBar = { AppBar() }) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            //color = Color.LightGray We used the color from the Theme.kt file
        ) {
            ProfileCard()
        }
    }
}

@Composable
fun AppBar(){
    TopAppBar(
        navigationIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_home_24),
                contentDescription = "Home",
                modifier = Modifier.padding(horizontal = 12.dp)
            )
        },
        title = {
            Text(
                text = "Messaging Application Users"
            )
        }
    )
}

@Composable
fun ProfileCard() {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .wrapContentHeight(
                align = Alignment.Top
            ),
        elevation = 8.dp,
        backgroundColor = Color.White // Uses Surface color by default, so we have to override it.
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            ProfilePicture()
            ProfileContent()
        }
    }
}

@Composable
fun ProfilePicture() {
    /**
     * We wrap it in a [Card] to use its properties
     */
    Card(
        shape = CircleShape,
        border = BorderStroke(width = 2.dp, color = lightGreen  /* We can use it this way as well MaterialTheme.colors.lightGreen */),
        modifier = Modifier.padding(16.dp),
        elevation = 4.dp
    ) {
        Image(
            painter = painterResource(id = R.drawable.hala),
            contentDescription = "Test Image",
            modifier = Modifier.size(72.dp),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun ProfileContent() {
    Column(
        modifier =
        Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "Hala Atamleh",
            style = MaterialTheme.typography.h5
        )
        Text(
            text = "Active Now",
            style = MaterialTheme.typography.body2,
            modifier = Modifier.alpha(0.5f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ProfileCardLayoutTheme {
        MainScreen()
    }
}