package com.thales.product_service.service

import com.thales.product_service.dto.ApiResponse
import com.thales.product_service.dto.Error
import com.thales.product_service.dto.ProductCreationRequest
import com.thales.product_service.dto.ProductCreationResponseData
import com.thales.product_service.entity.Product
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
interface ProductService {
    fun createProduct(productCreationRequest: ProductCreationRequest): ResponseEntity<ApiResponse<ProductCreationResponseData, Error>>
    fun getAllProducts(): ResponseEntity<ApiResponse<List<Product>, Error>>
    fun findProducts(name: String): ResponseEntity<ApiResponse<List<Product>, Error>>
}