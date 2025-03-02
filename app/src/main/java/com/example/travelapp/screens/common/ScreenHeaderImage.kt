package com.example.travelapp.screens.common

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.DefaultStrokeLineMiter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.invisibleToUser
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.travelapp.R
import com.example.travelapp.ui.theme.Black
import com.example.travelapp.ui.theme.TravelAppTheme

@Composable
fun ScreenHeaderImage(
    @DrawableRes headerImagePainterId : Int,
    title : String = stringResource(R.string.let_s_go)
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        Image(
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.Crop,
            painter = painterResource(id = headerImagePainterId),
            contentDescription = stringResource(R.string.add_trip_screen_header)
        )


        OutlinedText(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp),
            outlineDrawStyle = Stroke(4f),
            text = title,
            fillColor = Color.White,
            outlineColor = Black,
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold
        )
    }
}




@Preview
@Composable
fun PreviewScreenHeader(){
    TravelAppTheme {
        ScreenHeaderImage(
            headerImagePainterId = R.drawable.notes_header_image,
            "My notes"
        )
    }
}