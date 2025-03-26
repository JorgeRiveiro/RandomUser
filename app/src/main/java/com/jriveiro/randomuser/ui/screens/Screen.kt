package com.jriveiro.randomuser.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jriveiro.randomuser.ui.theme.RandomUserTheme

@Composable
fun Screen(content: @Composable () -> Unit) {
    RandomUserTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            content = content
        )
    }
}