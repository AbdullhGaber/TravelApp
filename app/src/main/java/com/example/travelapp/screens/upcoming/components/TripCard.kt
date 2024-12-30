package com.example.travelapp.screens.upcoming.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.entity.TripEntity
import com.example.travelapp.R
import com.example.travelapp.ui.theme.Black100
import com.example.travelapp.ui.theme.LightBlue

@Composable
fun TripCard(
    modifier: Modifier = Modifier,
    trip : TripEntity = TripEntity(status = "Upcoming"),
    onAddNotesClick : () -> Unit = {},
    onEditClick : () -> Unit = {},
    onDeleteClick : () -> Unit = {},
    onCancelClick : () -> Unit = {},
){

    val isOptionMenuOpened = remember{
        mutableStateOf(false)
    }

    val isMenuOpened = remember {
        mutableStateOf(false)
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(0.dp),
        onClick = {},
        shape = RoundedCornerShape(20.dp)
    ){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
        ){
            Image(
                modifier = Modifier
                    .matchParentSize()
                    .clickable {
                        isMenuOpened.value = isMenuOpened.value.not()
                    },
                painter = painterResource(id = R.drawable.trip_card_image),
                contentScale = ContentScale.Crop,
                contentDescription = stringResource(
                    R.string.trip_card_image
                )
            )

            Column(
                Modifier.align(Alignment.TopEnd)
            ){
                Icon(
                    modifier = Modifier
                        .size(48.dp)
                        .padding(8.dp)
                        .clickable {
                            isOptionMenuOpened.value = isOptionMenuOpened.value.not()
                        },
                    tint = White,
                    painter = painterResource(id = R.drawable.three_dots_ic),
                    contentDescription = stringResource(R.string.three_dots_more_icon)
                )

                TripOptionMenu(
                    expanded = isOptionMenuOpened.value,
                    onDismissRequest = {
                        isOptionMenuOpened.value = false
                    },
                    onAddNotesClick = onAddNotesClick,
                    onEditClick = onEditClick,
                    onDeleteClick = onDeleteClick,
                    onCancelClick = onCancelClick,
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize()
                .background(White)
                .padding(horizontal = 32.dp, vertical = if (isMenuOpened.value) 8.dp else 0.dp)
        ) {
            if (isMenuOpened.value) {
                TripDetailsCollapsingMenu(trip)
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
                .background(Black100)
                .padding(15.dp)
                .clickable {

                },
        ){
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = stringResource(R.string.start),
                color = White,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun TripDetailsCollapsingMenu(
    trip: TripEntity
){
    Column(
        Modifier
            .fillMaxWidth()
            .background(White)
            .padding(horizontal = 8.dp, vertical = 8.dp)
    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(text = trip.date)
            Text(text = trip.time)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                text = trip.name,
                fontWeight = FontWeight.Bold
            )

            Text(text = trip.status)
        }

        Spacer(modifier = Modifier.height(8.dp))

        HorizontalDivider(thickness = 0.5.dp , color = Gray)

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Column{
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ){
                    UnfilledCircle()
                    Text(text = trip.startDestination)
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ){
                    Icon(
                        painter = painterResource(id = R.drawable.map_ic),
                        tint = LightBlue,
                        contentDescription = stringResource(
                            id = R.string.map_icon
                        )
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(text = trip.endDestination)
                }
            }

            Image(
                painter = painterResource(id = R.drawable.file_icon),
                contentDescription = "File Icon"
            )
        }
    }
}

@Composable
fun TripOptionMenu(
    expanded : Boolean,
    onDismissRequest : () -> Unit = {},
    onAddNotesClick : () -> Unit = {},
    onEditClick : () -> Unit = {},
    onDeleteClick : () -> Unit = {},
    onCancelClick : () -> Unit = {},
){
    val options = listOf(
        stringResource(R.string.add_notes),
        stringResource(R.string.edit),
        stringResource(R.string.delete),
        stringResource(R.string.cancel)
    )

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest
    ){
        options.forEach { option ->
            DropdownMenuItem(
                text = {
                    Text(text = option)
                },
                onClick = {
                    when (option) {
                        options[0] -> {
                            onDismissRequest()
                            onAddNotesClick()
                        }

                        options[1] -> {
                            onDismissRequest()
                            onEditClick()
                        }

                        options[2] -> {
                            onDismissRequest()
                            onDeleteClick()
                        }

                        options[3] -> {
                            onDismissRequest()
                            onCancelClick()
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun UnfilledCircle(
    size: Int = 15,
    borderThickness: Int = 2,
    borderColor: Color = LightBlue,
) {
    Box(
        modifier = Modifier
            .padding(start = 5.dp, end = 12.dp)
            .size(size.dp)
            .drawBehind {
                drawCircle(
                    color = borderColor,
                    radius = size.dp.toPx() / 2, // Half the size for the circle radius
                    style = Stroke(width = borderThickness.dp.toPx()) // Stroke for unfilled effect
                )
            }
    )
}

@Composable
@Preview()
fun PreviewTripCard(){
    TripCard()
}