package com.example.roomdemo

import androidx.lifecycle.LiveData

class ProductRepository (private val dao: ProductDao) {
    val allProducts: LiveData<List<Product>> = dao.getAllProducts()

    suspend fun insert(product: Product){
        dao.insertProduct(product)
    }

    suspend fun delete(name: String){
        dao.deleteProduct(name)
    }

    suspend fun find(name: String): List<Product>{
        return dao.findProduct(name)
    }
}