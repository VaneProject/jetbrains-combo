package org.vane.combo.listener

import com.intellij.codeInsight.editorActions.enter.EnterHandlerDelegate
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.actionSystem.EditorActionHandler
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Ref
import com.intellij.psi.PsiFile
import org.vane.combo.JComboWindow

internal class ComboEnterHandler : EnterHandlerDelegate {
    override fun preprocessEnter(
        file: PsiFile,
        editor: Editor,
        ref1: Ref<Int>,
        ref2: Ref<Int>,
        dataContext: DataContext,
        handler: EditorActionHandler?
    ): EnterHandlerDelegate.Result = EnterHandlerDelegate.Result.Continue

    override fun postProcessEnter(
        file: PsiFile,
        editor: Editor,
        dataContext: DataContext
    ): EnterHandlerDelegate.Result {
        val project: Project? = editor.project
        if (project !== null) JComboWindow.showComboWindow(project)
        return EnterHandlerDelegate.Result.Continue
    }
}