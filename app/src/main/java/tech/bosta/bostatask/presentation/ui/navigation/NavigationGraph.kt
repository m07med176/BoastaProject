package tech.bosta.bostatask.presentation.ui.navigation

sealed class NavigationGraph(val route:String){
    object ProfileScreen:NavigationGraph(route = "profile_screen")
    object AlbumsScreen:NavigationGraph(route = "albums_screen")
}