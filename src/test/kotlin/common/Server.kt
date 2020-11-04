package hello.tests.common

import io.kotest.core.spec.style.AnnotationSpec
import io.restassured.RestAssured
import io.restassured.response.ResponseBodyExtractionOptions
import io.restassured.specification.RequestSpecification
import org.junit.jupiter.api.BeforeAll

fun RequestSpecification.When(): RequestSpecification {
    return this.`when`()
}

open class Server : AnnotationSpec() {
    companion object {
        @BeforeAll @JvmStatic fun setup() {
            RestAssured.baseURI = "http://localhost"
            RestAssured.port = 3000
            RestAssured.basePath = "/"
        }
    }
}