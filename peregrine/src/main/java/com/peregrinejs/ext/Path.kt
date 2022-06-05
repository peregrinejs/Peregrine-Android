package com.peregrinejs.ext

internal typealias Path = String

internal val Path.extension: String
    get() = ".${substringAfterLast('.', "")}"

internal val Path.segments: List<String>
    get() = trim('/').split('/')

/**
 * Appends an index file if the path ends in a trailing slash.
 *
 * @param indexFile The name of the index file to conditionally append.
 */
internal fun Path.resolve(indexFile: String): Path {
    if (isEmpty())
        return ""

    return when (last()) {
        '/' -> "${this}$indexFile"
        else -> this
    }
}
