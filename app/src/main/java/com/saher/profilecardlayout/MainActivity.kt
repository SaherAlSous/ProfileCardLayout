package com.saher.profilecardlayout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
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
fun MainScreen(mainViewModel: MainViewModel = MainViewModel()) {
    /**
     * We can use [Scaffold] to pass an AppBar,
     * we add the main Surface within it.
     */
    Scaffold(topBar = { AppBar() }) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            //color = Color.LightGray We used the color from the Theme.kt file
        ) {
            LazyColumn() {
                val users = mainViewModel.userProfileList
                items(users){ user ->
                    ProfileCard(userProfile = user)
                }
                //We can pass item, as a separator or itemIndexed

            }
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
fun ProfileCard(userProfile: UserProfile) {
    Card( //We changed the default shape of Card to make a cut in the corner.
        modifier = Modifier
            .padding(top = 8.dp, bottom = 4.dp, start = 16.dp, end = 16.dp)
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
            ProfilePicture(userProfile.drawableId, userProfile.status)
            ProfileContent(userProfile.name, userProfile.status)
        }
    }
}

@Composable
fun ProfilePicture(drawableId: Int, onlineStatus: Boolean) {
    /**
     * We wrap it in a [Card] to use its properties
     */
    Card(
        shape = CircleShape,
        border = BorderStroke(width = 2.dp, color = if (onlineStatus) lightGreen else Color.Red  /* We can use it this way as well MaterialTheme.colors.lightGreen */),
        modifier = Modifier.padding(16.dp),
        elevation = 4.dp
    ) {
        Image(
            painter = painterResource(id = drawableId),
            contentDescription = "Test Image",
            modifier = Modifier.size(72.dp),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun ProfileContent(name: String, onlineStatus: Boolean) {
    Column(
        modifier =
        Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.h5,
            modifier = if (onlineStatus)  Modifier.alpha(1f) else  Modifier.alpha(0.5f)
        )
        Text(
            text = if (onlineStatus) "Active Now" else "Offline",
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