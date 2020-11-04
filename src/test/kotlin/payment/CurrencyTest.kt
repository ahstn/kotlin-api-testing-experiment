package payment

import com.aventstack.extentreports.ExtentReports
import com.aventstack.extentreports.Status
import com.aventstack.extentreports.reporter.ExtentSparkReporter
import io.kotest.core.spec.BeforeTest
import io.kotest.core.spec.style.FeatureSpec
import io.kotest.data.forAll
import io.kotest.data.headers
import io.kotest.data.row
import io.kotest.data.table
import io.restassured.RestAssured
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import model.Charge
import model.Currency
import model.Response
import model.card.Brand
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

val setup: BeforeTest = {
    RestAssured.baseURI = "http://localhost"
    RestAssured.port = 3000
    RestAssured.basePath = "/"
}

class CurrencyTest : FeatureSpec({
    beforeTest(setup)

    feature("generic currencies") {
        val extent = ExtentReports()
        val spark = ExtentSparkReporter("target/Spark.html")
        extent.attachReporter(spark)
        val feature = extent.createTest("generic currencies")

        table(
                headers("currency"),
                row(Currency.GBP),
                row(Currency.USD)
        ).forAll { a ->
            scenario("${a.code} is supported") {
                val scenario = feature.createNode("${a.code} is supported")
                val request = Charge.Builder()
                        .currency(a)
                        .amount(120.00)
                        .customer("en-US")
                        .card(Brand.VISA)

                val response: Response = given()
                        .contentType(ContentType.JSON)
                        .body(request)
                        .`when`()
                        .post("/payment")
                        .then()
                        .statusCode(200)
                        .extract()
                        .`as`(Response::class.java)

                assertThat(response).isNotNull
                assertThat(response.status).isEqualTo("success")
                scenario.pass("success")
            }

        }
        extent.flush()
    }
})