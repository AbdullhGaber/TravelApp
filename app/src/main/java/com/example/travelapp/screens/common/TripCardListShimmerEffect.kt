package com.example.travelapp.screens.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.travelapp.utils.shimmerEffect

@Composable
fun TripCardListShimmerEffect(){
    LazyColumn(
        contentPadding = PaddingValues(10.dp)
    ){
        items(3){
            Box(modifier = Modifier.padding(vertical = 8.dp).clip(RoundedCornerShape(20.dp)).fillMaxWidth().height(150.dp).shimmerEffect())
        }
    }
}

@Composable
@Preview
fun PreviewTripCardListShimmerEffect(){
    TripCardListShimmerEffect()
}