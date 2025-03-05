package com.example.travelapp.screens.notes.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.domain.entity.NoteEntity
import com.example.travelapp.ui.theme.TravelAppTheme

@Composable
fun NoteCard(
    modifier: Modifier = Modifier,
    note : NoteEntity
){
    Box(
        modifier = modifier.fillMaxWidth().
        background(MaterialTheme.colorScheme.secondary).
        padding(8.dp)
    ){
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                text = note.text,
                color = MaterialTheme.colorScheme.primaryContainer
            )

            Icon(
                imageVector = Icons.Default.DeleteForever,
                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                contentDescription = null
            )
        }
    }
}

@Composable
@Preview
fun PreviewNoteCard(){
    TravelAppTheme {
        NoteCard(note = NoteEntity(text = "note one"))
    }
}