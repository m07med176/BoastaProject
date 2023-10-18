package tech.bosta.bostatask.presentation.ui.albumscreen

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage

@Composable
fun AlbumScreen(
    navController: NavHostController?=null,
    albumId:Int,
    viewModel: AlbumViewModel = hiltViewModel()
) {

    LaunchedEffect(true){
        viewModel.onEvent(AlbumsEventUI.RequestPhotos(albumId))
    }

    val photos = viewModel.statePhotos.collectAsState().value?.data
    LazyVerticalGrid(columns = GridCells.Fixed(3)){
        items(photos?: emptyList()){
            AsyncImage(model = it.url, contentDescription = it.title,modifier = Modifier.size(80.dp).padding(5.dp))
        }
    }
}

@Composable
@Preview(name = "AlbumScreen")
private fun AlbumScreenScreenPreview() {
    AlbumScreen(albumId = 4)
}

