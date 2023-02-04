package com.peregrinejs

import kotlinx.serialization.*

@Serializable
internal data class CallError(val message: String, val code: String?)
