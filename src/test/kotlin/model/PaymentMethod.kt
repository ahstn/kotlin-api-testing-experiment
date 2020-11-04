package model

import model.card.Brand

class PaymentMethod(
        val brand: Brand,
        val number: String
)