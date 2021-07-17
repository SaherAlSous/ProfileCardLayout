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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.transform.CircleCropTransformation
import com.google.accompanist.coil.rememberCoilPainter
import com.saher.profilecardlayout.ui.theme.ProfileCardLayoutTheme
import com.saher.profilecardlayout.ui.theme.lightGreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProfileCardLayoutTheme{
                UserListScreen()
            }

        }
    }
}

@Composable
fun UserListScreen(mainViewModel: MainViewModel = MainViewModel()) {
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
            ProfilePicture(userProfile.pictureUrl, userProfile.status, 72.dp)
            ProfileContent(userProfile.name, userProfile.status, Alignment.Start)
        }
    }
}

@Composable
fun ProfilePicture(drawableId: String, onlineStatus: Boolean, imageSize: Dp) {
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
            painter = rememberCoilPainter(
                request= drawableId,
            requestBuilder = {
                transformations(CircleCropTransformation())
            }),
            contentDescription = "User Image",
            modifier = Modifier.size(imageSize),
        )
    }
}

@Composable
fun ProfileContent(name: String, onlineStatus: Boolean, alignment: Alignment.Horizontal) {
    Column(
        modifier =
        Modifier
            .padding(8.dp),
        horizontalAlignment = alignment
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

/**
 *Creating another Composable main screen to use it with Navigation
 */
@Composable
fun UserProfileDetailsScreen(userProfile: UserProfile = userProfileList[0]) {
    /**
     * We can use [Scaffold] to pass an AppBar,
     * we add the main Surface within it.
     */
    Scaffold(topBar = { AppBar() }) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            //color = Color.LightGray We used the color from the Theme.kt file
        ) {
            /**
             * You can reuse composables and customize them.
             */
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                ProfilePicture(userProfile.pictureUrl, userProfile.status, 250.dp)
                ProfileContent(userProfile.name, userProfile.status,Alignment.CenterHorizontally)
            }

        }
    }
}

//Creating another preview for the second composables.
@Preview(showBackground = true)
@Composable
fun UserProfileDetailPreview() {
    UserProfileDetailsScreen()
}

@Preview(showBackground = true)
@Composable
fun UserListPreview() {
    ProfileCardLayoutTheme {
        UserListScreen()
    }
}