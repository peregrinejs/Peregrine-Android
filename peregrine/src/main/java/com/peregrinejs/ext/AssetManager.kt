package com.peregrinejs.ext

import android.content.res.AssetManager
import android.net.Uri

fun AssetManager.uri(path: Path): Uri =
    Uri.parse("file://android_asset/${path.trim('/')}/")
