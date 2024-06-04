package com.example.gazpromtask.ui.template

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gazpromtask.R
import com.example.gazpromtask.viewmodel.CitiesListViewModel

@Preview
@Composable
fun RetryNetworkRequest(
    errorMessage: String="Some Error Message",
    onRetry: () -> Unit = {}
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = errorMessage,
            fontFamily = FontFamily(Font(R.font.roboto_medium)),
            fontSize = TextUnit(14f, TextUnitType.Unspecified),
            lineHeight = TextUnit(20f, TextUnitType.Unspecified),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(bottom = 42.dp)
        )
        
        Button(onClick = { onRetry() }) {
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