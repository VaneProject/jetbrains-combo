package org.vane.combo.setting

import com.intellij.openapi.options.Configurable
import com.intellij.openapi.options.ConfigurableProvider
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogPanel
import com.intellij.ui.ColorPanel
import com.intellij.ui.components.JBCheckBox
import com.intellij.ui.dsl.builder.*
import org.vane.combo.ComboBundle
import org.vane.combo.ComboUtil
import org.vane.combo.JComboWindow
import org.vane.combo.display.DisplayListCellRenderer
import org.vane.combo.display.DisplayUtil
import org.vane.hub.setting.EmptyComponent
import org.vane.hub.setting.VaneComponent

internal class ComboConfigurableProvider(private val project: Project) : ConfigurableProvider() {
    override fun createConfigurable(): Configurable {
        val display: String = ComboBundle.message("setting.combo.title")
        // window valid check
        return if (ComboUtil.isValidWindow()) ComboComponent(project, display)
        else EmptyComponent(display, ComboBundle.message("setting.combo.empty.message"))
    }
}

/**
 * combo setting component
 */
private class ComboComponent(private val project: Project, display: String) : VaneComponent(display) {
    private val state: ComboState = ComboSettings.getInstance(project).state

    override fun createPanel(): DialogPanel = panel {
        lateinit var enabled: Cell<JBCheckBox>
        row {
            enabled = checkBox(ComboBundle.message("setting.combo.enable"))
                .bindSelected(state::enable)
                .onApply(::disposeWindow)
        }

        group(ComboBundle.message("setting.combo.display")) {
            row(ComboBundle.message("setting.combo.display.id") + ":") {
                comboBox(DisplayUtil.getDisplayList(), DisplayListCellRenderer())
                    .bindItem(state::getDisplay, state::setDisplay)
                    .enabledIf(enabled.selected)
                    .onApply { project.getUserData(JComboWindow.COMBO_WINDOW_KEY)?.updateLocation() }
            }
            row(ComboBundle.message("setting.combo.font.color") + ":") {
                colorPanel()
            }
        }
        group(ComboBundle.message("setting.combo.font")) {
            row {
                intTextField(0)
                    .label(ComboBundle.message("setting.combo.font.size") + ":")
                    .bindIntText(state::fontSize)
                    .enabledIf(enabled.selected)
                    .onApply(::updateFont)
            }
        }
    }

    private fun disposeWindow() {
        if (!state.enable) JComboWindow.dispose(project)
    }

    private fun updateFont() {
        project.getUserData(JComboWindow.COMBO_WINDOW_KEY)?.updateFont()
    }
}