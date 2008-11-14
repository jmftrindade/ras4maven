package br.ufrgs.inf.ras.jaxb;

/**
 * Interface that clients must implement if they want to be notified when an
 * asset changed. The listener can be registered for a person using
 * {@link Asset#addAssetListener(IAssetListener)}.
 * 
 */
public interface IAssetListener {
    public void assetChanged();
}
