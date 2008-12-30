package br.ufrgs.inf.ras.editors.xml;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.IFormPage;

import br.ufrgs.inf.ras.util.ui.FileEditor;


public class RASXMLEditor extends TextEditor implements IFormPage {

        private ColorManager colorManager;
        
        /** The underlying multipage form editor. */
        private FileEditor editor;

        /** The SWT control where the text is shown. */
        private Control partControl;

        /** The index of this text page on the underlying multipage form editor. */
        private int index;

        /**
         * Flag indicating whether the content was changed while this page was
         * active.
         */
        private boolean dirty;

        /**
         * Document listener that updates the own dirty flag and the editor dirty
         * state.
         */
        private IDocumentListener documentListener = new IDocumentListener() {
            public void documentChanged(DocumentEvent event) {
                if (editor != PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                        .getActivePage().getActivePart()) {
                    // the text was changed, but the editor was not active, so the
                    // content was changed outside (in an other editor)
                    editor.getPartListener().setChangedInOtherEditor(true);
                } else if (isActive()) {
                    dirty = true;
                    editor.setDirty(true);
                }
            }

            public void documentAboutToBeChanged(DocumentEvent event) {
                // unused interface method
            }
        };

        public RASXMLEditor() {
            super();
            colorManager = new ColorManager();
            setSourceViewerConfiguration(new XMLConfiguration(colorManager));
            setDocumentProvider(new XMLDocumentProvider());
        }
        
        public RASXMLEditor(FileEditor editor) {
            this();
            initialize(editor);
        }
        
        public void dispose() {
            getDocument().removeDocumentListener(documentListener);
            colorManager.dispose();
            super.dispose();
        }
           /**
         * Install document listener.
         */
        @Override
        public void init(IEditorSite site, IEditorInput input)
                throws PartInitException {
            super.init(site, input);
            getDocument().addDocumentListener(documentListener);
        }

        /**
         * Sets the content of this text editor to the given new content.
         */
        public void setContent(String newContent) {
            getDocument().set(newContent);
        }

        /**
         * @return The IDocument of this text editor.
         */
        private IDocument getDocument() {
            return getDocumentProvider().getDocument(getEditorInput());
        }

        /**
         * The save mechanism of the default text editor is used.
         */
        @Override
        public void doSave(IProgressMonitor progressMonitor) {
            super.doSave(progressMonitor);
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.ui.forms.editor.IFormPage#canLeaveThePage()
         */
        @Override
        public boolean canLeaveThePage() {
            if (dirty ) {
                // text content changed since last switch -> update data model
                editor.reloadFromText(getDocument().get());
            } else {
                editor.showErrorDialog();
            }

            // only can leave the page if the source has no errors
            return editor.getLoadStatus().isOK();
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.ui.forms.editor.IFormPage#setActive(boolean)
         */
        @Override
        public void setActive(boolean active) {
            if (active) {
                // clear dirty flag if text page is active now
                dirty = false;
            }
        }

        // --------------------------------------------
        // Standard implementation of IFormPage methods
        // --------------------------------------------

        @Override
        public boolean isActive() {
            return equals(editor.getActivePageInstance());
        }

        @Override
        public FormEditor getEditor() {
            return editor;
        }

        @Override
        public void initialize(FormEditor editor) {
            this.editor = (FileEditor) editor;
        }

        @Override
        public boolean isEditor() {
            return true;
        }

        @Override
        public boolean selectReveal(Object object) {
            return false;
        }

        @Override
        public String getId() {
            return editor.getSite().getId() + ".rasXMLEditor";
        }

        @Override
        public int getIndex() {
            return index;
        }

        @Override
        public void setIndex(int index) {
            this.index = index;
        }

        @Override
        public IManagedForm getManagedForm() {
            // not a form page
            return null;
        }

        @Override
        public void createPartControl(Composite parent) {
            super.createPartControl(parent);
            Control[] children = parent.getChildren();
            partControl = children[children.length - 1];
        }

        @Override
        public Control getPartControl() {
            return partControl;
        }
 }
