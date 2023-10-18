package tech.bosta.bostatask.presentation.ui.album

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import tech.bosta.bostatask.presentation.ui.albumscreen.AlbumScreen
import tech.bosta.bostatask.presentation.ui.profileScreen.ProfileScreen


@Composable
fun AppNavigation(
    navController: NavHostController,
    startDestination: String = NavigationGraph.ProfileScreen.route,
    ) {
    NavHost(navController = navController, startDestination = startDestination) {

        composable(
            route = NavigationGraph.ProfileScreen.route,
        ) {
            ProfileScreen(navController)
        }

        composable(
            route = NavigationGraph.AlbumsScreen.route+"/{albumId}",
        ) {
            val albumId = it.arguments?.getInt("albumId") ?: -1
            AlbumScreen(navController,albumId)
        }



    }
}

