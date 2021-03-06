package hello.tests.payment

import hello.tests.common.Server
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import model.Payment
import model.Response
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.CoreMatchers.equalTo
import org.junit.internal.runners.JUnit38ClassRunner
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith

@RunWith(JUnit38ClassRunner::class)
class GenericTest : Server() {
    @Test
    fun `Payment Returns 200`() {
        given()
            .`when`()
            .post("/payment")
            .then()
            .statusCode(200)
    }

    @Test
    fun `Payment returns 'status' of 'success'`() {
        val request = Payment("adam", "houston")
        given()
            .contentType(ContentType.JSON)
            .body(request)
            .`when`()
            .post("/payment")
            .then()
            .assertThat()
            .statusCode(200)
            .body("status", equalTo("success"))
    }

    @Test
    fun `Payment JSON returns 'status' of 'success'`() {
        val request = Payment("adam", "houston")
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
