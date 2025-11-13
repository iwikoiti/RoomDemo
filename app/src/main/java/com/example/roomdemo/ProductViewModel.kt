package com.example.roomdemo

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ProductViewModel(private val repository: ProductRepository): ViewModel() {
    val allProducts: LiveData<List<Product>> = repository.allProducts

    fun addProduct(name: String, quantity: Int){
        viewModelScope.launch{
            repository.insert(Product(productname = name, quantity = quantity))
        }
    }

    fun deleteProduct(name: String){
        viewModelScope.launch{
            repository.delete(name)
        }
    }

    fun findProduct(name: String, onResult: (List<Product>) -> Unit){
        viewModelScope.launch{
            val result = repository.find(name)
            onResult(result)
        }
    }
}

class ProductViewModelFactory(private val repository: ProductRepository):
        ViewModelProvider.Factory {
            override fun <T: ViewModel> create(modelClass: Class<T>): T{
                if (modelClass.isAssignableFrom(ProductViewModel::class.java)){
                   @Suppress("UNCHECKED_CAST")
                   return ProductViewModel(repository) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }