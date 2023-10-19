package tech.bosta.bostatask.presentation.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import tech.bosta.bostatask.R
import tech.bosta.bostatask.presentation.ui.albumscreen.AlbumsEventUI

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTextField(onValueChanged:(String)->Unit) {
    var search: String by remember { mutableStateOf("") }
    TextField(
        value = search, onValueChange = {
            search = it
            onValueChanged(it)
        },
        modifier = Modifier
            .padding(0.dp)
            .fillMaxWidth(),
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, tint = Color.Gray, contentDescription = null)
        },
        colors = TextFieldDefaults.textFieldColors(
            disabledIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        shape = RoundedCornerShape(15.dp),
        placeholder = {
            Text(text = stringResource(R.string.search_in_images),modifier = Modifier.fillMaxWidth(), color = Color.Gray)
        }
    )

}