package model

import io.github.serpro69.kfaker.Faker
import io.github.serpro69.kfaker.FakerConfig
import io.github.serpro69.kfaker.create

class Charge private constructor(
        val currency: Currency?,
        val amount: Double?,
        val billingDetails: BillingDetails?) {

    data class Builder(
            var currency: Currency = Currency.USD,
            var amount: Double = 100.00,
            var billingDetails: BillingDetails? = null) {
        fun customer() = apply {
            val faker = Faker()
            this.billingDetails = BillingDetails(
                    faker.name.firstName(),
                    faker.name.lastName()
            )
        }

        fun customer(locale: String) = apply {
            val fakerConfig = FakerConfig.builder().create {
                this.locale = locale
            }

            val faker = Faker()
            this.billingDetails = BillingDetails(
                    faker.name.firstName(),
                    faker.name.lastName()
            )
        }

        fun amount(amount: Double) = apply {
            this.amount = amount
        }

        fun currency(currency: Currency) = apply {
            this.currency = currency
        }

        fun build() = Charge(currency, amount, billingDetails)
    }
}