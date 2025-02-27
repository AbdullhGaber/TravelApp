package com.example.travelapp.screens.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun PrimaryButton(
    modifier : Modifier = Modifier,
    onClick : () -> Unit = {},
    enabled : Boolean = true,
    text : String = ""
){
    Button(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled,
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 20.dp
        ),
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
            contentColor = MaterialTheme.colorScheme.secondary,
            disabledContainerColor = Color.LightGray,
            disabledContentColor = Color.Gray.copy(alpha = 0.2f)
        )
    ){
           Text(text = text)
    }
}

@Composable
@Preview(showBackground = true , backgroundColor = 0xFFFFFFFF)
fun PreviewPrimaryButton(){
    PrimaryButton(
        modifier = Modifier.fillMaxWidth(),
        text = "Sign Up"
    )
}