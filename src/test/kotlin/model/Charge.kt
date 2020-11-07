package model

import io.github.serpro69.kfaker.Faker
import io.github.serpro69.kfaker.FakerConfig
import io.github.serpro69.kfaker.create
import model.card.Brand

class Charge private constructor(
    val currency: Currency?,
    val amount: Double?,
    val billingDetails: BillingDetails?,
    val paymentMethod: PaymentMethod?
) {

    /**
     * Builder helps generate a custom Charge.
     * By default random customer details will be used with a VISA card and USD
     */

    data class Builder(
        var currency: Currency = Currency.USD,
        var amount: Double = 100.00,
        var billingDetails: BillingDetails? = null,
        var paymentMethod: PaymentMethod? = null
    ) {

        // Transient so GSON ignores
        @Transient private val faker: Faker = Faker()

        init {
            customer()
            card()
        }

        fun customer() = apply {
            this.billingDetails = BillingDetails(faker.name.firstName(), faker.name.lastName())
        }

        fun customer(locale: String) = apply {
            val fakerConfig = FakerConfig.builder().create { this.locale = locale }
            val faker = Faker(fakerConfig)

            this.billingDetails = BillingDetails(faker.name.firstName(), faker.name.lastName())
        }

        fun amount(amount: Double) = apply {
            this.amount = amount
        }

        fun currency(currency: Currency) = apply {
            this.currency = currency
        }

        fun card() = apply {
            this.paymentMethod = PaymentMethod(Brand.VISA, faker.stripe.validCards(Brand.VISA.code))
        }

        fun card(brand: Brand) = apply {
            this.paymentMethod = PaymentMethod(brand, faker.stripe.validCards(brand.code))
        }

        fun build() = Charge(currency, amount, billingDetails, paymentMethod)
    }
}
