package tech.bosta.bostatask.presentation.ui.albumscreen

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import kotlinx.coroutines.delay
import tech.bosta.bostatask.R
import tech.bosta.bostatask.presentation.composable.AppTextView
import tech.bosta.bostatask.presentation.composable.ImageLoader
import tech.bosta.bostatask.presentation.composable.ImageViewer
import tech.bosta.bostatask.presentation.composable.LineDivider
import tech.bosta.bostatask.presentation.composable.LottieStateUI
import tech.bosta.bostatask.presentation.composable.SearchTextField
import tech.bosta.bostatask.presentation.composable.StateOfData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumScreen(
    navController: NavHostController? = null,
    albumId: Int,
    title: String?,
    viewModel: AlbumViewModel = hiltViewModel()
) {

    LaunchedEffect(true) {
        delay(1000)
        viewModel.onEvent(AlbumsEventUI.RequestPhotos(albumId))
    }

    var showDialog by remember { mutableStateOf(false to "") }
    val photos = viewModel.statePhotos.collectAsState().value

    Column(
        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(5.dp))
        title?.let {
            AppTextView(value = it)
        }

        Spacer(modifier = Modifier.height(2.dp))

        LineDivider()

        Spacer(modifier = Modifier.height(5.dp))

        SearchTextField {
            viewModel.onEvent(AlbumsEventUI.RequestSearchPhoto(it))

        }
        Spacer(modifier = Modifier.height(10.dp))

        if (photos?.error != null) {
            LottieStateUI(
                modifier = Modifier.fillMaxSize(),
                type = StateOfData.Error,
                message = photos.error
            )
        } else if (photos?.loading == true) {
            LottieStateUI(
                modifier = Modifier.fillMaxSize(),
                type = StateOfData.Loading,
                message = stringResource(
                    R.string.still_fetch_data
                )
            )
        }else if(photos?.data != null){
            val photosData = photos.data
            if (photosData.isEmpty()){
                LottieStateUI(
                    modifier = Modifier.fillMaxSize(),
                    type = StateOfData.NoData,
                    message = stringResource(
                        R.string.there_is_no_data
                    )
                )
            }else{
                LazyVerticalGrid(columns = GridCells.Fixed(3)) {
                    items(photosData) {
                        ImageLoader(modifier = Modifier.fillMaxSize(0.3f),it.url) {url->
                            showDialog = true to url
                        }
                    }
            }
            }
        }

    }

    if (showDialog.first) {
        Dialog(
            onDismissRequest = { showDialog = showDialog.copy(first = false ) }
        ) {
            ImageViewer(
                imageUrl = showDialog.second,
                onDismiss = { showDialog = showDialog.copy(first = false)  }
            )
        }
    }
}

@Composable
@Preview(name = "AlbumScreen")
private fun AlbumScreenScreenPreview() {
    AlbumScreen(albumId = 4, title = "this is title of album")
}

