package com.example.travelapp.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.data.uitls.Resource
import com.example.travelapp.R
import com.example.travelapp.screens.common.ErrorDialog
import com.example.travelapp.screens.common.PrimaryButton
import com.example.travelapp.screens.common.TripCircularProgressIndicator
import com.example.travelapp.screens.common.TripTextField
import com.example.travelapp.utils.AuthValidator

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    navigateToHome : () -> Unit,
    navigateToSignUp : () -> Unit
) {
    val authStateFlow = viewModel.authStateFlow.collectAsState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondary)
    ) {
        if (authStateFlow.value is Resource.Loading) {
            TripCircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }

        if (authStateFlow.value is Resource.Failure) {
            ErrorDialog(
                text = authStateFlow.value.message.toString(),
                onDismiss = {
                    viewModel.onEvent(LoginScreenEvents.ClearAuthFlowState)
                }
            )
        }

        LaunchedEffect(Unit) {
            viewModel.navigationSharedFlow.collect { navigate ->
                if (navigate) {
                    navigateToHome()
                }
            }
        }

        LoginScreenContent(
            viewModel = viewModel,
            navigateToSignUp = navigateToSignUp
        )
    }
}

@Composable
fun LoginScreenContent(
    viewModel: LoginViewModel,
    navigateToSignUp: () -> Unit
){
    Column {
        LoginHeader()
        Spacer(modifier = Modifier.height(48.dp))

        LoginForm(viewModel = viewModel)
        Spacer(modifier = Modifier.height(16.dp))

        LoginFooter(navigateToSignUp)
    }
}

@Composable
fun LoginHeader(){
    Box(
        modifier = Modifier
            .fillMaxHeight(0.3f)
            .fillMaxWidth()
    ) {
        Image(
            modifier = Modifier.matchParentSize(),
            painter = painterResource(id = R.drawable.auth_screen_header),
            contentDescription = stringResource(R.string.auth_screen_header),
            contentScale = ContentScale.Crop,
        )

        Text(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(vertical = 4.dp),
            text = stringResource(R.string.sign_in),
            fontSize = 18.sp,
            color = Color.White
        )
    }
}

@Composable
fun LoginForm(viewModel: LoginViewModel){
    Column {
        TripTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            value = viewModel.emailState.value,
            errorMessage = viewModel.emailErrorState.value,
            onValueChange = {
                viewModel.emailState.value = it
                viewModel.emailErrorState.value = ""
                viewModel.authValidator.isEmailValid(
                    email = it,
                    emailError = viewModel.emailErrorState
                )
            },
            isError = viewModel.emailErrorState.value.isNotEmpty(),
            placeholder = stringResource(R.string.email),
        )

        Spacer(modifier = Modifier.height(8.dp))

        TripTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            value = viewModel.passwordState.value,
            onValueChange = {
                viewModel.passwordState.value = it
                viewModel.passwordErrorState.value = ""
                viewModel.authValidator.isPasswordValid(
                    password = it,
                    passwordError = viewModel.passwordErrorState
                )
            },
            errorMessage = viewModel.passwordErrorState.value,
            isError = viewModel.passwordErrorState.value.isNotEmpty(),
            placeholder = stringResource(R.string.password),
            isPasswordVisible = viewModel.isPasswordVisibleState.value,
            isPasswordField = true,
            onPasswordVisibilityChange = {
                viewModel.isPasswordVisibleState.value = it
            }
        )

        PrimaryButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 48.dp),
            text = stringResource(id = R.string.sign_in).uppercase(),
            enabled = viewModel.isNoErrors(),
            onClick = {
                viewModel.onEvent(LoginScreenEvents.Login)
            }
        )
    }
}

@Composable
fun LoginFooter(
    navigateToSignUp : () -> Unit
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = stringResource(R.string.sign_in_with),
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.tertiary
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ){
            val socialMediaIcons = listOf(
                R.drawable.google_plus_ic,
                R.drawable.facebook_ic,
                R.drawable.linkedin_ic,
                R.drawable.twitte__x_ic,
            )

            repeat(socialMediaIcons.size){
                Image(
                    modifier = Modifier.padding(horizontal = 4.dp).size(48.dp),
                    painter = painterResource(id = socialMediaIcons[it]),
                    contentDescription = "Social Media Icon",
                    contentScale = ContentScale.FillBounds
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(
            colors = ButtonDefaults.textButtonColors(
                contentColor = MaterialTheme.colorScheme.tertiary
            ),
            onClick = {
                navigateToSignUp()
            }
        ) {
            Text(
                text = "Don't have an account? Create one",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
@Preview
fun PreviewLoginScreen(){
    LoginScreen(
        viewModel = hiltViewModel(),
        navigateToHome = {},
        navigateToSignUp = {}
    )
}

