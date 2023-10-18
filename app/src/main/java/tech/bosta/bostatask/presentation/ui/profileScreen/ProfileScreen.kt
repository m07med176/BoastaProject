package tech.bosta.bostatask.presentation.ui.profileScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import tech.bosta.bostatask.domain.entities.AlbumResponseItem
import tech.bosta.bostatask.domain.entities.UsersResponseItem
import tech.bosta.bostatask.presentation.ui.album.NavigationGraph

@Composable
fun ProfileScreen(
    navController: NavHostController?=null,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    LaunchedEffect(true){
        viewModel.onEvent(ProfileEventUI.RequestRandomUser)
    }

    val user = viewModel.stateUser.collectAsState().value
    val albums = viewModel.stateAlbums.collectAsState()

    Column(modifier = Modifier
        .padding(15.dp)
        .fillMaxSize(), horizontalAlignment = Alignment.Start){
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Profile", style = TextStyle(fontSize = 23.sp, fontWeight = FontWeight(800)))
        Spacer(modifier = Modifier.height(20.dp))
        user?.data?.let { ProfileCard(it) }
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "My Albums", style = TextStyle(fontSize = 20.sp))
        Spacer(modifier = Modifier.height(10.dp))
        LazyColumn {
            items(albums.value?.data ?: emptyList()) {
                AlbumItem(it){
                    navController?.navigate(NavigationGraph.AlbumsScreen.route+"/${it.id}")
                }
            }
        }
    }
}

@Composable
@Preview(name = "AlbumsScreen")
private fun AlbumsScreenScreenPreview() {
    ProfileScreen()
}


@Composable
fun ProfileCard(user:UsersResponseItem) {
    Text(text = user.name, style = TextStyle(fontSize = 18.sp))
    Spacer(modifier = Modifier.height(10.dp))
    Text(text = user.toStringAddress(), style = TextStyle(fontSize = 12.sp))
}

@Composable
fun LazyItemScope.AlbumItem(model:AlbumResponseItem,onClick:()->Unit) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(1.dp)
        .background(Color.Gray))
    Column(modifier = Modifier.clickable { onClick() }) {
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = model.title)
        Spacer(modifier = Modifier.height(10.dp))
    }

}