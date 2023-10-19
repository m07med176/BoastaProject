package tech.bosta.bostatask.presentation.composable

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp

@Composable
fun AppTextView(modifier:Modifier = Modifier,value:String) {
    Text(
        text = value,
        style = TextStyle(fontSize = 23.sp, fontWeight = FontWeight(800)),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        softWrap = true,
        modifier = modifier,

    )
}