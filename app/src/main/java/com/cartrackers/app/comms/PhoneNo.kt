package com.cartrackers.app.comms

const val invalidPhoneNoErrorMsg = "Not a valid NZ phone number"

fun String.canonicalPhoneNo(): String {
    //find where the local portion of the phone no starts by matching the prefix being used
    val localNoOffset = when {
        this.startsWith("+642") -> 4
        this.startsWith("642") -> 3
        this.startsWith("02") -> 2
        this.startsWith("2") -> 1
        else -> throw IllegalArgumentException(invalidPhoneNoErrorMsg)
    }

    val localNo = this.substring(localNoOffset)

    //check phone no length is correct
    require(localNo.length in 7..9) { invalidPhoneNoErrorMsg }

    //check that the phone no is only made up of digits
    require(localNo.matches(Regex("[0-9]+"))) { invalidPhoneNoErrorMsg }

    return "+642$localNo"
}