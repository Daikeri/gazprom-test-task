package com.example.gazpromtask.ui.template

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gazpromtask.R
import com.example.gazpromtask.viewmodel.CitiesListViewModel
import kotlinx.coroutines.flow.distinctUntilChanged


@Composable
fun CitiesList(
    viewModel: CitiesListViewModel = viewModel(factory=CitiesListViewModel.Factory)
) {
    val uiState by viewModel.uiState.observeAsState(mapOf())
    var alphabet by remember { mutableStateOf<List<String>>(listOf()) }
    val listState = rememberLazyListState()
    var groupOffset by remember { mutableStateOf(Pair<Int, Int>(0, 0)) }
    var currentVisibleGroup by remember { mutableIntStateOf(listState.layoutInfo.visibleItemsInfo.size) }
    var currentItemGroup by remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        alphabet = uiState.keys.toList().map { it.toString() }
    }

    LaunchedEffect(listState) {
        snapshotFlow {
            Triple(
                listState.firstVisibleItemIndex,
                listState.firstVisibleItemScrollOffset,
                listState.layoutInfo.visibleItemsInfo
            )
        }
            .distinctUntilChanged()
            .collect { (firstVisibleItemIndex, firstVisibleItemScrollOffset, visibleItemsInfo) ->
                currentItemGroup = firstVisibleItemIndex
                if (currentVisibleGroup != visibleItemsInfo.size) {
                    currentVisibleGroup = visibleItemsInfo.size
                } else {
                    Log.e("lauch", "new effect")
                    groupOffset = Pair(currentItemGroup, firstVisibleItemScrollOffset)
                }
            }
    }

    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxSize()
    ) {
        itemsIndexed(uiState.keys.toList()) { index, key ->
            uiState[key]?.let { value ->
                GroupItems(
                    stickyHeaderLabel = key.toString(),
                    items = value,
                    localIndex = index,
                    groupOffset
                )
            }
        }
    }
}


@Composable
fun GroupItems(
    stickyHeaderLabel: String,
    items: List<String>,
    localIndex: Int,
    localOffset: Pair<Int, Int>,
) {
    val index by remember { mutableIntStateOf(localIndex) }

    Row(
        modifier = Modifier
            .background(Color(0xFFFEF7FF))
            .fillMaxSize()
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(40.dp)
                .padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
                .offset(
                    y = if (index == localOffset.first) {
                        (localOffset.second / LocalDensity.current.density).dp
                    } else {
                        0.dp
                    }
                )
        ) {
            Text(
                text = stickyHeaderLabel,
                color = Color(0xFF000000),
                fontSize = TextUnit(24f, TextUnitType.Unspecified),
                fontFamily = FontFamily(Font(R.font.roboto_medium)),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Column {
           items.forEach {
               Item(it)
           }
        }
    }
}

@Composable
fun Item(label: String="Какое-то слишком длинное название...") {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .height(40.dp)
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 8.dp)
            .background(Color(0xFFFEF7FF))
            .clip(
                shape = RoundedCornerShape(
                    topStart = 8.dp,
                    bottomStart = 8.dp
                )
            )
            .clickable(onClick = {})
    ) {
        Text(
            text = label,
            color = Color(0xFF000000),
            fontSize = TextUnit(16f, TextUnitType.Unspecified),
            fontFamily = FontFamily(Font(R.font.roboto_regular)),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(horizontal = 16.dp)
        )
    }
}
