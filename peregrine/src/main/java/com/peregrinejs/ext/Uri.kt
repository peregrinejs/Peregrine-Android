package com.peregrinejs.ext

import android.net.Uri

internal val Uri.origin: String
    get() = "$scheme://$authority"

internal val Uri.normalizedPath: String
    get() {
        val p = path
        return if (p.isNullOrEmpty()) "/" else p
    }

internal val Uri.isFileUrl: Boolean
    get() = scheme == "file"

internal val Uri.isAndroidAssetUrl: Boolean
    get() = isFileUrl && authority == "android_asset"

/**
 * Whether the path of a URI contains another's.
 *
 * A URI "contains" another URI if their scheme and authority match and the latter's path falls
 * within the former's, like as in a directory structure.
 */
internal infix fun Uri.contains(uri: Uri): Boolean =
    origin == uri.origin && uri.normalizedPath.startsWith(normalizedPath)
