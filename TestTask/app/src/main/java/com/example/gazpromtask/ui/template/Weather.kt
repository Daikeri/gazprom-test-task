package com.example.gazpromtask.ui.template

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.example.gazpromtask.R

@Preview
@Composable
fun WeatherScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp, bottom = 36.dp, start = 16.dp, end = 16.dp)
    ) {
        Temperature(modifier = Modifier.weight(1.0F))
        RefreshButton(modifier = Modifier.weight(1.0F))
    }
}


@Composable
fun Temperature(
    degrees:Int = 23,
    city: String = "Москва",
    modifier: Modifier
) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier= Modifier
            .fillMaxWidth()
            .then(modifier)
    ) {
        Text(
            fontFamily = FontFamily(Font(R.font.roboto_regular)),
            fontSize = TextUnit(57f, TextUnitType.Unspecified),
            lineHeight = TextUnit(64f, TextUnitType.Unspecified),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            text = "${degrees}C"
        )
        Text(
            fontFamily = FontFamily(Font(R.font.roboto_regular)),
            fontSize = TextUnit(32f, TextUnitType.Unspecified),
            lineHeight = TextUnit(40f, TextUnitType.Unspecified),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            text = city
        )
    }
}


@Composable
fun RefreshButton(modifier: Modifier) {
    Column(
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier= Modifier
            .fillMaxWidth()
            .then(modifier)
    ) {
        Button(onClick = {  }) {
            Text(
                fontFamily = FontFamily(Font(R.font.roboto_medium)),
                fontSize = TextUnit(14f, TextUnitType.Unspecified),
                lineHeight = TextUnit(20f, TextUnitType.Unspecified),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                text = "Обновить"
            )
        }
    }
}