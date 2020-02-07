package com.example.trainigkotlin.annotations


/**
 * Created by Juan Vivas on 2020-02-07
 * Copyright (c) 2020 Merqueo. All rights reserved.
 */

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class Greeting(val message: String)


data class Annotations(
    @Greeting(message = "Welcome, ") val message: String
)

fun main() {
val msg = Annotations("Test de annotation")
    val fields = msg.javaClass.declaredFields
    for (field in fields){
        println(field.name)
    }
    if(fields[0].isAnnotationPresent(Greeting::class.java)){
        val annotations = fields[0].annotations

        for(annotation in annotations){
            if(annotation is Greeting){
                val annotationValue = annotation.message
                fields[0].isAccessible = true
                val fieldValue = fields[0].get(msg)
                println(annotationValue + fieldValue)
            }
        }
    }
}