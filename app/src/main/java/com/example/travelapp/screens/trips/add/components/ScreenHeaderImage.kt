package com.example.travelapp.screens.trips.add.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.travelapp.R

@Composable
fun ScreenHeaderImage() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        Image(
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.Crop,
            painter = painterResource(id = R.drawable.add_trip_header),
            contentDescription = stringResource(R.string.add_trip_screen_header)
        )

        Text(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp),
            text = stringResource(R.string.let_s_go),
            color = Color.White,
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold
        )
    }
}