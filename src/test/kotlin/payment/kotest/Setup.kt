package payment.kotest

import com.aventstack.extentreports.ExtentReports
import com.aventstack.extentreports.reporter.ExtentSparkReporter
import com.natpryce.konfig.*
import com.natpryce.konfig.ConfigurationProperties.Companion.systemProperties
import io.kotest.core.spec.BeforeTest
import io.restassured.RestAssured

val setup: BeforeTest = {
    val serverProtocol = Key("server.protocol", stringType)
    val serverHost = Key("server.host", stringType)
    val serverPort = Key("server.port", intType)

    val config = systemProperties() overriding
            EnvironmentVariables() overriding
            ConfigurationProperties.fromResource("defaults.properties")

    RestAssured.baseURI = "${config[serverProtocol]}://${config[serverHost]}"
    RestAssured.port = config[serverPort]
    RestAssured.basePath = "/"
}

object ReportContainer {
    val instance: ExtentReports = createReportInstance()

    private fun createReportInstance(): ExtentReports {
        val extent = ExtentReports()
        extent.attachReporter(ExtentSparkReporter("target/Spark.html"))
        return extent
    }
}