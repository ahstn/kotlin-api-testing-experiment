package payment.kotest

import io.kotest.core.spec.BeforeTest
import io.restassured.RestAssured

val setup: BeforeTest = {
    RestAssured.baseURI = "http://localhost"
    RestAssured.port = 3000
    RestAssured.basePath = "/"
}