package br.ufrgs.inf.ras.editors.xml;

import org.eclipse.ui.editors.text.TextEditor;

public class RASXMLEditor extends TextEditor {

	private ColorManager colorManager;

	public RASXMLEditor() {
		super();
		colorManager = new ColorManager();
		setSourceViewerConfiguration(new XMLConfiguration(colorManager));
		setDocumentProvider(new XMLDocumentProvider());
	}
	public void dispose() {
		colorManager.dispose();
		super.dispose();
	}

}
