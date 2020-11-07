package hello.tests.common

import io.restassured.RestAssured
import io.restassured.specification.RequestSpecification
import org.junit.jupiter.api.BeforeAll

fun RequestSpecification.When(): RequestSpecification {
    return this.`when`()
}

open class Server {
    companion object {
        @BeforeAll @JvmStatic fun setup() {
            RestAssured.baseURI = "http://localhost"
            RestAssured.port = 3000
            RestAssured.basePath = "/"
        }
    }
}
