package payment.retrofit

import com.aventstack.extentreports.markuputils.CodeLanguage
import com.aventstack.extentreports.markuputils.MarkupHelper
import com.google.gson.Gson
import io.kotest.core.NamedTag
import io.kotest.core.spec.style.FeatureSpec
import io.kotest.data.forAll
import io.kotest.data.headers
import io.kotest.data.row
import io.kotest.data.table
import io.kotest.matchers.shouldBe
import model.Charge
import model.card.Brand
import payment.kotest.ReportContainer
import payment.kotest.setup

class LocaleTest : FeatureSpec({
    val reporter = ReportContainer.instance
    beforeTest(setup)

    feature("generic locales") {
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
                    .build()
                scenario.info(MarkupHelper.createCodeBlock(Gson().toJson(request), CodeLanguage.JSON))

                val api = Api.create()
                val response = api.postPayment(request).execute()

                response.body()?.status shouldBe "success"
                scenario.pass("success")
            }
        }

        reporter.flush()
    }
})
