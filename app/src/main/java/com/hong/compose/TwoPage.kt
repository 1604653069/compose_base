package com.hong.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController


@Composable
fun OnePage(navHostController: NavHostController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("第一个界面")
        Button(onClick = {
            navHostController.navigate("TwoPage")
        }) {
            Text(
                text = "跳转到第二个界面"
            )
        }
    }
}

@Composable
fun TwoPage(navHostController: NavHostController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth().padding(10.dp)
    ) {
        Text("第二个界面")
        Button(onClick = {
            navHostController.navigate("MainPage")
        }) {
            Text("跳转到第一个界面")
        }
    }
}