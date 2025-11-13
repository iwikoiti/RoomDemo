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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.roomdemo.ui.theme.RoomDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = ProductRoomDatabase.getInstance(this)
        val repository = ProductRepository(database.productDao())
        val factory = ProductViewModelFactory(repository)

        setContent {
            RoomDemoTheme {
                val viewModel: ProductViewModel = viewModel(factory = factory)
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ScreenSetup(Modifier.padding(innerPadding), viewModel)
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
fun MainScreen(modifier: Modifier = Modifier, viewModel: ProductViewModel) {
    var quantity by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    val allProducts by viewModel.allProducts.observeAsState(emptyList())
    var searchResult by remember { mutableStateOf<List<Product>>(emptyList()) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = modifier
            .fillMaxSize()
            .padding(top = 20.dp, start = 20.dp, end = 20.dp)
    ){

        OutlinedTextField(
            value = name,
            onValueChange = { name = it},
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
                    if (name.isNotBlank()) {
                        quantity.toIntOrNull()?.let { qty ->
                            viewModel.addProduct(name, qty)
                            name = ""
                            quantity = ""
                        }
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Add")
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                onClick = {
                    if (name.isNotBlank()) {
                        viewModel.findProduct(name) { result ->
                            searchResult = if (name.isBlank()) allProducts else result
                        }
                    }
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
                    if (name.isNotBlank()) {
                        viewModel.deleteProduct(name)
                        name = ""
                        quantity = ""
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Delete")
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                onClick = {
                    name = ""
                    quantity = ""
                    searchResult = emptyList()
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Clear")
            }
        }

        val productsToDisplay = if (searchResult.isNotEmpty()) searchResult else allProducts
        LazyColumn(modifier = Modifier.fillMaxWidth()){
            items(productsToDisplay) { product ->
                ProductItem(product)
            }
        }
    }
}

@Composable
fun ProductItem(product: Product) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(product.productName)
        Text("Qty: ${product.quantity}")
    }
    //Divider()
}

@Composable
fun ScreenSetup(modifier: Modifier = Modifier, viewModel: ProductViewModel) {
    MainScreen(modifier, viewModel)
}

//@Preview(showSystemUi = true)
//@Composable
//fun ScreenSetupPreview(){
//
//    RoomDemoTheme{
//        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//            ScreenSetup(Modifier.padding(innerPadding), viewModel)
//        }
//    }
//}
