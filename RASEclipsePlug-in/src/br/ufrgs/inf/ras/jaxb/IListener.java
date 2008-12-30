package br.ufrgs.inf.ras.jaxb;

/**
 * Interface that clients must implement if they want to be notified when an
 * asset changed. The listener can be registered for a classification using
 * {@link Classification#addClassificationListener(IClassificationListener)}.
 * 
 */
public interface IListener {
    public void objectChanged();
}
