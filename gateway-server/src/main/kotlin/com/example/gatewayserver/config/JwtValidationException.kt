package com.example.gatewayserver.config

import java.lang.RuntimeException

class JwtValidationException(override val message: String?) : RuntimeException()