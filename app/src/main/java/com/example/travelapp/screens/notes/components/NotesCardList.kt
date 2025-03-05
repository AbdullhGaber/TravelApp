package com.example.travelapp.screens.notes.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.domain.entity.NoteEntity
import com.example.travelapp.ui.theme.TravelAppTheme

@Composable
fun NotesCardList(
    notes : List<NoteEntity>
){
    LazyColumn(contentPadding = PaddingValues(horizontal = 8.dp)) {
        items(notes){ note ->
            NoteCard(note = note, modifier = Modifier.padding(bottom = 8.dp))
        }
    }
}

@Composable
@Preview
fun PreviewNotesCardList(){
    TravelAppTheme {
        NotesCardList(notes = listOf(NoteEntity(text = "note one"),NoteEntity(text = "note two")))
    }
}