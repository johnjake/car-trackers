package com.cartrackers.app.comms

class EmailAddress(fullAddress: String) {

    var domain: String
    var localPart: String

    init {
        val atIndex = fullAddress.indexOf('@')
        if(atIndex<0) {
            throw IllegalArgumentException("The email address is invalid. (Missing @ character.)")
        }

        val localPart = fullAddress.substring(0..atIndex)
        if(localPart.isEmpty()) {
            throw IllegalArgumentException("The email address is invalid. (Local-part is empty.)")
        }
        else {
            this.localPart = localPart
        }

        val domain = fullAddress.substring(atIndex+1)
        if(domain.isEmpty()) {
            throw IllegalArgumentException("The email address is invalid. (Domain is empty.)")
        }
        else {
            this.domain = domain
        }
    }
}