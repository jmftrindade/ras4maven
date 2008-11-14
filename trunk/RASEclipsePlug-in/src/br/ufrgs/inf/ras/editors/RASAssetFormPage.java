package br.ufrgs.inf.ras.editors;

import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.widgets.FormToolkit;

import at.ssw.fileeditor.util.ui.FileFormPage;
import br.ufrgs.inf.ras.jaxb.Asset;
import br.ufrgs.inf.ras.jaxb.IAssetListener;

/**
 * The UI editor page in a {@link AssetFileEditor}. This page provides a user
 * interface for altering a asset solution from the underlying file.
 */
public class RASAssetFormPage extends FileFormPage {
	private Asset asset;

	private Text nameText, versionText, shortDescriptionText;

	/**
	 * Flag indicating whether the last change of the asset comes directly from
	 * a control on this page. Used to ignore to update the user interface in
	 * the dirtyListener below. To avoid this problem it would be better to use
	 * JFace Data Binding, but for this simple example it is not necessary.
	 */
	private boolean controlChange;

	private IAssetListener dirtyListener = new IAssetListener() {
		@Override
		public void assetChanged() {
			// mark page dirty, may be reset to false in {@link
			// #setActive(boolean)} if the change comes from the text page
			setDirty();

			if (!controlChange && nameText != null) {
				// update user interface only if the change does not come from
				// the control itself
				nameText.setText(asset.getName());
				versionText.setText(asset.getVersion());
				shortDescriptionText.setText(asset.getShortDescription());
			}
			controlChange = false;
		}
	};

	private ModifyListener modifyListener = new ModifyListener() {
		@Override
		public void modifyText(ModifyEvent e) {
			controlChange = true;
			if (e.widget == nameText) {
				asset.setName(nameText.getText());
			} else if (e.widget == versionText) {
				asset.setVersion(versionText.getText());
			} else {
				asset.setShortDescription(shortDescriptionText.getText());
			}
		}
	};

	public RASAssetFormPage(AssetFileEditor editor) {
		super(editor);
		asset = editor.getAsset();
		asset.addAssetListener(dirtyListener);
	}

	@Override
	public void dispose() {
		asset.removeAssetListener(dirtyListener);
		super.dispose();
	}

	@Override
	protected void createFormContent(IManagedForm managedForm) {
		FormToolkit toolkit = managedForm.getToolkit();

		managedForm.getForm().setText("Asset Editor");
		toolkit.decorateFormHeading(managedForm.getForm().getForm());

		Composite body = managedForm.getForm().getBody();
		GridLayoutFactory.swtDefaults().numColumns(2).applyTo(body);
		toolkit.paintBordersFor(body);

		toolkit.createLabel(body, "Name:");
		nameText = toolkit.createText(body, asset.getName());
		nameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		nameText.addModifyListener(modifyListener);

		toolkit.createLabel(body, "Version:");
		versionText = toolkit.createText(body, asset.getVersion());
		versionText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		versionText.addModifyListener(modifyListener);

		toolkit.createLabel(body, "Short Description:");
		shortDescriptionText = toolkit.createText(body, asset.getShortDescription());
		shortDescriptionText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		shortDescriptionText.addModifyListener(modifyListener);
	}
}
