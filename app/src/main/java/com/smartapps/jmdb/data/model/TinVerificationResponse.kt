package com.novaapps.jmdb.data.model

import com.smartapps.jmdb.data.model.Verification


data class TinVerificationResponse(
    val `data`: Verification,
    val status: Boolean
)