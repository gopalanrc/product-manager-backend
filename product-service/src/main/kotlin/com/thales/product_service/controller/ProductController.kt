package com.thales.product_service.controller

import com.thales.product_service.dto.ApiResponse
import com.thales.product_service.dto.Error
import com.thales.product_service.dto.ProductCreationRequest
import com.thales.product_service.dto.ProductCreationResponseData
import com.thales.product_service.entity.Product
import com.thales.product_service.service.ProductService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/product")
class ProductController @Autowired constructor(private val productService: ProductService) {

    @PostMapping("/new")
    fun createProduct(@RequestBody productCreationRequest: ProductCreationRequest): ResponseEntity<ApiResponse<ProductCreationResponseData, Error>> {
        return productService.createProduct(productCreationRequest)
    }

    @GetMapping("/all")
    fun getAllProducts(): ResponseEntity<ApiResponse<List<Product>, Error>> {
        return productService.getAllProducts()
    }

    @GetMapping("/search")
    fun findProducts(@RequestParam(name = "name") name: String): ResponseEntity<ApiResponse<List<Product>, Error>> {
        return productService.findProducts(name)
    }
}