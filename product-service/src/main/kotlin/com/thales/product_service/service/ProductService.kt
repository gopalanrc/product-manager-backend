package com.thales.product_service.service

import com.thales.product_service.dto.*
import com.thales.product_service.entity.Product
import org.springframework.core.io.Resource
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
interface ProductService {
    fun createProduct(productCreationRequest: ProductCreationRequest): ResponseEntity<ApiResponse<ProductCreationResponseData, Error>>

    fun uploadProductImages(productImages: List<MultipartFile>?): ResponseEntity<ApiResponse<ProductImageUploadResponseData, Error>>

    fun getAllProducts(): ResponseEntity<ApiResponse<List<Product>, Error>>

    fun findProducts(name: String): ResponseEntity<ApiResponse<List<Product>, Error>>

    fun getProductImage(imageName: String): ResponseEntity<Resource>
}