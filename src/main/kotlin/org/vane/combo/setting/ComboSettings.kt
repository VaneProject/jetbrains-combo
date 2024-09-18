package org.vane.combo.setting

import com.intellij.openapi.components.*
import com.intellij.openapi.project.Project
import org.vane.combo.display.DisplayUtil
import java.awt.Font
import java.awt.GraphicsDevice

@Service(Service.Level.PROJECT)
@State(name = "vane.combo.ComboState", storages = [Storage("vane.combo.xml")])
internal class ComboSettings : SimplePersistentStateComponent<ComboState>(ComboState()) {
    companion object {
        @JvmStatic
        fun getInstance(project: Project) = project.service<ComboSettings>()
    }
}

internal class ComboState : BaseState() {
    private var displayName by string()
    var enable: Boolean by property(false)
    var fontType: String? by string()
    var fontSize: Int by property(20)
    var outlineSize: Int by property(1)

    fun createFont(): Font = Font(fontType, Font.PLAIN, fontSize)

    fun getDisplay(): GraphicsDevice = if (displayName === null)
        DisplayUtil.getDefaultDisplay()
    else DisplayUtil.getDisplayList().find {
        it.iDstring == displayName
    } ?: DisplayUtil.getDefaultDisplay()

    fun setDisplay(device: GraphicsDevice?) {
        if (device === null || DisplayUtil.getDefaultDisplay() === device)
            this.displayName = null
        else this.displayName = device.iDstring
    }
}