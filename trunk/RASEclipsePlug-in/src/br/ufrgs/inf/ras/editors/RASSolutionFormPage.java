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
import br.ufrgs.inf.ras.jaxb.Asset;
import br.ufrgs.inf.ras.jaxb.Description;
import br.ufrgs.inf.ras.jaxb.IListener;
import br.ufrgs.inf.ras.jaxb.Solution;
import br.ufrgs.inf.ras.util.ui.FileFormPage;

/**
 * The UI editor page in a {@link AssetFileEditor}. This page provides a user
 * interface for altering a asset solution from the underlying file.
 */
public class RASSolutionFormPage extends FileFormPage {
	//private Asset asset;
	
	private Solution solution;
	private Artifact artifact;
	private Description desc;
	
	private Text artifactNameText, artifactTypeText, artifactVersionText, 
	    descriptionText;

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

			if (!controlChange && artifactNameText != null) {
				// update user interface only if the change does not come from
				// the control itself
			    //Solution solution = asset.getSolution();
			    
			    // FIXME get all artifacts instead of just the first
				artifactNameText.setText(artifact.getName());
				artifactTypeText.setText(artifact.getType());
				artifactVersionText.setText(artifact.getVersion());
				descriptionText.setText(artifact.getDescription().getValue());
			}
			controlChange = false;
		}
	};

	private ModifyListener modifyListener = new ModifyListener() {
		@Override
		public void modifyText(ModifyEvent e) {
			controlChange = true;
			
			Artifact firstArtifact = solution.getArtifacts().get(0);
			
			if (e.widget == artifactNameText) {
				firstArtifact.setName(artifactNameText.getText());
			} else if (e.widget == artifactTypeText) {
			    firstArtifact.setType(artifactTypeText.getText());                
			} else if (e.widget == artifactVersionText) {
			    firstArtifact.setVersion(artifactVersionText.getText());
			} else {
			    
			    desc.setValue(descriptionText.getText());
			    firstArtifact.setDescription(desc);
			}
		}
	};

	public RASSolutionFormPage(AssetFileEditor editor) {
		super(editor);
		solution = editor.getAsset().getSolution();
		artifact = solution.getArtifacts().get(0);
		desc = artifact.getDescription();
		artifact.addListener(dirtyListener);
	}

	@Override
	public void dispose() {
		artifact.removeListener(dirtyListener);
		super.dispose();
	}

	@Override
	protected void createFormContent(IManagedForm managedForm) {
		FormToolkit toolkit = managedForm.getToolkit();

		managedForm.getForm().setText("Solution Editor");
		toolkit.decorateFormHeading(managedForm.getForm().getForm());

		Composite body = managedForm.getForm().getBody();
		GridLayoutFactory.swtDefaults().numColumns(2).applyTo(body);
		toolkit.paintBordersFor(body);

		toolkit.createLabel(body, "Artifact Name:");
		artifactNameText = toolkit.createText(body, artifact.getName());
		artifactNameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		artifactNameText.addModifyListener(modifyListener);

	    toolkit.createLabel(body, "Type:");
	    artifactTypeText = toolkit.createText(body, artifact.getType());
	    artifactTypeText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	    artifactTypeText.addModifyListener(modifyListener);
	        
		toolkit.createLabel(body, "Version:");
		artifactVersionText = toolkit.createText(body, artifact.getVersion());
		artifactVersionText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		artifactVersionText.addModifyListener(modifyListener);

		toolkit.createLabel(body, "Description:");
		descriptionText = toolkit.createText(body, artifact.getDescription().getValue());
		descriptionText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		descriptionText.addModifyListener(modifyListener);
	}
}
