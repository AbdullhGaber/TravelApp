package com.example.travelapp.screens.register

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.data.uitls.Resource
import com.example.travelapp.R
import com.example.travelapp.screens.common.ErrorDialog
import com.example.travelapp.screens.common.PrimaryButton
import com.example.travelapp.screens.common.TripTextField
import com.example.travelapp.ui.theme.LightBlue
import com.example.travelapp.utils.isEmailValid
import com.example.travelapp.utils.isPasswordValid
import com.example.travelapp.utils.isPhoneNoValid
import com.example.travelapp.utils.isRePasswordValid
import com.example.travelapp.utils.isUsernameValid

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel,
    navigateToHome : () -> Unit
){

    val authStateFlow = viewModel.authStateFlow.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ){
        if(authStateFlow.value is Resource.Loading){
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = LightBlue,
                strokeWidth = 4.dp
            )
        }

        if(authStateFlow.value is Resource.Failure){
            ErrorDialog(
                text = authStateFlow.value.message.toString(),
                onDismiss = {
                   viewModel.onEvent(RegisterScreenEvents.ClearAuthFlowState)
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

        RegisterScreenContent(viewModel)

    }
}

@Composable
private fun RegisterScreenContent(
    viewModel: RegisterViewModel,
) {
    val verticalScrollState = rememberScrollState()

    Column{
        RegisterScreenHeader()

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier.verticalScroll(verticalScrollState)
        ){
            RegisterFormHeader(viewModel)

            Spacer(modifier = Modifier.height(16.dp))

            RegisterForm(viewModel)
        }
    }
}

@Composable
private fun RegisterFormHeader(
    viewModel: RegisterViewModel,
) {
    val profileImageStateFlow = viewModel.profileImageStateFlow.collectAsState()
    val contentResolver = LocalContext.current.contentResolver
    val arl = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            viewModel.onEvent(RegisterScreenEvents.OnChooseImageClick(contentResolver , it))
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(text = "New\nAccount", fontSize = 26.sp, fontWeight = FontWeight.Bold)

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if(profileImageStateFlow.value is Resource.Loading){
                Text(text = "Loading Image ...")
            }else{
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color.Gray)
                        .size(70.dp)
                        .clickable {
                            arl.launch("image/*")
                        }
                ){
                    if(profileImageStateFlow.value is Resource.Success){
                        Image(
                            modifier = Modifier
                                .clip(CircleShape)
                                .matchParentSize(),
                            contentScale = ContentScale.Crop,
                            bitmap = profileImageStateFlow.value.data?.asImageBitmap()!!,
                            contentDescription = stringResource(R.string.user_profile_picture)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    modifier = Modifier.fillMaxWidth(.4f),
                    textAlign = TextAlign.Center,
                    text = stringResource(R.string.upload_your_profile_picture),
                )
            }
        }
    }
}

@Composable
private fun RegisterForm(viewModel: RegisterViewModel) {
    TripTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        value = viewModel.emailState.value,
        errorMessage = viewModel.emailErrorState.value,
        onValueChange = {
            viewModel.emailState.value = it
            viewModel.emailErrorState.value = ""
            isEmailValid(
                email = it,
                emailError = viewModel.emailErrorState
            )
        },
        isError = viewModel.emailErrorState.value.isNotEmpty(),
        placeholder = "Email",
    )

    Spacer(modifier = Modifier.height(8.dp))

    TripTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        value = viewModel.nameState.value,
        errorMessage = viewModel.nameErrorState.value,
        onValueChange = {
            viewModel.nameState.value = it
            viewModel.nameErrorState.value = ""
            isUsernameValid(
                userName = it,
                userNameError = viewModel.nameErrorState
            )
        },
        isError = viewModel.nameErrorState.value.isNotEmpty(),
        placeholder = "Username",
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
            isPasswordValid(
                password = it,
                passwordError = viewModel.passwordErrorState
            )
        },
        errorMessage = viewModel.passwordErrorState.value,
        isError = viewModel.passwordErrorState.value.isNotEmpty(),
        placeholder = "Password",
        isPasswordVisible = viewModel.isPasswordVisibleState.value,
        isPasswordField = true,
        onPasswordVisibilityChange = {
            viewModel.isPasswordVisibleState.value = it
        }
    )

    Spacer(modifier = Modifier.height(8.dp))

    TripTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        value = viewModel.rePasswordState.value,
        errorMessage = viewModel.rePasswordErrorState.value,
        onValueChange = {
            viewModel.rePasswordState.value = it
            viewModel.rePasswordErrorState.value = ""
            isRePasswordValid(
                rePassword = it,
                rePasswordError = viewModel.rePasswordErrorState,
                password = viewModel.passwordState.value
            )
        },
        isError = viewModel.rePasswordErrorState.value.isNotEmpty(),
        placeholder = "Re-type Password",
        isPasswordVisible = viewModel.isRePasswordVisibleState.value,
        isPasswordField = true,
        onPasswordVisibilityChange = {
            viewModel.isRePasswordVisibleState.value = it
        }
    )

    Spacer(modifier = Modifier.height(8.dp))

    TripTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        value = viewModel.phoneNoState.value,
        errorMessage = viewModel.phoneNoErrorState.value,
        onValueChange = {
            viewModel.phoneNoState.value = it
            viewModel.phoneNoErrorState.value = ""
            isPhoneNoValid(
                phoneNo = it,
                phoneNoError = viewModel.phoneNoErrorState
            )
        },
        isPhoneNumberField = true,
        isError = viewModel.phoneNoErrorState.value.isNotEmpty(),
        placeholder = "Phone Number",
    )

    PrimaryButton(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 48.dp),
        text = stringResource(id = R.string.sign_up).uppercase(),
        enabled = viewModel.isNoErrors(),
        onClick = {
            viewModel.onEvent(RegisterScreenEvents.Register)
        }
    )
}

@Composable
private fun RegisterScreenHeader() {
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
            text = stringResource(R.string.sign_up),
            fontSize = 18.sp,
            color = Color.White
        )
    }
}

@Preview
@Composable
fun PreviewRegisterScreen() {
    RegisterScreen(
        viewModel = hiltViewModel(),
        navigateToHome = {}
    )
}