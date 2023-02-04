package com.peregrinejs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope

/**
 * A fragment bound to a Web Frame.
 *
 * This fragment can either be constructed with or without the Web Frame (see
 * primary and secondary constructors). If constructing without the Web Frame,
 * set the [frame] property before [onViewCreated] is called.
 *
 * The fragment and Web Frame are "bound" in that the Web Frame is initialized
 * in [onViewCreated] and starts loading the [WebFrame.Configuration.baseUrl]
 * in [onStart].
 *
 * WebFrameFragment uses a [android.widget.FrameLayout] with dimensions
 * matching its parent.
 */
class WebFrameFragment() : Fragment() {
    /**
     * The Web Frame bound to this fragment.
     *
     * This must be initialized before [onViewCreated] is called.
     */
    lateinit var frame: WebFrame

    constructor(frame: WebFrame) : this() {
        this.frame = frame
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        frame.scope = viewLifecycleOwner.lifecycleScope
        frame.webView = view.findViewById(R.id.webview)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {
        frame.loadBaseURL()
        super.onStart()
    }
}
