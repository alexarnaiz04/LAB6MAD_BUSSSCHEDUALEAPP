package com.example.busscheduleapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

data class BusArrival(
    val stop: String,
    val time: String,
    val line: String
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BusScheduleScreen()
                }
            }
        }
    }
}

@Composable
fun BusScheduleScreen() {
    val arrivals = listOf(
        BusArrival("Central Station", "08:00", "Line A"),
        BusArrival("Central Station", "08:30", "Line A"),
        BusArrival("Central Station", "09:00", "Line B"),
        BusArrival("University", "08:15", "Line C"),
        BusArrival("University", "09:15", "Line C"),
        BusArrival("University", "10:45", "Line D"),
        BusArrival("Airport", "07:50", "Express"),
        BusArrival("Airport", "11:20", "Express"),
        BusArrival("Airport", "13:40", "Line E"),
        BusArrival("Shopping Center", "10:10", "Line B"),
        BusArrival("Shopping Center", "12:30", "Line B"),
        BusArrival("Shopping Center", "15:10", "Line F")
    )

    val stops = arrivals.map { it.stop }.distinct().sorted()

    var selectedStop by remember {
        mutableStateOf(stops.first())
    }

    val filteredArrivals = arrivals.filter {
        it.stop == selectedStop
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(18.dp)
    ) {
        Text(
            text = "Bus Schedule App",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Select a stop to check the next bus arrivals.",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(18.dp))

        SummaryCard(
            selectedStop = selectedStop,
            arrivalsCount = filteredArrivals.size
        )

        Spacer(modifier = Modifier.height(18.dp))

        Text(
            text = "Available stops",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier.height(245.dp)
        ) {
            items(stops) { stop ->
                StopCard(
                    stop = stop,
                    isSelected = stop == selectedStop,
                    onClick = {
                        selectedStop = stop
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Arrivals",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            TextButton(
                onClick = {
                    selectedStop = stops.first()
                }
            ) {
                Text("Reset")
            }
        }

        LazyColumn {
            items(filteredArrivals) { arrival ->
                ArrivalCard(arrival = arrival)
            }
        }
    }
}

@Composable
fun SummaryCard(
    selectedStop: String,
    arrivalsCount: Int
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier.padding(18.dp)
        ) {
            Text(
                text = "Selected stop",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = selectedStop,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "$arrivalsCount upcoming arrivals available",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun StopCard(
    stop: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isSelected) 8.dp else 3.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected)
                MaterialTheme.colorScheme.primaryContainer
            else
                MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stop,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
            )

            if (isSelected) {
                Text(
                    text = "Selected",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun ArrivalCard(
    arrival: BusArrival
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = arrival.line,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = arrival.stop,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Text(
                text = arrival.time,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
        }
    }
}