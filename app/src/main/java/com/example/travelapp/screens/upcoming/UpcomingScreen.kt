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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.travelapp.screens.common.TripCardList
import com.example.travelapp.screens.common.TripCardListShimmerEffect


@Composable
fun UpcomingScreen(
    viewModel: UpcomingViewModel = hiltViewModel(),
){
    val tripsState = viewModel.tripStateFlow.collectAsState()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.secondary ,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /*TODO*/ },
                containerColor = MaterialTheme.colorScheme.primary,
                shape = CircleShape
            ){
               Icon(
                   imageVector = Icons.Default.Add,
                   tint =  MaterialTheme.colorScheme.secondary,
                   contentDescription = "Add Icon"
               )
            }
        }
    ){ innerPadding ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Box(modifier = Modifier
                    .fillMaxHeight(0.3f)
                    .fillMaxWidth()
                ){
                    Image(
                        modifier = Modifier.matchParentSize(),
                        contentScale = ContentScale.Crop,
                        painter = painterResource(id = R.drawable.upcoming_header),
                        contentDescription = stringResource(R.string.upcoming_screen_header)
                    )
                    Text(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(24.dp),
                        text = stringResource(R.string.my_trips),
                        color = Color.White,
                        fontSize = 30.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                tripsState.value.data?.let{
                    TripCardList(
                        trips = it
                    )
                }

                if(tripsState.value is Resource.Loading){
                    TripCardListShimmerEffect()
                }
            }

            if(tripsState.value !is Resource.Loading && (tripsState.value.data == null || tripsState.value.data!!.isEmpty())){
                ShowNoItems(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(horizontal = 8.dp)
                )
            }
        }
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
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
    }
}
@Composable
@Preview
fun PreviewUpcomingScreen(){
    UpcomingScreen()
}