package br.ufrgs.inf.ras.editors;

import java.io.InputStream;
import java.io.InputStreamReader;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;

import at.ssw.fileeditor.util.ui.FileEditor;
import at.ssw.fileeditor.util.ui.FileFormPage;
import br.ufrgs.inf.ras.jaxb.Asset;
import br.ufrgs.inf.ras.jaxb.IAssetListener;
import br.ufrgs.inf.ras.jaxb.XMLBinding;

public class AssetFileEditor extends FileEditor {
    private Asset asset = new Asset();

    private MultiStatus loadStatus;

    private IAssetListener dirtyListener = new IAssetListener() {
        @Override
        public void assetChanged() {
            setDirty(true);
        }
    };

    protected Asset getAsset() {
        return asset;
    }

    @Override
    protected FileFormPage createFormPage() {
        return new RASAssetFormPage(this);
    }

    @Override
    protected void loadFile(InputStream stream) {
        asset.removeAssetListener(dirtyListener);

        loadStatus = XMLBinding.loadAsset(asset,
                new InputStreamReader(stream));
        
        asset.addAssetListener(dirtyListener);
    }

    @Override
    protected IStatus getLoadStatus() {
        return loadStatus;
    }

    @Override
    protected String getSource() {
        return XMLBinding.saveAsset(asset);
    }

    @Override
    public void dispose() {
        asset.removeAssetListener(dirtyListener);
        super.dispose();
    }
}
