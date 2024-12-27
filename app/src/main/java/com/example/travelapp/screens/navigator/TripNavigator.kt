package com.example.travelapp.screens.navigator

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.data.uitls.DataUtil
import com.example.data.uitls.NetworkUtil
import com.example.travelapp.R
import com.example.travelapp.screens.nav_graph.Route
import com.example.travelapp.screens.navigator.components.NavItems
import com.example.travelapp.screens.upcoming.UpcomingScreen
import com.example.travelapp.ui.theme.LightGray
import java.io.File

@Composable
fun TripNavigator(){
    val navController = rememberNavController()

   ModalNavigationDrawer(
       drawerContent = {
           ModalDrawerSheet(
               modifier = Modifier.fillMaxWidth(0.8f) // Adjust the width of the drawer
           ) {
               DrawerContent(navController = navController)
           }
       }
   ){
        NavHost(navController = navController, startDestination = Route.UpComingScreen.route){
            composable(
                route = Route.UpComingScreen.route
            ){
                UpcomingScreen()
            }
        }
   }
}

@Composable
private fun DrawerContent(
    navigatorViewModel: NavigatorViewModel = hiltViewModel(),
    navController: NavHostController
) {
    ModalDrawerSheet{
        Box(
            modifier = Modifier
                .fillMaxHeight(0.3f)
                .fillMaxWidth()
        ) {
            Image(
                modifier = Modifier.matchParentSize(),
                contentScale = ContentScale.Crop,
                painter = painterResource(id = R.drawable.drawer_header),
                contentDescription = "Drawer Header Image"
            )

            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .matchParentSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val context = LocalContext.current

                if(NetworkUtil.isDeviceConnected(context)){
                    AsyncImage(
                        model = DataUtil.tripUser?.imageURL,
                        error = painterResource(id = R.drawable.user_ic),
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(100.dp),
                        contentScale = ContentScale.FillBounds,
                        contentDescription = stringResource(
                            R.string.user_profile_picture
                        )
                    )
                }else{
                    AsyncImage(
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(100.dp),
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(File(DataUtil.tripUser?.imagePath?:""))
                            .crossfade(true)
                            .build(),
                        contentDescription = "Cached Image",
                        contentScale = ContentScale.Crop,
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = DataUtil.tripUser?.name ?: "No User",
                    style = TextStyle(
                        shadow = Shadow(
                            color = Color.Black,
                            offset = Offset(2f, 2f),
                            blurRadius = 8f
                        )
                    ),
                    color = Color.White,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = DataUtil.tripUser?.email ?: "No Email",
                    style = TextStyle(
                        shadow = Shadow(
                            color = Color.Black,
                            offset = Offset(2f, 2f),
                            blurRadius = 8f
                        )
                    ),
                    color = Color.White,
                    fontSize = 20.sp,
                )
            }
        } //End box

        Spacer(modifier = Modifier.height(8.dp))

        val itemSelectedIdx = remember {
            mutableIntStateOf(0)
        }

        NavItems.navItems.forEachIndexed { index, navItem ->
            NavigationDrawerItem(
                label = {
                    Text(
                        text = stringResource(id = navItem.nameStringRes),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                selected = itemSelectedIdx.intValue == index,
                onClick = {
                    itemSelectedIdx.intValue = index
                    navController.navigate(navItem.route)
                },
                icon = {
                    Image(
                        painter = painterResource(id = navItem.icon),
                        contentDescription = stringResource(
                            R.string.drawer_item_icon
                        )
                    )
                }
            )
        }

        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            thickness = 0.5.dp,
            color = LightGray
        )

        NavigationDrawerItem(
            label = {
                Text(
                    text = stringResource(R.string.others),
                    color = LightGray,
                    fontSize = 16.sp,
                )
            },
            icon = {},
            onClick = {},
            selected = false,
        )

        NavigationDrawerItem(
            label = {
                Text(
                    text = stringResource(id = R.string.sign_out),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            selected = false,
            onClick = {
                //sign out
            },
            icon = {
                Image(
                    painter = painterResource(id = R.drawable.sign_out_ic),
                    contentDescription = stringResource(
                        R.string.drawer_item_icon
                    )
                )
            }
        )
    }
}

@Composable
@Preview
fun PreviewTripNavigator(){
    TripNavigator()
}