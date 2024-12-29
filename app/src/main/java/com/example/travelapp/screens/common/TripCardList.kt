package com.example.travelapp.screens.common

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.domain.entity.TripEntity
import com.example.travelapp.screens.upcoming.components.TripCard

@Composable
fun TripCardList(
    trips : List<TripEntity> = emptyList(),
    onAddNotesClick : () -> Unit = {},
    onEditClick : () -> Unit = {},
    onDeleteClick : () -> Unit = {},
    onCancelClick : () -> Unit = {},
){
    LazyColumn(
        contentPadding = PaddingValues(10.dp)
    ){
        items(trips){
            TripCard(
                modifier = Modifier.padding(vertical  = 10.dp),
                onAddNotesClick = onAddNotesClick,
                onEditClick = onEditClick,
                onDeleteClick =onDeleteClick ,
                onCancelClick = onCancelClick,
            )
        }
    }
}

@Composable
@Preview
fun PreviewTripCardList(){
    TripCardList()
}