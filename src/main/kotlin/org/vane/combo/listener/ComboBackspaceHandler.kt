package org.vane.combo.listener

import com.intellij.codeInsight.editorActions.BackspaceHandlerDelegate
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFile
import org.vane.combo.JComboWindow

internal class ComboBackspaceHandler : BackspaceHandlerDelegate() {
    override fun beforeCharDeleted(c: Char, file: PsiFile, editor: Editor) {
    }

    override fun charDeleted(c: Char, file: PsiFile, editor: Editor): Boolean {
        val project: Project? = editor.project
        if (project !== null) JComboWindow.showComboWindow(project)
        return false
    }
}