package com.thales.product_service.service

import com.thales.product_service.dto.ApiResponse
import com.thales.product_service.dto.Error
import com.thales.product_service.dto.ProductCreationRequest
import com.thales.product_service.dto.ProductCreationResponseData
import com.thales.product_service.entity.Product
import com.thales.product_service.repo.ProductRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component

@Component
class ProductServiceImpl @Autowired constructor(private val repository: ProductRepository) : ProductService {
    override fun createProduct(productCreationRequest: ProductCreationRequest): ResponseEntity<ApiResponse<ProductCreationResponseData, Error>> {
        val productCreated = repository.insert(
            Product(
                productCreationRequest.name,
                productCreationRequest.type,
                productCreationRequest.price,
                productCreationRequest.description
            )
        )

        return ResponseEntity.status(HttpStatus.CREATED).body(
            ApiResponse(
                isSuccess = true,
                message = "Product created successfully",
                data = ProductCreationResponseData(productCreated.id.orEmpty())
            )
        )
    }

    override fun getAllProducts(): ResponseEntity<ApiResponse<List<Product>, Error>> {
        val products = repository.findAll()
        return ResponseEntity.ok(
            ApiResponse(
                isSuccess = true,
                message = "Found ${products.size} product(s)",
                data = products
            )
        )
    }

    override fun findProducts(name: String): ResponseEntity<ApiResponse<List<Product>, Error>> {
        val products = repository.findByNameContainingIgnoreCase(name)
        return ResponseEntity.ok(
            ApiResponse(
                isSuccess = true,
                message = "Found ${products.size} product(s)",
                data = products
            )
        )
    }
}