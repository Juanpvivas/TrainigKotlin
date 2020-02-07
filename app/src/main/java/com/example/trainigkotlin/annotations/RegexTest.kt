package com.example.trainigkotlin.annotations

import android.provider.ContactsContract


/**
 * Created by Juan Vivas on 2020-02-07
 * Copyright (c) 2020 Merqueo. All rights reserved.
 */

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class CustomValidation(val regex: String)


data class Persona(
    @CustomValidation(regex = "(?:[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")
    var email: String
)

fun main() {

    val persona = Persona("jvivas@merqueo.com")

    val fields = persona.javaClass.declaredFields
    for (field in fields) {
        println(field.name)
    }
    if (fields[0].isAnnotationPresent(CustomValidation::class.java)) {
        val annotations = fields[0].annotations

        for (annotation in annotations) {
            if (annotation is CustomValidation) {
                fields[0].isAccessible = true
                val fieldValue = fields[0].get(persona).toString()
                val isValidateEmail = annotation.regex.toRegex().containsMatchIn(fieldValue)

                println("is valid: $isValidateEmail")
            }
        }
    }
}