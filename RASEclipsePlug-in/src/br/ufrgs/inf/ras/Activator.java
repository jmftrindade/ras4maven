package br.ufrgs.inf.ras;

import java.net.URL;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.FormColors;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

    // The plug-in ID
    public static final String PLUGIN_ID = "RASEclipsePlug_in";

    // The shared instance
    private static Activator plugin;

    //Resource bundle.
    private ResourceBundle resourceBundle;
    private FormColors formColors;
    public static final String IMG_FORM_BG = "formBg"; //$NON-NLS-1$
    public static final String IMG_LARGE = "large"; //$NON-NLS-1$
    public static final String IMG_HORIZONTAL = "horizontal"; //$NON-NLS-1$
    public static final String IMG_VERTICAL = "vertical"; //$NON-NLS-1$
    public static final String IMG_SAMPLE = "sample"; //$NON-NLS-1$
    public static final String IMG_WIZBAN = "wizban"; //$NON-NLS-1$
    public static final String IMG_LINKTO_HELP = "linkto_help"; //$NON-NLS-1$
    public static final String IMG_HELP_TOPIC = "help_topic"; //$NON-NLS-1$
    public static final String IMG_CLOSE = "close"; //$NON-NLS-1$

    /**
     * The constructor
     */
    public Activator() {
        plugin = this;
        try {
            resourceBundle = ResourceBundle
            .getBundle("org.eclipse.ui.forms.examples.internal.ExamplesPluginResources"); //$NON-NLS-1$
        } catch (MissingResourceException x) {
            resourceBundle = null;
        }
    }


    protected void initializeImageRegistry(ImageRegistry registry) {
        registerImage(registry, IMG_FORM_BG, "form_banner.gif"); //$NON-NLS-1$
        registerImage(registry, IMG_LARGE, "large_image.gif"); //$NON-NLS-1$
        registerImage(registry, IMG_HORIZONTAL, "th_horizontal.gif"); //$NON-NLS-1$
        registerImage(registry, IMG_VERTICAL, "th_vertical.gif"); //$NON-NLS-1$
        registerImage(registry, IMG_SAMPLE, "sample.gif"); //$NON-NLS-1$
        registerImage(registry, IMG_WIZBAN, "newprj_wiz.gif"); //$NON-NLS-1$
        registerImage(registry, IMG_LINKTO_HELP, "linkto_help.gif"); //$NON-NLS-1$
        registerImage(registry, IMG_HELP_TOPIC, "help_topic.gif"); //$NON-NLS-1$
        registerImage(registry, IMG_CLOSE, "close_view.gif"); //$NON-NLS-1$
    }

    private void registerImage(ImageRegistry registry, String key,
            String fileName) {
        try {
            IPath path = new Path("icons/" + fileName); //$NON-NLS-1$
            URL url = find(path);
            if (url!=null) {
                ImageDescriptor desc = ImageDescriptor.createFromURL(url);
                registry.put(key, desc);
            }
        } catch (Exception e) {
        }
    }

    public FormColors getFormColors(Display display) {
        if (formColors == null) {
            formColors = new FormColors(display);
            formColors.markShared();
        }
        return formColors;
    }


    /*
     * (non-Javadoc)
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
     */
    public void start(BundleContext context) throws Exception {
        super.start(context);
        plugin = this;
    }

    /*
     * (non-Javadoc)
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
     */
    public void stop(BundleContext context) throws Exception {
        plugin = null;
        super.stop(context);
    }

    /**
     * Returns the shared instance
     *
     * @return the shared instance
     */
    public static Activator getDefault() {
        return plugin;
    }

    /**
     * Returns the string from the plugin's resource bundle, or 'key' if not
     * found.
     */
    public static String getResourceString(String key) {
        ResourceBundle bundle = Activator.getDefault().getResourceBundle();
        try {
            return (bundle != null ? bundle.getString(key) : key);
        } catch (MissingResourceException e) {
            return key;
        }
    }
    /**
     * Returns the plugin's resource bundle,
     */
    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    public Image getImage(String key) {
        return getImageRegistry().get(key);
    }

    /**
     * Returns an image descriptor for the image file at the given
     * plug-in relative path
     *
     * @param path the path
     * @return the image descriptor
     */
    public static ImageDescriptor getImageDescriptor(String path) {
        return imageDescriptorFromPlugin(PLUGIN_ID, path);
    }

    /**
     * Utility method to report an error message to the platform log.
     * 
     * @param message
     *            The human-readable message.
     * @param ex
     *            The exception, or null if not applicable.
     */
    public static void logError(String message, Throwable ex) {
        IStatus status = new Status(IStatus.ERROR, PLUGIN_ID, message, ex);
        Bundle bundle = Platform.getBundle(PLUGIN_ID);
        Platform.getLog(bundle).log(status);
    }
}
