package org.vane.combo

import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Key
import com.intellij.openapi.wm.WindowManager
import com.intellij.ui.JBColor
import org.vane.combo.setting.ComboSettings
import org.vane.combo.setting.ComboState
import java.awt.Dimension
import java.awt.FontMetrics
import java.awt.Frame
import java.awt.Rectangle
import java.util.*
import java.util.concurrent.atomic.AtomicLong
import javax.swing.JFrame
import javax.swing.JWindow


internal class JComboWindow private constructor(project: Project, frame: Frame) : JWindow(frame) {
    private val state: ComboState = ComboSettings.getInstance(project).state
    private val outline: JOutlinedLabel = JOutlinedLabel("", JBColor.RED, 1)
    private val count: AtomicLong = AtomicLong(0L)
    private var timer: Timer = Timer("ComboTimer")
    init {
        this.isAlwaysOnTop = true

        // outline
        this.updateFont()
        this.add(this.outline)

        this.updateLocation()
    }

    companion object {
        internal val COMBO_WINDOW_KEY: Key<JComboWindow> = Key.create("COMBO_WINDOW")

        internal fun dispose(project: Project) {
            val window: JComboWindow? = project.getUserData(COMBO_WINDOW_KEY)
            if (window !== null) {
                window.dispose()
                project.putUserData(COMBO_WINDOW_KEY, null)
            }
        }

        internal fun showComboWindow(project: Project) {
            if (!ComboSettings.getInstance(project).state.enable) return
            var window: JComboWindow? = project.getUserData(COMBO_WINDOW_KEY)
            if (window === null) {
                // get parent frame
                val frame: JFrame = WindowManager.getInstance().getFrame(project) ?: return
                window = JComboWindow(project, frame)
                project.putUserData(COMBO_WINDOW_KEY, window)
            } else if (window.isShowing) window.cancelTimer()
            window.showCombo()
        }
    }

    private fun showCombo() {
        this.isVisible = true
        this.outline.text = count.incrementAndGet().toString()
        this.updateSize()

        // setting timer
        this.timer = Timer()
        this.timer.schedule(object : TimerTask() {
            override fun run() {
                count.set(0L)
                isVisible = false
                cancelTimer()
            }
        }, 1000L)
    }

    private fun updateSize() {
        val metrics: FontMetrics = outline.getFontMetrics(outline.font)
        val thickness: Int = outline.getThickness() * 2
        this.size = Dimension(
            metrics.stringWidth(outline.text) + thickness,
            metrics.height + thickness
        )
    }

    private fun setLocation(bounds: Rectangle) {
        val x: Int = bounds.x + bounds.width / 2 - width / 2
        val y: Int = bounds.y + bounds.height * 7 / 10 - height * 7 / 10
        setLocation(x, y)
    }

    internal fun updateLocation() {
        val bounds: Rectangle = state.getDisplay().defaultConfiguration.bounds
        this.setLocation(bounds)
    }

    // outline styles
    internal fun updateFont() {
        this.outline.font = state.createFont()
        this.updateSize()
    }

    private fun cancelTimer() {
        this.timer.cancel()
        this.timer.purge()
    }

    override fun dispose() {
        super.dispose()
        this.cancelTimer()
    }
}