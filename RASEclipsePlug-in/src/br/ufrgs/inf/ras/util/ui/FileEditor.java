package br.ufrgs.inf.ras.util.ui;

import br.ufrgs.inf.ras.Activator;
import br.ufrgs.inf.ras.editors.xml.RASXMLEditor;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.article.rcp.FreeFormPage;
import org.eclipse.ui.forms.article.rcp.MasterDetailsPage;
import org.eclipse.ui.forms.article.rcp.PageWithSubPages;
import org.eclipse.ui.forms.article.rcp.SecondPage;
import org.eclipse.ui.forms.article.rcp.ThirdPage;
import org.eclipse.ui.forms.editor.FormEditor;

/**
 * Abstract base class for editors with a form page and a text editor showing
 * the underlying text content.
 * <p>
 * The data model and source/text page are automatically synchronized by calling
 * {@link #loadFile(InputStream)} with the content from the text page and
 * {@link #getSource()} that returns the current textual representation of the
 * model.
 * 
 * @author Harald Vogl
 */
public abstract class FileEditor extends FormEditor {
    /** The editor input file. */
    private IFile inputFile;

    /** The form page added to this multipage editor. */
    private FileFormPage formPageArray[];

    /** The text editor added to this multipage editor. */
    //private FileTextPage textPage;
    
    /** The xml editor added to this multipage editor. */
    private RASXMLEditor rasXMLEditor;

    /** Flag indicating whether this editor is dirty or not. */
    private boolean dirty;

    /** The activation listener used in this editor. */
    private PartActivationListener partListener;

    /**
     * Loads the content of the given stream into the editors data model. The
     * status returned in {@link #getLoadStatus()} should be updated in this
     * method.
     * 
     * @param stream
     *            The input stream with the new content.
     */
    protected abstract void loadFile(InputStream stream);

    /**
     * @return The load status of the current data model with human readable
     *         error messages. Can be a {@link MultiStatus}.
     */
    public abstract IStatus getLoadStatus();

    /**
     * @return The textual content of the data model that is shown in this
     *         editor.
     */
    protected abstract String getSource();

    /** Creates the ui page of this multipage editor. */
    protected abstract FileFormPage[] createFormPages();

    /** Creates the text page of this multipage editor. */
    /*protected FileTextPage createTextPage() {
        return new FileTextPage(this);
    }*/
    
    protected RASXMLEditor createRASXMLEditor() {
        return new RASXMLEditor(this);
    }

    /**
     * Creates the activation listener used in this editor.
     */
    protected PartActivationListener createPartListener() {
        return new FileEditorActivationListener(this);
    }

    /**
     * @return The {@link PartActivationListener} created in
     *         {@link #createPartListener()}.
     */
    public PartActivationListener getPartListener() {
        return partListener;
    }

    /**
     * Initializes the editor with the underlying file and loads the content.
     */
    @Override
    public void init(IEditorSite site, IEditorInput input)
            throws PartInitException {
        Object file = input.getAdapter(IFile.class);
        if (!(file instanceof IFile)) {
            throw new PartInitException(
                    "Invalid editor input: Does not provide an IFile");
        }
        super.init(site, input);

        inputFile = (IFile) file;
        setPartName(input.getName());
        reload();

        // install part activation listener
        partListener = createPartListener();
        if (partListener != null) {
            getSite().getWorkbenchWindow().getPartService().addPartListener(
                    partListener);
        }
    }

    /**
     * Remove part activation listener on dispose.
     */
    @Override
    public void dispose() {
        if (partListener != null) {
            getSite().getWorkbenchWindow().getPartService().removePartListener(
                    partListener);
        }
        super.dispose();
    }

    /**
     * @return The file resource underlying this editor.
     */
    public IFile getInputFile() {
        return inputFile;
    }

    /**
     * Reloads the data model with the contents from the file resource by
     * calling {@link #loadFile(InputStream)}. If the resource is "out of
     * sync", it is synchronized before.
     */
    protected void reload() {
        try {
            if (!inputFile.isSynchronized(IResource.DEPTH_ZERO)) {
                inputFile.refreshLocal(IResource.DEPTH_ZERO, null);
            }
            loadFile(inputFile.getContents());
            fileReloaded();
        } catch (CoreException ex) {
            IStatus status = ex.getStatus();
            ErrorDialog.openError(getSite().getShell(),
                    "Error on loading file", status.getMessage(), status);
        }
    }

    /**
     * Helper method that activates the text page and opens an error dialog on
     * load errors.
     */
    private void fileReloaded() {
/*      if (textPage != null && !getLoadStatus().isOK()) {
            setActivePage(textPage.getId());
        }
        this.getPageCount()
        */
        if (rasXMLEditor != null && !getLoadStatus().isOK()) {
            setActivePage(rasXMLEditor.getId());
        }
        this.getPageCount();
        
        showErrorDialog();
    }

    /**
     * Shows an {@link ErrorDialog} with an accordingly message if the current
     * load status has errors.
     */
    public void showErrorDialog() {
        if (!getLoadStatus().isOK()) {
            String msg = "The source has errors. The form editor cannot be used "
                    + "until these errors are corrected.";
            ErrorDialog.openError(getSite().getShell(),
                    "Errors during loading file", msg, getLoadStatus());
        }
    }

    /**
     * Updates the text editor with the source that is provided via
     * {@link #getSource()}.
     */
    protected void updateTextContent() {
        //textPage.setContent(getSource());
        rasXMLEditor.setContent(getSource());
    }

    /**
     * Reloads the data model with the current contents from the source page.
     */
    public void reloadFromText(String source) {
        loadFile(new ByteArrayInputStream(source.getBytes()));
        fileReloaded();
    }

    /**
     * Sets the new dirty state of the editor.
     * 
     * @param dirty
     *            Set to true if the editor should be dirty and false otherwise.
     */
    public void setDirty(boolean dirty) {
        if (this.dirty != dirty) {
            this.dirty = dirty;
            editorDirtyStateChanged();
        }
    }

    /**
     * @return True if the file content has been changed inside the editor.
     */
    @Override
    public boolean isDirty() {
        return dirty;
    }

    /**
     * Adds a {@link FileFormPage} created in {@link #createFormPage()} and a
     * text editor created in {@link #createTextPage()}.
     */
    @Override
    protected void addPages() {
        try {
            formPageArray = createFormPages();
               
            // Asset
            addPage(0, formPageArray[0]);
            setPageText(0, "Asset");
                        
            // Solution
            addPage(1, formPageArray[1]);
            setPageText(1, "Solution");

            // Classification
            addPage(1, formPageArray[2]);
            setPageText(1, "Classification");

            addPage(2, formPageArray[3]);
            setPageText(2, "Usage");
            
            addPage(2, formPageArray[4]);
            setPageText(2, "Related Assets");

            //textPage = createTextPage();
            //int index = addPage(textPage, getEditorInput());
            //setPageText(index, "Source");

            rasXMLEditor = createRASXMLEditor();
            int index = addPage(rasXMLEditor, this.getEditorInput());
            setPageText(index, "Source");            
            
            if (getLoadStatus().isOK()) {
                setActivePage(formPageArray[0].getId());
            } else {
                // activate text page on load errors
                //setActivePage(textPage.getId());
                setActivePage(rasXMLEditor.getId());
            }
        } catch (PartInitException ex) {
            Activator.logError("Could not create editor pages.", ex);
        }
    }

    /**
     * In the file editor, the save mechanism from the text editor is used.
     */
    @Override
    public void doSave(IProgressMonitor monitor) {
        for (int i=0; i<formPageArray.length; i++) {
            if (getActivePage() == formPageArray[i].getIndex()) {
                // call doSave() to propagate the changes from the form-page to the
                // text editor
                formPageArray[i].doSave(monitor);
            }            
        }
        
        //textPage.doSave(monitor);
        rasXMLEditor.doSave(monitor);
        // clear dirty flag of the editor
        setDirty(false);
    }

    @Override
    public boolean isSaveAsAllowed() {
        return false;
    }

    @Override
    public void doSaveAs() {
        // unused interface method, save as is not allowed
    }

}
