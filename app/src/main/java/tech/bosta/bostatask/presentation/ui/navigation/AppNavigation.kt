package tech.bosta.bostatask.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import tech.bosta.bostatask.presentation.composable.SlideInOutAnimation
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
            SlideInOutAnimation{ ProfileScreen(navController) }
        }

        composable(
            route = NavigationGraph.AlbumsScreen.route+"/{albumId}/{title}",
        ) {
            val albumId = it.arguments?.getString("albumId")?.toInt()?: -1
            val title = it.arguments?.getString("title")
            SlideInOutAnimation{ AlbumScreen(navController, albumId, title) }
        }



    }
}

