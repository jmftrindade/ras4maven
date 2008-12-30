package br.ufrgs.inf.ras.util.ui;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.dialogs.MessageDialog;

/**
 * Default implementation of the {@link PartActivationListener}. A reload
 * dialog is shown if the file content was changed outside the editor.
 * <p>
 * This default implementation only listens to changes from the editor input
 * file. Subclasses may override and also listen to changes in other files that
 * are probably included in the data model.
 * 
 * @author Harald Vogl
 */
public class FileEditorActivationListener extends PartActivationListener {
	/** Indicates whether activation handling is currently be done. */
	private boolean isHandlingActivation = false;

	private long modificationStamp = -1;

	private FileEditor editor;

	public FileEditorActivationListener(FileEditor editor) {
		this.editor = editor;
	}

	@Override
	public void handleActivation() {
		if (isHandlingActivation || editor != getActivePart()) {
			return;
		}
		if (isChangedInOtherEditor()) {
			doHandleActivation();
			setChangedInOtherEditor(false);
		}
		IFile file = editor.getInputFile();
		if (!file.isSynchronized(IResource.DEPTH_ZERO)) {
			isHandlingActivation = true;
			try {
				long stamp = file.getLocalTimeStamp();
				if (stamp != modificationStamp) {
					doHandleActivation();
				}
				modificationStamp = stamp;
			} finally {
				isHandlingActivation = false;
			}
		}
	}

	private void doHandleActivation() {
		String title = "File Changed";
		String msg = "The file has been changed on the file system. Do you want to load the changes?";

		if (MessageDialog.openQuestion(editor.getSite().getShell(), title, msg)) {
			editor.reload();
			editor.updateTextContent(); // do not forget to update text content
			editor.setDirty(false);
		} else {
			// set editor dirty, because now the editor's content is different
			// from the current file content
			editor.setDirty(true);
		}
	}
}
