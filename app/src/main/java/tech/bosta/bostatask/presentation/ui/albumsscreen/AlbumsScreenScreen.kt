package tech.bosta.bostatask.presentation.ui.albumsscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import tech.bosta.bostatask.domain.entities.UsersResponse
import tech.bosta.bostatask.domain.entities.UsersResponseItem
import tech.bosta.bostatask.presentation.utils.UIScreenState

@Composable
fun AlbumsScreenScreen(
    actions: AlbumsScreenActions?=null,
    viewModel: AlbumsScreenViewModel = hiltViewModel()
) {
    LaunchedEffect(true){
        viewModel.onEvent(AlbumsEventUI.RequestRandomUser)
    }

    val user = viewModel.stateUser.collectAsState().value
    val albums = viewModel.stateAlbums.collectAsState()



    Column(modifier = Modifier.padding(10.dp).fillMaxSize(), horizontalAlignment = Alignment.Start){
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Profile", style = TextStyle(fontSize = 21.sp))
        Spacer(modifier = Modifier.height(20.dp))
        user?.data?.let { ProfileCard(it) }


        LazyColumn {
            items(albums.value?.data ?: emptyList()) {

                Text(text = it.title)
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

@Composable
@Preview(name = "AlbumsScreen")
private fun AlbumsScreenScreenPreview() {
    AlbumsScreenScreen(
        actions = AlbumsScreenActions(),
    )
}


@Composable
fun ProfileCard(user:UsersResponseItem) {
    Text(text = user.name, style = TextStyle(fontSize = 18.sp))
    Spacer(modifier = Modifier.height(10.dp))
    Text(text = user.toStringAddress(), style = TextStyle(fontSize = 12.sp))
}