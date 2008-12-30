package br.ufrgs.inf.ras.util.ui;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;

/**
 * Base class of the form page added on a {@link FileEditor}. It provides
 * useful implementations to handle the reload mechanism if the UI respectively
 * the text content was changed.
 * 
 * @see FileEditor
 * 
 * @author Harald Vogl
 */
public class FileFormPage extends FormPage {
	/**
	 * Flag indicating whether the content was changed while this page was
	 * active.
	 */
	private boolean dirty;

	/**
	 * Constructor with the underlying multipage form editor.
	 */
	public FileFormPage(FileEditor editor) {
		super(editor, editor.getSite().getId() + ".formPage", editor
				.getPartName());
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.forms.editor.FormPage#setActive(boolean)
	 */
	@Override
	public void setActive(boolean active) {
		if (active) {
			// clear dirty flag if this page is active now
			dirty = false;
		} else if (dirty) {
			// data model changed since last switch -> update text content
			((FileEditor) getEditor()).updateTextContent();
		}
		super.setActive(active);
	}

	/**
	 * The editor save mechanism is used from the default text editor, so update
	 * the text content before saving (but only if something was changed).
	 */
	@Override
	public void doSave(IProgressMonitor monitor) {
		if (dirty) {
			((FileEditor) getEditor()).updateTextContent();
		}
		dirty = false;
	}

	/**
	 * Helper method to indicate that the content was changed on this ui-page.
	 */
	protected void setDirty() {
		dirty = true;
	}
}
