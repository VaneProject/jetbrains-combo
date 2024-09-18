package org.vane.combo.display

import com.intellij.codeInsight.lookup.impl.LookupCellRenderer
import com.intellij.ui.SimpleListCellRenderer
import org.vane.combo.ComboBundle
import java.awt.Color
import java.awt.GraphicsDevice
import javax.swing.JList

internal class DisplayListCellRenderer : SimpleListCellRenderer<GraphicsDevice>() {
    override fun customize(
        list: JList<out GraphicsDevice>,
        start: GraphicsDevice,
        index: Int,
        selected: Boolean,
        hasFocus: Boolean
    ) {
        if (DisplayUtil.getDefaultDisplay() === start) {
            setDefaultDisplay(start.iDstring, selected)
        } else {
            text = start.iDstring
        }
    }
}

private fun DisplayListCellRenderer.setDefaultDisplay(text: String, selected: Boolean) {
    val gray: Color = LookupCellRenderer.getGrayedForeground(selected)
    val hex: String = String.format("#%02x%02x%02x", gray.red, gray.green, gray.blue)
    val default: String = ComboBundle.message("combobox.default")
    this.text = "<html>${text} <font color='$hex'>$default</html>"
}