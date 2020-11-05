# kotlin-restassured-kotest

This repo is a POC to experiment with Kotlin and different JVM testing frameworks with asserting against REST APIs in mind.

Test implements exist (in some form) for both Kotlin with Kotest and Java with JUnit. This is primarily to show the contrasts between both given a similar context.

For the actual API, currently I'm just using Mockoon (an API Simulator) to always return `{"status":"success"}`.

## Technologies Used

* Kotlin
    * Kotest - https://github.com/kotest/kotest/
    * Konfig - https://github.com/npryce/konfig
* Java
    * JUnit
    * AssertJ
* Both (Kotlin & Java)
    * RESTAssured - https://github.com/rest-assured/rest-assured
    * ExtentReports - https://github.com/extent-framework/extentreports-java
    * Mockoon - https://mockoon.com/
    
## Why X ?
* Kotest - It's great support for data driven testing and spec style flexibility.

## Notes
Technically, the Java implementations should live under `/src/test/java`, but I've left them under `/src/main/kotlin` to show how both can co-exist.