package com.saher.profilecardlayout

import android.os.Bundle
import android.service.autofill.OnClickAction
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import coil.transform.CircleCropTransformation
import com.google.accompanist.coil.rememberCoilPainter
import com.saher.profilecardlayout.ui.theme.ProfileCardLayoutTheme
import com.saher.profilecardlayout.ui.theme.lightGreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProfileCardLayoutTheme {
                // UserListScreen() <-- after we add Navigation, we don't pass main screen. we pass navigation.
                UserApplication()
            }

        }
    }
}

//Navigation Management Composable
//https://developer.android.com/jetpack/compose/navigation
@Composable
fun UserApplication(mainViewModel: MainViewModel = MainViewModel()) { //Pass arguments from here.
    //every component have to be remembered as a state
    //Inistialize the controller
    val navController = rememberNavController()

    //Creating NavHost
    NavHost(navController = navController, startDestination = "users_List") {
        composable("users_List") {
            //Our first screen. the entry point
            UserListScreen(navController, mainViewModel)
        }
        composable(
            route = "user_details/{userId}",
            arguments = listOf(navArgument("userId") {
                type = NavType.IntType //Passing Integer
            })
        ) { NavBackStackEntry -> //this way we can access all the nav arguments
            UserProfileDetailsScreen(
                NavBackStackEntry.arguments!!.getInt("userId"),
                navController) // we added this controller to run the back button.
        }
    }

}

//Main Screen
@Composable
fun UserListScreen(navController: NavHostController?, mainViewModel: MainViewModel) {
    /**
     * We can use [Scaffold] to pass an AppBar,
     * we add the main Surface within it.
     */
    Scaffold(topBar = {
        AppBar(
            title = "Users List",
            icon = painterResource(id = R.drawable.ic_baseline_home_24)

        ) { } //no action for this page on home screen.

    }) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            //color = Color.LightGray We used the color from the Theme.kt file
        ) {
            LazyColumn() {
                val users = mainViewModel.userProfileList
                items(users) { user ->
                    ProfileCard(userProfile = user) {  //Clickable function that takes lambda
                        navController?.navigate("user_details/${user.id}") //passing ID
                    }
                }
                //We can pass item, as a separator or itemIndexed

            }
        }
    }
}

/**
 *Creating another Composable main screen to use it with Navigation
 */
@Composable
fun UserProfileDetailsScreen(userId: Int, navController: NavHostController?) {
    //getting clicked user with condition.
    val userProfile = userProfileList.first { userProfile -> userId == userProfile.id }

    /**
     * We can use [Scaffold] to pass an AppBar,
     * we add the main Surface within it.
     */
    Scaffold(topBar = {
        AppBar(
            title = userProfile.name,
            icon = painterResource(
                id = R.drawable.ic_baseline_arrow_back_24
            )
        ) { //back button pressed navigation action
            navController?.navigateUp() //popping the current composable to previous one.
        }
    }) {
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
                ProfileContent(userProfile.name, userProfile.status, Alignment.CenterHorizontally)
            }

        }
    }
}

@Composable
fun AppBar(
    title: String,
    icon: Painter,
    iconClickAction: () -> Unit
) { //adding back button with action.
    TopAppBar(
        navigationIcon = {
            Icon(
                painter = icon,//painterResource(id = R.drawable.ic_baseline_home_24),
                contentDescription = "Home",
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .clickable(onClick = { iconClickAction.invoke() })
            )
        },
        title = {
            Text(
                text = title //from Parameter
            )
        }
    )
}

@Composable
fun ProfileCard(userProfile: UserProfile, clickAction: () -> Unit) {
    Card( //We changed the default shape of Card to make a cut in the corner.
        modifier = Modifier
            .padding(top = 8.dp, bottom = 4.dp, start = 16.dp, end = 16.dp)
            .fillMaxWidth()
            .wrapContentHeight(
                align = Alignment.Top
            )
            .clickable { clickAction.invoke() }, //<-- moving the action to main composable.
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
        border = BorderStroke(
            width = 2.dp,
            color = if (onlineStatus) lightGreen else Color.Red  /* We can use it this way as well MaterialTheme.colors.lightGreen */
        ),
        modifier = Modifier.padding(16.dp),
        elevation = 4.dp
    ) {
        Image(
            painter = rememberCoilPainter(
                request = drawableId,
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
            modifier = if (onlineStatus) Modifier.alpha(1f) else Modifier.alpha(0.5f)
        )
        Text(
            text = if (onlineStatus) "Active Now" else "Offline",
            style = MaterialTheme.typography.body2,
            modifier = Modifier.alpha(0.5f)
        )
    }
}


//Creating another preview for the second composables.
@Preview(showBackground = true)
@Composable
fun UserProfileDetailPreview() {
    UserProfileDetailsScreen(userId = 0, navController = null)
}

@Preview(showBackground = true)
@Composable
fun UserListPreview() {
    ProfileCardLayoutTheme {
        UserListScreen(
            mainViewModel = MainViewModel(),
            navController = null
        ) //Even we added navigation, we keep preview the same.
    }
}