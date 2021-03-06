package payment.kotest

import com.aventstack.extentreports.markuputils.CodeLanguage
import com.aventstack.extentreports.markuputils.MarkupHelper
import com.google.gson.Gson
import io.kotest.core.spec.style.FeatureSpec
import io.kotest.data.forAll
import io.kotest.data.headers
import io.kotest.data.row
import io.kotest.data.table
import io.kotest.matchers.shouldBe
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import model.Charge
import model.Currency
import model.Response
import model.card.Brand

class CurrencyTest : FeatureSpec({
    val reporter = ReportContainer.instance
    beforeTest(setup)

    feature("generic currencies") {
        val feature = reporter.createTest(description.name.displayName).assignCategory("payment", "currency")

        table(
            headers("currency"),
            row(Currency.GBP),
            row(Currency.USD),
            row(Currency.EUR)
        ).forAll { a ->
            scenario("${a.code} is supported") {
                val scenario = feature.createNode(testCase.displayName)
                val request = Charge.Builder()
                    .currency(a)
                    .amount(120.00)
                    .customer("en-US")
                    .card(Brand.VISA)
                scenario.info(MarkupHelper.createCodeBlock(Gson().toJson(request), CodeLanguage.JSON))

                val response: Response = given()
                    .contentType(ContentType.JSON)
                    .body(request)
                    .`when`()
                    .post("/payment")
                    .then()
                    .statusCode(200)
                    .extract()
                    .`as`(Response::class.java)

                response.status shouldBe "success"

                scenario.pass("success")
            }

            reporter.flush()
        }
    }
})
