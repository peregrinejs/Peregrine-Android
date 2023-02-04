package com.peregrinejs

import android.net.Uri

internal const val PEREGRINE_URL_SCHEME = "https"
internal const val PEREGRINE_URL_AUTHORITY = "peregrine"
internal const val PEREGRINE_URL_ORIGIN = "$PEREGRINE_URL_SCHEME://$PEREGRINE_URL_AUTHORITY"
internal val PEREGRINE_URL = Uri.parse("$PEREGRINE_URL_ORIGIN/")

const val PEREGRINE_RPC_DIRECTIVE = "__rpc__"
const val PEREGRINE_USER_DIRECTIVE = "__user__"

internal const val DEFAULT_APP_URL_SCHEME = "https"
internal const val DEFAULT_APP_URL_AUTHORITY = "app"
internal const val DEFAULT_APP_URL_ORIGIN = "$DEFAULT_APP_URL_SCHEME://$DEFAULT_APP_URL_AUTHORITY"
internal val DEFAULT_APP_URL = Uri.parse("$DEFAULT_APP_URL_ORIGIN/")
