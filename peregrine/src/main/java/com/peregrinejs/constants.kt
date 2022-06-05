// Peregrine for Android: native container for hybrid apps
// Copyright (C) 2022 Caracal LLC
//
// This program is free software: you can redistribute it and/or modify it
// under the terms of the GNU General Public License version 3 as published
// by the Free Software Foundation.
//
// This program is distributed in the hope that it will be useful, but WITHOUT
// ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
// FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
// more details.
//
// You should have received a copy of the GNU General Public License version 3
// along with this program. If not, see <https://www.gnu.org/licenses/>.

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
