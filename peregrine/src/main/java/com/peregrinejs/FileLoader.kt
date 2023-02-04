package com.peregrinejs

import com.peregrinejs.ext.*
import java.io.InputStream

internal interface FileLoader {
    fun open(path: Path): InputStream
    fun guessMimeType(path: Path): String
}
