package common

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.core.spec.style.FeatureSpec
import io.kotest.core.test.TestCase
import io.restassured.RestAssured

open class KotlinFeatureSpec : FeatureSpec({
    fun beforeAny(testCase: TestCase) {
        RestAssured.baseURI = "http://localhost"
        RestAssured.port = 3000
        RestAssured.basePath = "/"
    }
})