package tech.bosta.bostatask.presentation.composable

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import tech.bosta.bostatask.R
import java.io.File

@Composable
fun ImageLoader(modifier:Modifier = Modifier, url:String, onReturnUrl:((String)->Unit)?=null, onClick:(String)->Unit) {

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
            onReturnUrl?.invoke(url)
        }
    )
}
@Composable
fun ImageViewer(
    imageUrl: String,
    onDismiss: () -> Unit,) {
    var scale by remember { mutableFloatStateOf(1f) }
    var url by remember { mutableStateOf("") }
    val shareImage = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { isGranted ->
    }
    val context = LocalContext.current

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        ImageLoader( modifier = Modifier
            .fillMaxSize()
            .graphicsLayer(scaleX = scale, scaleY = scale),url = imageUrl, onReturnUrl = {
            url = it
        }){

        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = { scale += 0.1f },
                modifier = Modifier.size(48.dp)
            ) { IconImageViewDialog(Icons.Default.KeyboardArrowUp) }

            IconButton(
                onClick = { scale -= 0.1f },
                modifier = Modifier.size(48.dp)
            ) { IconImageViewDialog(Icons.Default.KeyboardArrowDown) }
        }

        Row(modifier = Modifier.align(Alignment.TopEnd)) {

            IconButton(
                onClick = {
                    shareImage(context,shareImage,imageUri = url)
                },
                modifier = Modifier
                    .padding(16.dp)
            ) { IconImageViewDialog(Icons.Default.Share)  }

            Spacer(modifier = Modifier.width(10.dp))

            IconButton(
                onClick = { onDismiss() },
                modifier = Modifier
                    .padding(16.dp)
            ) { IconImageViewDialog(Icons.Default.Close) }

        }

    }
}


@Composable
fun IconImageViewDialog(imageVector: ImageVector) {
    Icon(imageVector = imageVector, contentDescription = null , tint = Color.White, modifier = Modifier.background(color = MaterialTheme.colorScheme.primary, shape = CircleShape))

}

fun shareImage(context:Context,activity:ManagedActivityResultLauncher<Intent,ActivityResult>, imageUri: String) {

    loadImageAndCache(context,imageUri){
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            type = "image/*"
            putExtra(Intent.EXTRA_STREAM, it)
        }
        val chooser = Intent.createChooser(intent, "Share Image")
        activity.launch(chooser)
    }


}

fun loadImageAndCache(context: Context, imageUrl: String,onError:( () -> Unit)?=null, onSuccess: (Uri) -> Unit) {
    Glide.with(context)
        .asFile()
        .load(imageUrl)
        .into(object : SimpleTarget<File>() {
            override fun onResourceReady(resource: File, transition: Transition<in File>?) {
                val cachedUri = Uri.fromFile(resource)
                onSuccess(cachedUri)
            }

            override fun onLoadFailed(errorDrawable: Drawable?) {
                onError?.invoke()
            }
        })
}