package com.thales.product_service.controller

import com.thales.product_service.dto.*
import com.thales.product_service.entity.Product
import com.thales.product_service.service.ProductService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.Resource
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/product")
class ProductController @Autowired constructor(private val productService: ProductService) {

    @PostMapping("/new")
    fun createProduct(@RequestBody productCreationRequest: ProductCreationRequest): ResponseEntity<ApiResponse<ProductCreationResponseData, Error>> {
        return productService.createProduct(productCreationRequest)
    }

    @PostMapping("/new/product_image")
    fun uploadProductImages(@RequestParam("productImages") productImages: List<MultipartFile>?): ResponseEntity<ApiResponse<ProductImageUploadResponseData, Error>> {
        return productService.uploadProductImages(productImages)
    }

    @GetMapping("/all")
    fun getAllProducts(): ResponseEntity<ApiResponse<List<Product>, Error>> {
        return productService.getAllProducts()
    }

    @GetMapping("/search")
    fun findProducts(@RequestParam(name = "name") name: String): ResponseEntity<ApiResponse<List<Product>, Error>> {
        return productService.findProducts(name)
    }

    @GetMapping("/image")
    fun getProductImage(
        @RequestParam(name = "imageName") imageName: String
    ): ResponseEntity<Resource> {
        return productService.getProductImage(imageName)
    }
}