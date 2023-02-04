package com.peregrinejs

/**
 * A remote function.
 */
typealias RemoteFunction = (Call) -> Unit

/**
 * A map of function names -> functions.
 */
typealias RemoteFunctions = Map<String, RemoteFunction>
