package com.novaapps.jmdb.data.model

import com.smartapps.jmdb.data.model.LGA


data class LgasResponse(
    val `data`: List<LGA>,
    val status: Boolean
)