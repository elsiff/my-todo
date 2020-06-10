package me.elsiff.mytodo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MyTodoApplication

fun main(args: Array<String>) {
    runApplication<MyTodoApplication>(*args)
}
