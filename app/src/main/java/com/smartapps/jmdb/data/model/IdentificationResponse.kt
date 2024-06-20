package com.novaapps.jmdb.data.model

import com.smartapps.jmdb.data.model.IdentityType


data class IdentificationResponse(
    val `data`: List<IdentityType>,
    val status: Boolean
)