package com.dmytro.ponomarenko.market

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import com.dmytro.ponomarenko.market.di.appModule
import com.dmytro.ponomarenko.market.di.sharedModule
import com.dmytro.ponomarenko.market.ui.screens.HomeScreen
import org.koin.compose.KoinApplication

@Composable
fun App() {
    KoinApplication(
        application = {
            modules(sharedModule, appModule)
        }
    ) {
        MaterialTheme {
            // For JS target, directly show HomeScreen without Voyager Navigator
            // Voyager has limited JS support in version 1.0.1
            HomeScreen()
        }
    }
}
