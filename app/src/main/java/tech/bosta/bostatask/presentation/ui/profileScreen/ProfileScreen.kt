package tech.bosta.bostatask.presentation.ui.profileScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tech.bosta.bostatask.R
import tech.bosta.bostatask.domain.entities.AlbumResponseItem
import tech.bosta.bostatask.domain.entities.UsersResponseItem
import tech.bosta.bostatask.presentation.composable.AppTextView
import tech.bosta.bostatask.presentation.composable.LineDivider
import tech.bosta.bostatask.presentation.composable.LottieStateUI
import tech.bosta.bostatask.presentation.composable.StateOfData
import tech.bosta.bostatask.presentation.ui.navigation.NavigationGraph

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
        .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally){
        Spacer(modifier = Modifier.height(10.dp))

        AppTextView(value = stringResource(R.string.profile), modifier = Modifier.align(Alignment.Start))

        Spacer(modifier = Modifier.height(20.dp))
        if (user?.error!=null){
            Text(text = stringResource(R.string.there_is_no_data), style = TextStyle(fontSize = 18.sp), modifier = Modifier.align(Alignment.Start))
        }else if(user?.loading == true){
            LinearProgressIndicator()
        }else if(user?.data!=null){
            ProfileCard(modifier = Modifier.align(Alignment.Start),user.data)

        }
            if(user?.error != null) {
            LottieStateUI(
                modifier = Modifier.fillMaxSize(),
                type = StateOfData.Error,
                message = albums.value?.error
            )
            }
            else if (albums.value?.loading == true) {
                LottieStateUI(
                    modifier = Modifier.fillMaxSize(),
                    type = StateOfData.Loading,
                    message = stringResource(
                        R.string.still_fetch_data
                    )
                )
            }
            else if(albums.value?.data!=null){
                val data = albums.value?.data ?: emptyList()
                if (data.isEmpty()){
                    LottieStateUI(modifier = Modifier.fillMaxSize(), type = StateOfData.Loading, message = stringResource(R.string.there_is_no_data))

                }
                else{
                    LazyColumn {
                        item {
                            Text(text = stringResource(R.string.my_albums), style = TextStyle(fontSize = 20.sp), modifier = Modifier.padding(10.dp))
                        }

                        items(albums.value?.data ?: emptyList()) {
                            AlbumItem(it) {
                                navController?.navigate(NavigationGraph.AlbumsScreen.route + "/${it.id}/${it.title}")
                            }
                        }
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
fun ProfileCard(modifier: Modifier,user:UsersResponseItem) {
    Column(modifier = modifier.fillMaxWidth() ){
        Text(text = user.name, style = TextStyle(fontSize = 18.sp))
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = user.toStringAddress(), style = TextStyle(fontSize = 12.sp))
    }
}

@Composable
fun LazyItemScope.AlbumItem(model:AlbumResponseItem,onClick:()->Unit) {
    LineDivider()
    Column(modifier = Modifier.clickable { onClick() }) {
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = model.title)
        Spacer(modifier = Modifier.height(10.dp))
    }
}