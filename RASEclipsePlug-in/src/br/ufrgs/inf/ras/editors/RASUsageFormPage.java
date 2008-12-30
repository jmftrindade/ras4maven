package br.ufrgs.inf.ras.editors;

import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.FormToolkit;

import br.ufrgs.inf.ras.jaxb.Artifact;
import br.ufrgs.inf.ras.jaxb.IListener;
import br.ufrgs.inf.ras.jaxb.Usage;
import br.ufrgs.inf.ras.util.ui.FileFormPage;

/**
 * The UI editor page in a {@link AssetFileEditor}. This page provides a user
 * interface for altering a asset solution from the underlying file.
 */
public class RASUsageFormPage extends FileFormPage {
	//private Asset asset;
	
	private Usage usage;
	
	private Text artifactText;

	/**
	 * Flag indicating whether the last change of the asset comes directly from
	 * a control on this page. Used to ignore to update the user interface in
	 * the dirtyListener below. To avoid this problem it would be better to use
	 * JFace Data Binding, but for this simple example it is not necessary.
	 */
	private boolean controlChange;

	private IListener dirtyListener = new IListener() {
		@Override
		public void objectChanged() {
			// mark page dirty, may be reset to false in {@link
			// #setActive(boolean)} if the change comes from the text page
			setDirty();

			if (!controlChange && artifactText != null) {
				// update user interface only if the change does not come from
				// the control itself
				artifactText.setText(usage.getArtifact());
			}
			controlChange = false;
		}
	};

	private ModifyListener modifyListener = new ModifyListener() {
		@Override
		public void modifyText(ModifyEvent e) {
			controlChange = true;
			
			if (e.widget == artifactText) {
				usage.setArtifact(artifactText.getText());
			}
		}
	};

	public RASUsageFormPage(AssetFileEditor editor) {
		super(editor);
		usage = editor.getAsset().getUsage();
		usage.addListener(dirtyListener);
	}

	@Override
	public void dispose() {
		usage.removeListener(dirtyListener);
		super.dispose();
	}

	@Override
	protected void createFormContent(IManagedForm managedForm) {
		FormToolkit toolkit = managedForm.getToolkit();

		managedForm.getForm().setText("Usage Editor");
		toolkit.decorateFormHeading(managedForm.getForm().getForm());

		Composite body = managedForm.getForm().getBody();
		GridLayoutFactory.swtDefaults().numColumns(2).applyTo(body);
		toolkit.paintBordersFor(body);

		toolkit.createLabel(body, "Artifact Name:");
		artifactText = toolkit.createText(body, usage.getArtifact());
		artifactText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		artifactText.addModifyListener(modifyListener);

	}
}
