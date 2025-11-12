package com.naturaz.bd

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import com.naturaz.bd.presentation.ui.theme.NaturazTheme
import com.naturaz.bd.presentation.navigation.NaturazApp

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NaturazTheme {
                NaturazApp()
            }
        }
    }
}
