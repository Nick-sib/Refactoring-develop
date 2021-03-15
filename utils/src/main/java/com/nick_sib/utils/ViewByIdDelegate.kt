package com.nick_sib.utils

import android.app.Activity
import android.view.View
import androidx.annotation.IdRes
import java.lang.ref.WeakReference
import kotlin.reflect.KProperty

class ViewByIdDelegate<out T : View>(
    private val rootGetter: () -> View?,
    private val viewId: Int
) {
    private var rootRef: WeakReference<View>? = null
    private var viewRef: T? = null

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        var view = viewRef
        val cachedRoot = rootRef?.get()
        val currentRoot = rootGetter()
        if (currentRoot != cachedRoot || view == null) {
            if (currentRoot == null) {
                if (view != null) {
                    // Failsafe, возвращать хотя бы последнюю view
                    return view
                }
                throw IllegalStateException("Cannot get View, there is no root yet")
            }
            view = currentRoot.findViewById(viewId)
            viewRef = view
            rootRef = WeakReference(currentRoot)
        }

        checkNotNull(view) { "View with id \"$viewId\" not found in root" }

        return view
    }
}

fun <T : View> Activity.viewById(@IdRes viewId: Int): ViewByIdDelegate<T> =
     ViewByIdDelegate({ window.decorView.findViewById(android.R.id.content) }, viewId)
