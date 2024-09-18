package org.vane.combo

import java.awt.GraphicsEnvironment

internal object ComboUtil {
    fun isValidWindow(): Boolean = !GraphicsEnvironment.isHeadless()
}