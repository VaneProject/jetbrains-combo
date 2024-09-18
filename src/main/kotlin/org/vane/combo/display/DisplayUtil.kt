package org.vane.combo.display

import java.awt.GraphicsDevice
import java.awt.GraphicsEnvironment

internal object DisplayUtil {
    internal fun getDisplayList(): List<GraphicsDevice> =
        GraphicsEnvironment.getLocalGraphicsEnvironment().screenDevices.toList()

    internal fun getDefaultDisplay(): GraphicsDevice =
        GraphicsEnvironment.getLocalGraphicsEnvironment().defaultScreenDevice
}