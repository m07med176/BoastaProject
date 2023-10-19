package tech.bosta.bostatask.presentation.composable

import androidx.annotation.RawRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import tech.bosta.bostatask.R

enum class StateOfData(val color:Color,@RawRes val rowId:Int){
    Error(Color.Red,R.raw.network),
    NoData(Color.Black,R.raw.empty),
    Loading(Color.Blue,R.raw.loading)
}

@Composable
fun LottieStateUI(modifier: Modifier,message:String?=null,type:StateOfData) {
    val isPlaying by remember {
        mutableStateOf(true)
    }

    val speed by remember {
        mutableFloatStateOf(1f)
    }

    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(type.rowId)
    )


    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever,
        isPlaying = isPlaying,
        speed = speed,
        restartOnPlay = false
    )

    Box(modifier = modifier, contentAlignment = Alignment.Center){

        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Bottom) {

            message?.let {
                Text(
                    text = it,
                    color = type.color,
                    modifier = Modifier.padding(10.dp),
                    fontSize = 15.sp
                )
            }
        }
        LottieAnimation(
            composition,
            progress,
        )
    }

}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun LottieFilePreview() {
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        LottieStateUI(modifier = Modifier.size(150.dp), message = "Loading", type = StateOfData.Loading)
        Spacer(modifier = Modifier.height(10.dp))
        LottieStateUI(modifier = Modifier.size(150.dp), message = "No Data", type = StateOfData.NoData)
        Spacer(modifier = Modifier.height(10.dp))
        LottieStateUI(modifier = Modifier.size(150.dp), message = "No network", type = StateOfData.Error)
    }
}