package com.peregrinejs

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ApplicationInfo
import android.net.Uri
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.webkit.WebViewAssetLoader
import com.peregrinejs.ext.*

class WebFrame(val context: Context, val configuration: Configuration) {
    class Configuration(
        /**
         * The URL to the web app.
         *
         * If this is a file URI that lies within the Android asset directory
         * (e.g. `file://android_asset/www/`), then the app will be loaded locally. Otherwise,
         * the URI is assumed to be remote and the app will be loaded remotely.
         */
        internal val baseUrl: Uri,

        /**
         * The functions exposed to the web view.
         */
        internal val functions: RemoteFunctions = emptyMap(),

        /**
         * The observables exposed to the web view.
         */
        internal val observables: RemoteObservables = emptyMap(),

        private val pathHandlers: Map<Path, WebViewAssetLoader.PathHandler> = emptyMap(),
    ) {
        /**
         * Whether or not the app will load from assets on the device.
         */
        internal val local: Boolean = baseUrl.isAndroidAssetUrl

        /**
         * The URL that the WebView will load.
         */
        internal val appUrl: Uri =
            if (local)
                DEFAULT_APP_URL
            else
                baseUrl

        internal val peregrineUrl: Uri = PEREGRINE_URL

        /**
         * The origin which is allowed during CORS requests to the Peregrine RPC server.
         */
        internal val allowedOrigin: String = appUrl.origin

        internal val assetLoaderBuilder = WebViewAssetLoader.Builder().apply {
            setDomain(peregrineUrl.authority!!)

            for ((path, handler) in pathHandlers) {
                addPathHandler(path, handler)
            }
        }
    }

    private lateinit var _webView: WebView
    private val _webViewClient = WebViewClient(this)
    private val _webChromeClient = WebChromeClient(this)

    internal val isDebuggable: Boolean
        get() = 0 != context.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE

    internal val isLoaded: Boolean
        get() = webView.url != null

    internal lateinit var scope: LifecycleCoroutineScope

    internal var webView: WebView
        get() = _webView
        @SuppressLint("SetJavaScriptEnabled")
        set(value) {
            if (isDebuggable) {
                android.webkit.WebView.setWebContentsDebuggingEnabled(true)
            }

            _webView = value.apply {
                webViewClient = _webViewClient
                webChromeClient = _webChromeClient

                settings.apply {
                    allowContentAccess = false
                    allowFileAccess = false
                    databaseEnabled = true
                    domStorageEnabled = true
                    javaScriptCanOpenWindowsAutomatically = true
                    javaScriptEnabled = true
                    mediaPlaybackRequiresUserGesture = false
                }
            }
        }

    internal val assetLoader = configuration.assetLoaderBuilder.build()

    internal fun loadBaseURL() {
        val url = configuration.appUrl.toString()

        webView.loadUrl(url)
    }
}
