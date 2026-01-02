package com.dmytro.ponomarenko.market

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import cafe.adriel.voyager.navigator.Navigator
import com.dmytro.ponomarenko.market.di.appModule
import com.dmytro.ponomarenko.market.di.sharedModule
import com.dmytro.ponomarenko.market.ui.screens.HomeScreen
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication

@Composable
@Preview
fun App() {
    KoinApplication(
        application = {
            modules(sharedModule, appModule)
        }
    ) {
        MaterialTheme {
            Navigator(HomeScreen())
        }
    }
}
