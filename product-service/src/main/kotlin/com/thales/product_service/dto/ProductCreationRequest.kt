package com.thales.product_service.dto

data class ProductCreationRequest(
    val name: String,
    val type: String,
    val price: Double,
    val description: String,
    val imageNames: List<String>? = null
)
