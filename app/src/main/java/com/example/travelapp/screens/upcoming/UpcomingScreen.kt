package com.example.travelapp.screens.upcoming


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.travelapp.R
import com.example.travelapp.ui.theme.LightBlue

@Composable
fun UpcomingScreen(){
    Box(modifier = Modifier.fillMaxSize()){
        Column(
            Modifier
                .fillMaxSize()
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Image(
                modifier = Modifier
                    .fillMaxHeight(0.3f)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop,
                painter = painterResource(id = R.drawable.upcoming_header),
                contentDescription = stringResource(R.string.upcoming_screen_header)
            )
        }

        ShowNoItems(
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun ShowNoItems(
    modifier: Modifier
){
    Column(
        modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(
            modifier = Modifier.size(60.dp),
            painter = painterResource(id = R.drawable.smile_face),
            contentDescription = stringResource(R.string.upcoming_screen_header)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.there_are_no_items_here),
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = LightBlue
        )
    }
}
@Composable
@Preview
fun PreviewUpcomingScreen(){
    UpcomingScreen()
}