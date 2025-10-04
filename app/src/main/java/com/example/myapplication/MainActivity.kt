package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {

                Scaffold(modifier = Modifier.fillMaxSize(),
                    containerColor = Color(0xFF0D1C3D)

                ) { innerPadding ->
                    // We pass the innerPadding to our main screen composable
                    AshaHomePage(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun AshaHomePage(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 32.dp), // Add padding for the whole screen
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween // Distributes space between top, center, and bottom
    ) {
        // --- TOP SECTION ---
        Text(
            text = "Welcome to ASHA Platform",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            fontSize = 36.sp,
            color = Color.White
        )

        // --- CENTER SECTION (IMAGE) ---
        Image(
            painter = painterResource(id = R.drawable.satymevjayte12), // Make sure your image is named satymevjayte.png
            contentDescription = "Satyamev Jayate Logo",
            modifier = Modifier
                .size(500.dp)// A balanced size for the image

                .background(
                    color = Color(0xFF0D1C3D),
                    shape =CircleShape
                )
        )

        // --- BOTTOM SECTION ---
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(30.dp) // Consistent spacing for bottom items
        ) {
            Text(
                text = "Ministry of Science and Technology ",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                fontSize = 30.sp,
                color = Color.Yellow

            )

            AnimatedEnterButton()

            NotificationBellIcon()
        }
    }
}

@Composable
fun AnimatedEnterButton() {
    // Create an infinite transition for the animation
    val infiniteTransition = rememberInfiniteTransition(label = "button-scale")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.05f, // Scales up to 105%
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 800), // Animation duration
            repeatMode = RepeatMode.Reverse // Scales back down
        ), label = "scale-animation"
    )

    Button(
        onClick = { /* TODO: Handle patient data entry navigation */ },
        modifier = Modifier
            .scale(scale) // Apply the animated scale first
            .width(280.dp) // Make the button span the full width
            .height(56.dp), // Set a fixed, taller height
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF00796B
            ) // Example: A nice shade of blue
        )
    ) {
        Text(
            text = "Enter Patient Data",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(vertical = 5.dp),
            fontSize = 23.sp,
            color=Color.White
        )
    }
}

@Composable
fun NotificationBellIcon() {
    // This is an IconButton with a visible border (the "ring")
    OutlinedIconButton(
        onClick = { /* TODO: Handle notification click */ },
        modifier = Modifier.size(60.dp),
        shape = CircleShape, // Makes the button circular
        border = BorderStroke(2.dp, Color.White) // Creates the white ring
    ) {
        Icon(
            imageVector = Icons.Filled.Notifications,
            contentDescription = "Notifications",
            modifier = Modifier.size(36.dp),
            tint = Color.Yellow

        )
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AshaHomePagePreview() {
    MyApplicationTheme {
        AshaHomePage()
    }
}