package com.thales.product_service.service

import com.thales.product_service.dto.*
import com.thales.product_service.entity.Product
import com.thales.product_service.repo.ProductRepository
import com.thales.product_service.util.ERROR_CODE_EMPTY_PRODUCT_IMAGES
import com.thales.product_service.util.ERROR_CODE_INVALID_IMAGE_FILE
import com.thales.product_service.util.PRODUCT_IMAGE_DIR
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.Resource
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.io.FileNotFoundException

@Component
class ProductServiceImpl @Autowired constructor(
    private val repository: ProductRepository, private val fileSystemService: FileSystemService
) : ProductService {
    override fun createProduct(productCreationRequest: ProductCreationRequest): ResponseEntity<ApiResponse<ProductCreationResponseData, Error>> {
        productCreationRequest.imageNames?.let { imageNames ->
            for (imageName in imageNames) {
                if (!fileSystemService.doesFileExist(PRODUCT_IMAGE_DIR, imageName)) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        ApiResponse(
                            error = Error(
                                ERROR_CODE_INVALID_IMAGE_FILE, "Image file $imageName doesn't exist"
                            )
                        )
                    )
                }
            }
        }
        val productCreated = repository.insert(
            Product(
                productCreationRequest.name,
                productCreationRequest.type,
                productCreationRequest.price,
                productCreationRequest.description,
                imageNames = productCreationRequest.imageNames,
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

    override fun uploadProductImages(productImages: List<MultipartFile>?): ResponseEntity<ApiResponse<ProductImageUploadResponseData, Error>> {
        val productImageNames = mutableListOf<String>()

        if (productImages.isNullOrEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ApiResponse(
                    error = Error(
                        ERROR_CODE_EMPTY_PRODUCT_IMAGES, "Product images empty or null"
                    )
                )
            )
        }
        for (image in productImages) {
            productImageNames.add(fileSystemService.saveFileToStorage(PRODUCT_IMAGE_DIR, image))
        }

        return ResponseEntity.ok(
            ApiResponse(
                isSuccess = true,
                message = "Product images uploaded successfully!",
                data = ProductImageUploadResponseData(productImageNames.toList())
            )
        )
    }

    override fun getAllProducts(): ResponseEntity<ApiResponse<List<Product>, Error>> {
        val products = repository.findAll()
        return ResponseEntity.ok(
            ApiResponse(
                isSuccess = true, message = "Found ${products.size} product(s)", data = products
            )
        )
    }

    override fun findProducts(name: String): ResponseEntity<ApiResponse<List<Product>, Error>> {
        val products = repository.findByNameContainingIgnoreCase(name)
        return ResponseEntity.ok(
            ApiResponse(
                isSuccess = true, message = "Found ${products.size} product(s)", data = products
            )
        )
    }

    override fun getProductImage(imageName: String): ResponseEntity<Resource> {
        return try {
            val imageResource = fileSystemService.loadAsResource(PRODUCT_IMAGE_DIR, imageName)
            ResponseEntity.ok().body(imageResource)

        } catch (e: FileNotFoundException) {
            ResponseEntity.notFound().build()
        }
    }
}