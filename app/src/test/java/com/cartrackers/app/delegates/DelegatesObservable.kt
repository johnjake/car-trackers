package com.cartrackers.app.delegates

import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestMethodOrder
import org.testcontainers.junit.jupiter.Testcontainers
import kotlin.properties.Delegates

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class DelegatesObservable {
    val user = Driver()

}

class User {
    var name: String by Delegates.observable("") {
            _, old, new -> val s = "$old $new"
        println(s)
    }
}

class Driver {
    var name: String = ""
}
