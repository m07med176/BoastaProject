package tech.bosta.bostatask.presentation.composable

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import tech.bosta.bostatask.R

@Composable
fun ImageLoader(modifier:Modifier = Modifier,url:String,onReturnUri:((Uri)->Unit)?=null,onClick:(String)->Unit) {

    SubcomposeAsyncImage(
        modifier = modifier.clickable { onClick(url) },
        model =url, contentDescription = null,
        loading = {
            Box(modifier = modifier, contentAlignment =  Alignment.Center){
                CircularProgressIndicator(modifier = Modifier
                    .size(60.dp)
                    .padding(10.dp))
            }

        },
        error = {
            Image(painter = painterResource(id = R.drawable.placeholder), contentDescription = null)
        },
        onSuccess = {
          val uri:Uri =  it.result.request.data as Uri
            onReturnUri?.let {
                onReturnUri(uri)
            }
        }
    )
}
@Composable
fun ImageViewer(
    imageUrl: String,
    onDismiss: () -> Unit,) {
    var scale by remember { mutableFloatStateOf(1f) }
    var uri:Uri? by remember { mutableStateOf<Uri?>(null) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        ImageLoader( modifier = Modifier
            .fillMaxSize()
            .graphicsLayer(scaleX = scale, scaleY = scale),url = imageUrl, onReturnUri = {
            uri = it
        }){}

        // Zoom controls
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = { scale += 0.1f },
                modifier = Modifier.size(48.dp)
            ) {
                IconImageViewDialog(Icons.Default.KeyboardArrowUp)

            }

            IconButton(
                onClick = { scale -= 0.1f },
                modifier = Modifier.size(48.dp)
            ) {
                IconImageViewDialog(Icons.Default.KeyboardArrowDown)

            }
        }

        Row(modifier = Modifier
            .align(Alignment.TopEnd)) {
            IconButton(
                onClick = { onDismiss() },
                modifier = Modifier
                    .padding(16.dp)
            ) {
                IconImageViewDialog(Icons.Default.Close)

            }
            Spacer(modifier = Modifier.width(10.dp))
//            val shareImage = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { isGranted ->
//            }
            IconButton(
                onClick = {
//                    uri?.let { shareImage(shareImage,imageUri = it.toString()) }

                },
                modifier = Modifier
                    .padding(16.dp)
            ) {
                IconImageViewDialog(Icons.Default.Share)
            }
        }

    }
}


@Composable
fun IconImageViewDialog(imageVector: ImageVector) {
    Icon(imageVector = imageVector, contentDescription = null , tint = Color.White, modifier = Modifier.background(color = MaterialTheme.colorScheme.primary, shape = CircleShape))

}

//fun shareImage(activity:ManagedActivityResultLauncher<Intent,ActivityResult>, imageUri: String) {
//
//    val intent = Intent().apply {
//        action = Intent.ACTION_SEND
//        type = "image/*"
//        putExtra(Intent.EXTRA_STREAM, Uri.parse(imageUri))
//    }
//    val chooser = Intent.createChooser(intent, "Share Image")
//    activity.launch(chooser)
//
//}