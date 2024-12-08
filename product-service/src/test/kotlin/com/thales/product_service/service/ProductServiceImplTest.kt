package com.thales.product_service.service

import com.thales.product_service.MockitoHelper.anyObject
import com.thales.product_service.dto.ProductCreationRequest
import com.thales.product_service.entity.Product
import com.thales.product_service.repo.ProductRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import kotlin.test.assertContains
import kotlin.test.assertEquals

@SpringBootTest
@ExtendWith(MockitoExtension::class)
class ProductServiceImplTest {

    @Mock
    private lateinit var productRepository: ProductRepository

    @Mock
    private lateinit var fileSystemService: FileSystemService

    private lateinit var productServiceImpl: ProductServiceImpl

    @BeforeEach
    fun setUp() {
        productServiceImpl = ProductServiceImpl(productRepository, fileSystemService)
    }

    @Test
    fun createProduct_shouldReturnCREATED() {
        val productCreationRequest = ProductCreationRequest("Test", "Test", 35.0, "Test")
        Mockito.`when`(productRepository.insert(any(Product::class.java))).thenReturn(
            Product(
                productCreationRequest.name,
                productCreationRequest.type,
                productCreationRequest.price,
                productCreationRequest.description
            )
        )
        val productCreationResponse = productServiceImpl.createProduct(productCreationRequest)
        assertEquals(HttpStatus.CREATED, productCreationResponse.statusCode)
    }

    @Test
    fun uploadProductImages_shouldReturnOk() {
        val imageName = "test_image"
        Mockito.`when`(fileSystemService.saveFileToStorage(anyObject(), anyObject())).thenReturn(imageName)
        val productImageUploadResponse = productServiceImpl.uploadProductImages(listOf(mock()))

        assertEquals(HttpStatus.OK, productImageUploadResponse.statusCode)
        assertContains(productImageUploadResponse.body?.data?.imageNames.orEmpty(), imageName)
    }

    @Test
    fun uploadProductImages_shouldReturnBadRequest_whenImageListIsEmpty() {
        val productImageUploadResponse = productServiceImpl.uploadProductImages(listOf())

        assertEquals(HttpStatus.BAD_REQUEST, productImageUploadResponse.statusCode)
    }

    @Test
    fun getAllProducts_shouldReturnListOfAvailableProducts() {
        val products =
            listOf(Product("Prod1", "Type1", 45.0, "Prod1 Desc"), Product("Prod2", "Type2", 45.0, "Prod2 Desc"))
        Mockito.`when`(productRepository.findAll()).thenReturn(products)

        val productResponse = productServiceImpl.getAllProducts()

        assertEquals(HttpStatus.OK, productResponse.statusCode)
        assertEquals(products, productResponse.body?.data)
    }

    @Test
    fun findProducts_shouldReturnProductsOfContainingName() {
        val products = listOf(
            Product("Test Prod1", "Type1", 75.0, "Prod1 Desc"), Product("Test Prod2", "Type2", 45.0, "Prod2 Desc")
        )
        Mockito.`when`(productRepository.findByNameContainingIgnoreCase(anyObject())).thenReturn(products)

        val productResponse = productServiceImpl.findProducts("test")

        assertEquals(HttpStatus.OK, productResponse.statusCode)
        assertEquals(products, productResponse.body?.data)
    }

}