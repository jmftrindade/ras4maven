package br.ufrgs.inf.ras.editors;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;

public class RASAssetFormPage2 extends FormPage {

    public RASAssetFormPage2(FormEditor editor, String id, String title) {
        super(editor, id, title);
    }

    protected void createFormContent(IManagedForm managedForm) {
        ScrolledForm form = managedForm.getForm();
        FormToolkit toolkit = managedForm.getToolkit();
        form.setText("CONFIG_TITLE");

        //form.setBackgroundImage( getImage(IMG_FORM_BANNER ) );
        toolkit.createCompositeSeparator( form );
           
        GridLayout layout = new GridLayout();
        layout.numColumns = 1;
        layout.marginWidth = 10;
        form.getBody().setLayout(layout);
      }
    
}
