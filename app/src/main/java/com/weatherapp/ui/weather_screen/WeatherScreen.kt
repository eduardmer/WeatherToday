package com.weatherapp.ui.weather_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.weatherapp.R
import com.weatherapp.ui.theme.Spacing
import com.weatherapp.ui.theme.Transparent
import com.weatherapp.ui.theme.TransparentBlack
import com.weatherapp.ui.theme.WeatherAppTheme
import com.weatherapp.ui.theme.White
import com.weatherapp.ui.utils.getErrorMessage

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun WeatherScreen(
    state: WeatherState,
    onEvent: (EventType) -> Unit
) {
    val cityName = remember {
        mutableStateOf("")
    }
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(state.backgroundImage),
                contentScale = ContentScale.FillBounds,
                contentDescription = null
            )
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(Spacing.medium),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .height(IntrinsicSize.Max)
                        .padding(horizontal = innerPadding.calculateStartPadding(LayoutDirection.Ltr)),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(Spacing.medium)
                ) {
                    CityTextField(
                        modifier = Modifier.fillMaxWidth()
                            .fillMaxHeight()
                            .clip(MaterialTheme.shapes.medium)
                            .weight(1F),
                        cityName = cityName
                    )
                    IconButton(
                        modifier = Modifier.fillMaxHeight()
                            .aspectRatio(1F)
                            .clip(MaterialTheme.shapes.medium)
                            .background(TransparentBlack),
                        onClick = {
                            onEvent(EventType.GetWeatherEvent(cityName.value))
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_search),
                            contentDescription = null,
                            tint = White
                        )
                    }
                }
                Spacer(modifier = Modifier.height(Spacing.medium))
                if (state.weatherConditions.isNotEmpty())
                    LazyVerticalGrid(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1F),
                        columns = GridCells.Fixed(2),
                        verticalArrangement = Arrangement.spacedBy(Spacing.medium),
                        horizontalArrangement = Arrangement.spacedBy(Spacing.medium)
                    ) {
                        item(span = {
                            GridItemSpan(2)
                        }) {
                            HeaderSection(
                                modifier = Modifier.fillMaxWidth(),
                                city = state.name,
                                temp = state.temp,
                                iconUrl = state.iconUrl,
                                description = state.weatherDescription
                            )
                        }
                        items(
                            span = { GridItemSpan(1) },
                            count = state.weatherConditions.size
                        ) {
                            AttributesItem(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(1F),
                                state.weatherConditions[it]
                            )
                        }
                    }
                else
                    Text(
                        text = stringResource(R.string.enter_city_to_see_weather),
                        color = White,
                        style = MaterialTheme.typography.headlineMedium
                    )
            }
            if (state.isLoading == true)
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
    if (state.error != null)
        AlertDialog(LocalContext.current.getErrorMessage(state.error)) {
            onEvent(EventType.DismissDialogEvent)
        }
}

@Composable
fun CityTextField(
    modifier: Modifier = Modifier,
    cityName: MutableState<String>
) {
    TextField(
        value = cityName.value,
        placeholder = {
            Text(
                text = stringResource(R.string.enter_city_name),
                color = White,
                style = MaterialTheme.typography.bodyMedium
            )
        },
        onValueChange = {
            cityName.value = it
        },
        leadingIcon = {
            Icon(
                painter = painterResource(R.drawable.ic_search),
                contentDescription = null,
                tint = White
            )
        },
        textStyle = TextStyle(color = White),
        singleLine = true,
        modifier = modifier,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = TransparentBlack,
            unfocusedContainerColor = TransparentBlack,
            focusedIndicatorColor = Transparent,
            unfocusedIndicatorColor = Transparent
        )
    )
}

@Composable
fun HeaderSection(
    modifier: Modifier = Modifier,
    city: String,
    temp: String,
    iconUrl: String?,
    description: String?
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            city,
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            temp,
            style = MaterialTheme.typography.headlineLarge
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (!iconUrl.isNullOrEmpty())
                AsyncImage(
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.size(30.dp),
                    model = iconUrl,
                    contentDescription = null
                )
            if (!description.isNullOrEmpty())
                Text(
                    description,
                    style = MaterialTheme.typography.bodyMedium
                )
        }
    }
}

@Composable
fun AttributesItem(
    modifier: Modifier = Modifier,
    item: WeatherConditionsUi
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = TransparentBlack
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(Spacing.medium)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(item.iconId),
                    tint = White,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(Spacing.small))
                Text(
                    text = stringResource(item.titleId),
                    modifier = Modifier.weight(1F),
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
            Spacer(modifier = Modifier.height(Spacing.small))
            Text(
                item.description,
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.weight(1F))
            Text(
                item.additionalDatas,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Preview
@Composable
fun WeatherScreenPreview() {
    WeatherAppTheme {
        WeatherScreen(
            WeatherState(
                backgroundImage = R.drawable.bg_day,
                name = "New York",
                temp = "18.2°",
                iconUrl = "https://openweathermap.org/img/wn/03d.png",
                weatherDescription = "Raining",
                weatherConditions = listOf(
                    WeatherConditionsUi(
                        R.string.wind,
                        R.drawable.ic_wind,
                        "2342",
                        ""
                    ),
                    WeatherConditionsUi(
                        R.string.rainfall,
                        R.drawable.ic_rain,
                        "13ml",
                        "Heavy rain"
                    )
                )
            )
        ) { }
    }
}