package br.ufrgs.inf.ras.editors;

import java.io.InputStream;
import java.io.InputStreamReader;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;

import br.ufrgs.inf.ras.jaxb.Asset;
import br.ufrgs.inf.ras.jaxb.IListener;
import br.ufrgs.inf.ras.jaxb.XMLBinding;
import br.ufrgs.inf.ras.util.ui.FileEditor;
import br.ufrgs.inf.ras.util.ui.FileFormPage;

public class AssetFileEditor extends FileEditor {
    private Asset asset = new Asset();

    private MultiStatus loadStatus;

    private IListener dirtyListener = new IListener() {
        @Override
        public void objectChanged() {
            setDirty(true);
        }
    };

    protected Asset getAsset() {
        return asset;
    }

    @Override
    protected FileFormPage[] createFormPages() {
        FileFormPage formPageList[] = new FileFormPage[5];
                
        formPageList[0] = new RASAssetFormPage(this);
        formPageList[1] = new RASSolutionFormPage(this);
        formPageList[2] = new RASClassificationFormPage(this);
        formPageList[3] = new RASUsageFormPage(this);
        formPageList[4] = new RASRelatedAssetFormPage(this);        
        
        return formPageList;
    }

    @Override
    protected void loadFile(InputStream stream) {
        asset.removeListener(dirtyListener);

        loadStatus = XMLBinding.loadAsset(asset,
                new InputStreamReader(stream));
        
        asset.addListener(dirtyListener);
        asset.getSolution().getArtifacts().get(0).addListener(dirtyListener);
        asset.getUsage().addListener(dirtyListener);
        asset.getRelatedAssets().get(0).addListener(dirtyListener);
        asset.getClassification().getDescriptorGroups().get(0).addListener(dirtyListener);
        asset.getClassification().getDescriptorGroups().get(0).getFreeFormDescriptors()
                .get(0).addListener(dirtyListener);
        asset.getClassification().getDescriptorGroups().get(0).getFreeFormDescriptors()
            .get(0).getDescription().addListener(dirtyListener);
    }

    @Override
    public IStatus getLoadStatus() {
        return loadStatus;
    }

    @Override
    protected String getSource() {
        return XMLBinding.saveAsset(asset);
    }

    @Override
    public void dispose() {
        asset.removeListener(dirtyListener);
        asset.getSolution().getArtifacts().get(0).removeListener(dirtyListener);
        asset.getUsage().removeListener(dirtyListener);
        asset.getRelatedAssets().get(0).addListener(dirtyListener);
        asset.getClassification().getDescriptorGroups().get(0).removeListener(dirtyListener);
        asset.getClassification().getDescriptorGroups().get(0).getFreeFormDescriptors()
                .get(0).removeListener(dirtyListener);
        asset.getClassification().getDescriptorGroups().get(0).getFreeFormDescriptors()
            .get(0).getDescription().removeListener(dirtyListener);
        super.dispose();
    }

}
