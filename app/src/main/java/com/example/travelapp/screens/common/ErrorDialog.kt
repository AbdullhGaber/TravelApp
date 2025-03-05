package com.example.travelapp.screens.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.travelapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ErrorDialog(
    modifier: Modifier = Modifier,
    text : String,
    onDismiss : () -> Unit,
){
    BasicAlertDialog(
        modifier = modifier.clip(RoundedCornerShape(20.dp)).background(Color.White),
        onDismissRequest = {
            onDismiss()
        },
        content = {
            Column(
                modifier = Modifier.padding(8.dp)
            ){
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Image(
                        modifier = Modifier.size(40.dp),
                        contentScale = ContentScale.Crop,
                        painter = painterResource(id = R.drawable.cross_error_icon),
                        contentDescription = stringResource(R.string.error_icon)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(text = stringResource(R.string.error) , fontSize = 20.sp)
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = text
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextButton(
                    modifier = Modifier.align(Alignment.End),
                    onClick = { onDismiss() }
                ){
                    Text(
                        text = stringResource(R.string.ok),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        },
    )
}

@Composable
@Preview
fun PreviewErrorDialog(){
    ErrorDialog(
        text = "Something went wrong",
        onDismiss = {},
    )
}