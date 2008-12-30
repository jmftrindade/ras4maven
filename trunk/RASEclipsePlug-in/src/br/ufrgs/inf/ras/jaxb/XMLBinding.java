package br.ufrgs.inf.ras.jaxb;

import java.io.Reader;
import java.io.StringWriter;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.transform.Result;
import javax.xml.validation.Schema;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;

import br.ufrgs.inf.ras.Activator;

/**
 * Class with static utility methods to load and save Assets in an xml-file.
 * 
 * @author Harald Vogl
 */
public class XMLBinding {
	private static JAXBContext context;

	private static Schema schema;

	private static class SchemaResolver extends SchemaOutputResolver {
		private Result result;

		private SchemaResolver(Result result) {
			this.result = result;
		}

		@Override
		public Result createOutput(String namespaceUri, String suggestedFileName) {
			return result;
		}
	}

	private static class ErrorHandler implements ValidationEventHandler {
		private MultiStatus status;

		private ErrorHandler(MultiStatus status) {
			this.status = status;
		}

		public boolean handleEvent(ValidationEvent event) {
			IStatus newStatus = new Status(IStatus.ERROR,
					Activator.PLUGIN_ID,
					event.getLocator().getLineNumber(), event.getMessage(),
					null);
			status.add(newStatus);
			// Resume to collect as many errors as possible.
			return true;
		}
	}

	/**
	 * Prevent creation of instances.
	 */
	private XMLBinding() {
		// Never called.
	}

	/**
	 * Creates the JAXB context and the schema definition.
	 */
	static {
		try {
			context = JAXBContext.newInstance("br.ufrgs.inf.ras.jaxb");
		} catch (Throwable ex) {
		    ex.printStackTrace();
			Activator.logError("Could not initialize JAXB", ex);
		}
	}

	/**
	 * Helper method to invoke the marshaller.
	 * 
	 * @param obj
	 *            The object to marshal.
	 * @return The xml representation of the object.
	 */
	private static String marshal(Object obj) {
		try {
			StringWriter writer = new StringWriter();
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.setSchema(schema);
			marshaller.marshal(obj, writer);
			return writer.toString();

		} catch (Throwable ex) {
			Activator.logError("Could not marshal data", ex);
			return "";
		}
	}

	/**
	 * Helper method to invoke the unmarshaller.
	 * 
	 * @param xml
	 *            The xml representation.
	 * @param status
	 *            The multi status filled during unmarshaling.
	 * @return The unmarshalled object, or <code>null</code> on error.
	 */
	private static Object unmarshal(Reader xml, MultiStatus status) {
		try {
			Unmarshaller unmarshaller = context.createUnmarshaller();
			unmarshaller.setSchema(schema);
			unmarshaller.setEventHandler(new ErrorHandler(status));
			return unmarshaller.unmarshal(xml);
		} catch (Throwable ex) {
		    ex.printStackTrace();
			if (status.isOK()) {
				status.add(new Status(IStatus.ERROR, Activator.PLUGIN_ID,
						ex.getMessage()));
			}
			return null;
		}
	}

	/**
	 * Saves an asset to an xml representation and returns the textual file
	 * content.
	 * 
	 * @param asset
	 *            The asset to save.
	 * @return The content of the xml file.
	 */
	public static String saveAsset(Asset asset) {
		return marshal(asset);
	}

	/**
	 * Loads an asset from an xml representation. The content is installed in an
	 * existing asset, i.e. all properties of this asset are changed. Only the
	 * listeners of the asset are not modified. If the xml content is not
	 * well-formed or not valid, a MultiStatus is returned that contains human
	 * readable messages.
	 * 
	 * @param asset
	 *            The asset that is overridden with the new contents.
	 * @param xml
	 *            The xml representation to load.
	 * @return The load status with human readable messages if problems
	 *         occurred.
	 */
	public static MultiStatus loadAsset(Asset asset, Reader xml) {
		MultiStatus status = new MultiStatus(Activator.PLUGIN_ID, 0,
				"Load Status", null);
		Object obj = unmarshal(xml, status);

		// TODO: modify here so we unmarshall all objects, including
		// description, usage, classification, etc
		if (status.isOK() && obj instanceof Asset) {
			// when unmarshalling succeeds, then propagate loaded values
			// to our object.
			Asset newAsset = (Asset) obj;
			asset.setName(newAsset.getName());
            asset.setVersion(newAsset.getVersion());
            asset.setShortDescription(newAsset.getShortDescription());
            
            // classification/descriptorGroup/freeFormDescriptor/description
            asset.setClassification(newAsset.getClassification());
            
            // solution/artifact/description
            asset.setSolution(newAsset.getSolution());
            
            // usage
            asset.setUsage(newAsset.getUsage());
            
            // relatedAsset
            List<RelatedAsset> raList = newAsset.getRelatedAssets();
            for (RelatedAsset ra : raList) {
                asset.getRelatedAssets().add(ra);
            }
            
            // profile
            asset.setProfile(newAsset.getProfile());
            
            // other items...
            asset.setAccessRights(newAsset.getAccessRights());
            asset.setDate(newAsset.getDate());
            asset.setDescription(newAsset.getDescription());
            asset.setId(newAsset.getId());
            asset.setState(newAsset.getState());
            
            
		} else if (status.isOK()) {
		    System.err.println("Not an instance of Asset!");
		    System.out.println(obj.getClass().getName());
//		    System.err.println("File does not contain a valid asset.");
//			status.add(new Status(IStatus.ERROR, Activator.PLUGIN_ID,
//					"File does not contain a valid asset."));
		}

		return status;
	}
}
