package com.thales.product_service.dto

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ApiResponse<out T, out E>(
    val isSuccess: Boolean = false,
    val message: String? = null,
    val data: T? = null,
    val error: E? = null
)