package com.example.travelapp.screens.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.example.travelapp.R

@Composable
fun TripTextField(
    modifier: Modifier = Modifier,
    placeholder : String = "",
    value : String = "",
    onValueChange : (String) -> Unit = {},
    isPasswordField : Boolean = false,
    isPhoneNumberField : Boolean = false,
    isPasswordVisible : Boolean = false,
    onPasswordVisibilityChange: (Boolean) -> Unit = {},
    isError : Boolean = false,
    errorMessage : String = ""
){
    TextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        isError = isError,
        placeholder = {
            Text(text = placeholder)
        },
        supportingText = {
            if(isError)
                Text(text = errorMessage , color = Color.Red)
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = if(isPhoneNumberField) KeyboardType.Phone else KeyboardType.Unspecified
        ),
        colors = TextFieldDefaults.colors(
            errorIndicatorColor = Color.Red,
            unfocusedTextColor = MaterialTheme.colorScheme.primaryContainer,
            focusedTextColor = MaterialTheme.colorScheme.primaryContainer,
            errorTextColor = MaterialTheme.colorScheme.primaryContainer,
            focusedIndicatorColor = MaterialTheme.colorScheme.primaryContainer,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.primaryContainer,
            cursorColor = MaterialTheme.colorScheme.primaryContainer,
            focusedContainerColor = Color.Transparent,
            errorContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent
        ),
        visualTransformation = if(isPasswordField && !isPasswordVisible) PasswordVisualTransformation() else VisualTransformation.None,
        suffix = {
            if(isPasswordField){
                Icon(
                    imageVector = if(isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                    contentDescription = stringResource(
                        R.string.password_visibility_icon
                    ),
                    modifier = Modifier.clickable {
                        onPasswordVisibilityChange(!isPasswordVisible)
                    }
                )
            }
        }
    )
}

@Composable
@Preview
fun PreviewTripTextField(){
    TripTextField(
        modifier = Modifier.fillMaxWidth(),
        placeholder = "Email"
    )
}