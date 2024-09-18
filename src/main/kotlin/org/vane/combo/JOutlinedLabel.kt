package org.vane.combo

import java.awt.*
import javax.swing.JLabel
import javax.swing.SwingConstants

internal class JOutlinedLabel(
    text: String,
    private var outlineColor: Color,
    private var thickness: Int
) : JLabel(text, SwingConstants.CENTER) {
    init {
        verticalAlignment = SwingConstants.CENTER
    }

    fun setOutlineColor(outlineColor: Color) {
        this.outlineColor = outlineColor
        super.repaint()
    }

    fun setThickness(thickness: Int) {
        this.thickness = thickness
        super.repaint()
    }

    fun getThickness(): Int = this.thickness

    override fun paintComponent(g: Graphics) {
        val g2d: Graphics2D = g.create() as Graphics2D
        val fm: FontMetrics = g2d.fontMetrics

        g2d.color = this.outlineColor
        g2d.stroke = BasicStroke(this.thickness.toFloat())

        val text: String = super.getText()
        val x: Int = (width - fm.stringWidth(text)) / 2
        val y: Int = (height - fm.ascent) + (height / 2)

        for (i: Int in -thickness..thickness) {
            for (j: Int in -thickness..thickness) {
                g2d.drawString(text, x + i, y + j)
            }
        }

        g2d.color = background
        g2d.drawString(text, x, y)
        g2d.dispose()
    }
}