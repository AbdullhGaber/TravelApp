package com.example.travelapp.screens.notes

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.data.uitls.Resource
import com.example.travelapp.R
import com.example.travelapp.screens.common.ErrorDialog
import com.example.travelapp.screens.common.ScreenHeaderImage
import com.example.travelapp.screens.common.TripCircularProgressIndicator
import com.example.travelapp.screens.notes.components.NotesCardList
import com.example.travelapp.ui.theme.TravelAppTheme

@Composable
fun NotesScreen(
    viewModel: NotesViewModel = hiltViewModel(),
    navigateUp : () -> Unit,
    tripId : String
){
    Column( Modifier.fillMaxSize().background(MaterialTheme.colorScheme.secondary)){

        LaunchedEffect(true) {
            viewModel.getTripNotes(tripId)
        }

        val context = LocalContext.current
        LaunchedEffect(true){
            viewModel.addTripState.collect{ message ->
                Toast.makeText(context,message,Toast.LENGTH_LONG).show()
            }
        }

        val notesState = viewModel.notesStateFlow.collectAsState()
        when(notesState.value){
            is Resource.Loading -> {
                Box(
                    Modifier.
                    fillMaxSize().
                    background(Color.Black.copy(alpha = 0.3f)).
                    clickable(enabled = false) {}
                ){
                    TripCircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }

            is Resource.Failure -> {
                ErrorDialog(
                    text = notesState.value.message ?: "Error",
                    onDismiss = {
                        viewModel.onEvent(NotesScreenEvent.OnErrorDialogDismiss)
                        navigateUp()
                    }
                )
            }

            is Resource.Success -> {
                ScreenHeaderImage(
                    headerImagePainterId = R.drawable.notes_header_image,
                    title = stringResource(R.string.my_notes)
                )

                AddNoteForm(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    viewModel = viewModel,
                    tripId = tripId
                )
                Spacer(Modifier.height(8.dp))

                NotesCardList(notes = notesState.value.data ?: emptyList())
            }

            is Resource.Unspecified -> Unit
        }
    }

}

@Composable
private fun AddNoteForm(
    viewModel: NotesViewModel,
    tripId: String,
    modifier: Modifier = Modifier
){
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ){
        TextField(
            modifier = Modifier.weight(2f),
            value = viewModel.noteTextState.value,
            onValueChange = {
                viewModel.updateNoteText(it)
            },
            placeholder = { Text(text = stringResource(R.string.add_note)) },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = MaterialTheme.colorScheme.onPrimaryContainer,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                cursorColor =  MaterialTheme.colorScheme.onPrimaryContainer,
                errorIndicatorColor = Color.Red,
                errorSuffixColor = Color.Red
            )
        )
        Spacer(Modifier.width(8.dp))
        AddNoteButton(
            modifier = Modifier.padding(top = 16.dp),
            onClick = {
                viewModel.onEvent(NotesScreenEvent.OnAddButtonClick(tripId = tripId))
            }
        )
    }
}

@Composable
private fun AddNoteButton(
    modifier: Modifier,
    onClick : () -> Unit
){
    Button(
        modifier = modifier.size(56.dp),
        onClick = onClick,
        contentPadding = PaddingValues(0.dp),
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
            contentColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ){
        Icon(
            imageVector = Icons.AutoMirrored.Filled.Send,
            contentDescription = "Send Icon"
        )
    }
}

@Composable
@Preview
fun PreviewNotesScreen(){
    TravelAppTheme {
//        NotesScreen()
    }
}