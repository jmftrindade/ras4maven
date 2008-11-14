package at.ssw.fileeditor.util.ui;

import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWindowListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

/**
 * Abstract class for activation-listening that provides an abstract
 * handleActivation()-method sub-classes must implement. The method is called if
 * an part (e.g. an editor) was activated.
 * 
 * @author Harald Vogl
 */
public abstract class PartActivationListener implements IPartListener,
		IWindowListener {
	/** Cache of the active workbench part. */
	private IWorkbenchPart activePart;

	/**
	 * Flag indicating if the content of the editor was changed inside eclipse
	 * but outside the editor this activation listener is connected with.
	 */
	private boolean changedInOtherEditor;

	/**
	 * Default constructor that registers this listener in the PlatformUI.
	 */
	public PartActivationListener() {
		PlatformUI.getWorkbench().addWindowListener(this);
	}

	/**
	 * @return True if the content of the editor was changed inside eclipse but
	 *         outside the editor this activation listener is connected with.
	 */
	public boolean isChangedInOtherEditor() {
		return changedInOtherEditor;
	}

	/**
	 * Allows to set the changed status.
	 * 
	 * @param changedInOtherEditor
	 *            The new status. True if the content of the editor was changed
	 *            inside eclipse but outside the editor this activation listener
	 *            is connected with.
	 */
	public void setChangedInOtherEditor(boolean changedInOtherEditor) {
		this.changedInOtherEditor = changedInOtherEditor;
	}

	public IWorkbenchPart getActivePart() {
		return activePart;
	}

	public void dispose() {
		PlatformUI.getWorkbench().removeWindowListener(this);
	}

	public void partActivated(IWorkbenchPart part) {
	    System.out.println("activepart = " + part);
		activePart = part;
		handleActivation();
	}

	public void partDeactivated(IWorkbenchPart part) {
		activePart = null;
	}

	public void windowActivated(IWorkbenchWindow window) {
		/*
		 * Workaround for problem described in
		 * http://dev.eclipse.org/bugs/show_bug.cgi?id=11731 Will be removed
		 * when SWT has solved the problem.
		 */
		// comment copied from AbstractTextEditor
		window.getShell().getDisplay().asyncExec(new Runnable() {
			public void run() {
				handleActivation();
			}
		});
	}

	public abstract void handleActivation();

	public void partBroughtToTop(IWorkbenchPart part) {
	}

	public void partClosed(IWorkbenchPart part) {
	}

	public void partOpened(IWorkbenchPart part) {
	}

	public void windowClosed(IWorkbenchWindow window) {
	}

	public void windowDeactivated(IWorkbenchWindow window) {
	}

	public void windowOpened(IWorkbenchWindow window) {
	}
}
