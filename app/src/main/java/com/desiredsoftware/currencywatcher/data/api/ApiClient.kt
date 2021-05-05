package com.desiredsoftware.currencywatcher.data.api

class ApiClient (baseUrl: String) {
    val apiService = ApiService.create(baseUrl)
}