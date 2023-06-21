package com.example.microserviceuser.exception

class CannotFindUserException(username: String) : RuntimeException("Cannot find user with username: $username")