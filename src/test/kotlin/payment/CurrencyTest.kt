package hello.tests.payment

import hello.tests.common.Server
import io.github.serpro69.kfaker.Faker
import model.Payment
import model.Response
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import model.Charge
import model.Currency
import org.hamcrest.CoreMatchers.equalTo
import org.assertj.core.api.Assertions.assertThat

import org.junit.jupiter.api.Test


class CurrencyTest : Server() {

    @Test
    fun `Currency Happy Path`()  {
        val request = Charge.Builder()
                .currency(Currency.GBP)
                .amount(120.00)
                .customer("en-US")

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
    }
}