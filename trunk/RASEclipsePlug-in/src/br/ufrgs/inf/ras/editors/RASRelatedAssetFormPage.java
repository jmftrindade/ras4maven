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
import br.ufrgs.inf.ras.jaxb.Description;
import br.ufrgs.inf.ras.jaxb.IListener;
import br.ufrgs.inf.ras.jaxb.RelatedAsset;
import br.ufrgs.inf.ras.util.ui.FileFormPage;

/**
 * The UI editor page in a {@link AssetFileEditor}. This page provides a user
 * interface for altering a asset solution from the underlying file.
 */
public class RASRelatedAssetFormPage extends FileFormPage {
    //private Asset asset;
    
    private RelatedAsset relatedAsset;
    private Description desc = new Description();
    
    private Text relatedAssetNameText, descriptionText;

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

            if (!controlChange && relatedAssetNameText != null) {
                // update user interface only if the change does not come from
                // the control itself
                //Solution solution = asset.getSolution();
                
                // FIXME get all artifacts instead of just the first
                relatedAssetNameText.setText(relatedAsset.getName());
                descriptionText.setText(relatedAsset.getDescription().getValue());
            }
            controlChange = false;
        }
    };

    private ModifyListener modifyListener = new ModifyListener() {
        @Override
        public void modifyText(ModifyEvent e) {
            controlChange = true;
            
            if (e.widget == relatedAssetNameText) {
                relatedAsset.setName(relatedAssetNameText.getText());
            } else {            
                desc.setValue(descriptionText.getText());
                relatedAsset.setDescription(desc);
            }
        }
    };

    public RASRelatedAssetFormPage(AssetFileEditor editor) {
        super(editor);
        // FIXME get the whole list, not just the first asset
        relatedAsset = editor.getAsset().getRelatedAssets().get(0);
        relatedAsset.addListener(dirtyListener);
    }

    @Override
    public void dispose() {
        relatedAsset.removeListener(dirtyListener);
        super.dispose();
    }

    @Override
    protected void createFormContent(IManagedForm managedForm) {
        FormToolkit toolkit = managedForm.getToolkit();

        managedForm.getForm().setText("Related Assets Editor");
        toolkit.decorateFormHeading(managedForm.getForm().getForm());

        Composite body = managedForm.getForm().getBody();
        GridLayoutFactory.swtDefaults().numColumns(2).applyTo(body);
        toolkit.paintBordersFor(body);

        toolkit.createLabel(body, "Asset Name:");
        relatedAssetNameText = toolkit.createText(body, relatedAsset.getName());
        relatedAssetNameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        relatedAssetNameText.addModifyListener(modifyListener);

        toolkit.createLabel(body, "Description:");
        descriptionText = toolkit.createText(body, relatedAsset.getDescription().getValue());
        descriptionText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        descriptionText.addModifyListener(modifyListener);
    }
}
