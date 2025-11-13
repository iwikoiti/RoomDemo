package com.example.roomdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.roomdemo.ui.theme.RoomDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RoomDemoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ScreenSetup(Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun RoomText(message: String, fontSize: Float){
    Text(
        text = message,
        fontSize = fontSize.sp,
    )
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    var quantity by remember { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = modifier
            .fillMaxSize()
            .padding(top = 20.dp, start = 20.dp, end = 20.dp)
    ){

        OutlinedTextField(
            value = quantity,
            onValueChange = { quantity = it},
            modifier = Modifier
                .fillMaxWidth(),
            label = { Text("Product Name") }
        )

        OutlinedTextField(
            value = quantity,
            onValueChange = { quantity = it},
            modifier = Modifier
                .fillMaxWidth(),
            label = { Text("Quantity") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ){
            Button(
                onClick = {

                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Add")
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                onClick = {

                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Search")
            }

        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ){
            Button(
                onClick = {

                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Delete")
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                onClick = {

                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Clear")
            }
        }


    }
}

@Composable
fun ScreenSetup(modifier: Modifier = Modifier) {
    MainScreen(modifier)
}

@Preview(showSystemUi = true)
@Composable
fun ScreenSetupPreview(){
    RoomDemoTheme{
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            ScreenSetup(Modifier.padding(innerPadding))
        }
    }
}
