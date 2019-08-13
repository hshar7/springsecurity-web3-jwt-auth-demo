package com.example.demo.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Resource Not Found")
class ResourceNotFoundException : RuntimeException {
    var resourceName: String
    var fieldName: String
    var fieldValue: Any

    constructor(resourceName: String, fieldName: String, fieldValue: Any) {
        this.resourceName = resourceName
        this.fieldName = fieldName
        this.fieldValue = fieldValue
    }
}
