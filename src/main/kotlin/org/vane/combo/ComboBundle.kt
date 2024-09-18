package org.vane.combo

import com.intellij.DynamicBundle
import org.jetbrains.annotations.PropertyKey

private const val BUNDLE = "messages.combo"
object ComboBundle {
    private val DYNAMIC = DynamicBundle(ComboBundle::class.java, BUNDLE)

    fun message(
        @PropertyKey(resourceBundle = BUNDLE) key: String,
        vararg params: Any
    ): String = DYNAMIC.getMessage(key, *params)
}