package com.thales.product_service.entity

import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = "products")
data class Product(
    val name: String,
    val type: String,
    val price: Double,
    val description: String,
    val imageNames: List<String>? = null,
    @Id val id: String? = null
)
