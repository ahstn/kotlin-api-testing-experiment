package payment.retrofit

import com.aventstack.extentreports.ExtentReports
import com.aventstack.extentreports.markuputils.CodeLanguage
import com.aventstack.extentreports.markuputils.MarkupHelper
import com.aventstack.extentreports.reporter.ExtentSparkReporter
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
import org.assertj.core.api.Assertions.assertThat
import payment.kotest.ReportContainer
import payment.kotest.setup

class CurrencyTest : FeatureSpec({
    val reporter = ReportContainer.instance
    beforeTest(setup)

    feature("generic currencies") {
        val feature = reporter.createTest("generic currencies")
                .assignCategory("e-commerce", "currency", "charge")

        table(
                headers("currency"),
                row(Currency.GBP),
                row(Currency.USD),
                row(Currency.EUR)
        ).forAll { a ->
            scenario("${a.code} is supported") {
                val scenario = feature.createNode("${a.code} is supported")
                val request = Charge.Builder()
                        .currency(a)
                        .amount(120.00)
                        .customer("en-US")
                        .card(Brand.VISA)
                        .build()
                scenario.info(MarkupHelper.createCodeBlock(Gson().toJson(request), CodeLanguage.JSON))

                val api = Api.create()
                val response = api.postPayment(request).execute()

                response.body()?.status shouldBe "success"
                scenario.pass("success")
            }

            reporter.flush()
        }
    }
})