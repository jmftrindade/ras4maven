package br.ufrgs.inf.ras.editors;

import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.FormToolkit;

import br.ufrgs.inf.ras.jaxb.Classification;
import br.ufrgs.inf.ras.jaxb.Description;
import br.ufrgs.inf.ras.jaxb.DescriptorGroup;
import br.ufrgs.inf.ras.jaxb.FreeFormDescriptor;
import br.ufrgs.inf.ras.jaxb.IListener;
import br.ufrgs.inf.ras.util.ui.FileFormPage;

/**
 * The UI editor page in a {@link AssetFileEditor}. This page provides a user
 * interface for altering a asset solution from the underlying file.
 */
public class RASClassificationFormPage extends FileFormPage {
	private Classification classification;
	private DescriptorGroup descGroup;
	private FreeFormDescriptor freeFormDesc;
	private Description desc;

	private Text descGroupNameText, freeFormNameText, descriptionText;

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

			
			if (!controlChange && descGroupNameText != null) {
				// update user interface only if the change does not come from
				// the control itself
			    descGroupNameText.setText(descGroup.getName());
			    freeFormNameText.setText(freeFormDesc.getName());
			    descriptionText.setText(freeFormDesc.getDescription().getValue());
			}
			controlChange = false;
		}
	};

	private ModifyListener modifyListener = new ModifyListener() {
		@Override
		public void modifyText(ModifyEvent e) {
			controlChange = true;
			if (e.widget == descGroupNameText) {
			    descGroup.setName(descGroupNameText.getText());
			} else if (e.widget == freeFormNameText) {
			    freeFormDesc.setName(freeFormNameText.getText());
			} else {
                desc.setValue(descriptionText.getText());
                freeFormDesc.setDescription(desc);
			}
		}
	};

	public RASClassificationFormPage(AssetFileEditor editor) {
		super(editor);
		classification = editor.getAsset().getClassification();
		descGroup = classification.getDescriptorGroups().get(0);
		freeFormDesc = descGroup.getFreeFormDescriptors().get(0);
		desc = freeFormDesc.getDescription();
		
		descGroup.addListener(dirtyListener);
		freeFormDesc.addListener(dirtyListener);
        desc.addListener(dirtyListener);
	}

	@Override
	public void dispose() {
        descGroup.removeListener(dirtyListener);
        freeFormDesc.removeListener(dirtyListener);
        desc.removeListener(dirtyListener);
		super.dispose();
	}

	@Override
	protected void createFormContent(IManagedForm managedForm) {
		FormToolkit toolkit = managedForm.getToolkit();

		managedForm.getForm().setText("Classification Editor");
		toolkit.decorateFormHeading(managedForm.getForm().getForm());

		Composite body = managedForm.getForm().getBody();
		GridLayoutFactory.swtDefaults().numColumns(2).applyTo(body);
		toolkit.paintBordersFor(body);

		toolkit.createLabel(body, "Descriptor Group:");
		descGroupNameText = toolkit.createText(body, descGroup.getName());
		descGroupNameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		descGroupNameText.addModifyListener(modifyListener);

		toolkit.createLabel(body, "Free Form Descriptor:");
		freeFormNameText = toolkit.createText(body, freeFormDesc.getName());
		freeFormNameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		freeFormNameText.addModifyListener(modifyListener);

        toolkit.createLabel(body, "Description:");
        descriptionText = toolkit.createText(body, desc.getValue());
        descriptionText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        descriptionText.addModifyListener(modifyListener);
	}
}
