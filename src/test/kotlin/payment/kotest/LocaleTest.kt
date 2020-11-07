package payment.kotest

import com.aventstack.extentreports.markuputils.CodeLanguage
import com.aventstack.extentreports.markuputils.MarkupHelper
import com.google.gson.Gson
import io.kotest.core.spec.style.FeatureSpec
import io.kotest.data.forAll
import io.kotest.data.headers
import io.kotest.data.row
import io.kotest.data.table
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import model.Charge
import model.Response
import model.card.Brand
import org.assertj.core.api.Assertions.assertThat

class LocaleTest : FeatureSpec({
    val reporter = ReportContainer.instance
    beforeTest(setup)

    feature("generic currencies") {
        val feature = reporter.createTest(description.name.displayName).assignCategory("payment", "locale")

        table(
            headers("locale"),
            row("en-GB"),
            row("en-US"),
            row("fr"),
            row("es"),
            row("de")
        ).forAll { a ->
            scenario("$a is supported") {
                val scenario = feature.createNode(testCase.displayName)
                val request = Charge.Builder()
                    .amount(120.00)
                    .customer(a)
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

                assertThat(response).isNotNull
                assertThat(response.status).isEqualTo("success")
                scenario.pass("success")
            }
        }

        reporter.flush()
    }
})
