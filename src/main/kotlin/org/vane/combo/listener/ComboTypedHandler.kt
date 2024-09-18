package org.vane.combo.listener

import com.intellij.codeInsight.editorActions.TypedHandlerDelegate
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.fileTypes.FileType
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFile
import org.vane.combo.JComboWindow

internal class ComboTypedHandler : TypedHandlerDelegate() {
    override fun isImmediatePaintingEnabled(editor: Editor, c: Char, context: DataContext): Boolean {
        val project: Project? = editor.project
        if (project !== null) JComboWindow.showComboWindow(project)
        return super.isImmediatePaintingEnabled(editor, c, context)
    }
}