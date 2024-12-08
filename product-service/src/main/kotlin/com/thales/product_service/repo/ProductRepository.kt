package com.thales.product_service.repo

import com.thales.product_service.entity.Product
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : MongoRepository<Product, String> {
    fun findByNameContainingIgnoreCase(name: String): List<Product>
}