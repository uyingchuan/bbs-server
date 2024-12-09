package org.levons.bbs

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BBSApplication

fun main(args: Array<String>) {
    runApplication<BBSApplication>(*args)
}
