package org.omg.maven.archiva.consumers.ras;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.xml.xpath.*;
import org.xml.sax.InputSource;

public class RassetReader {

	final String RAS_FILE = "rasset.xml";
	private List<String> xpaths;


	public RassetReader(List<String> _xpaths) {
		xpaths = _xpaths;
	}
	
	public String getIndexWords(File zipFile) throws IOException, XPathExpressionException	{
		String rasFile = extractRasFile(zipFile);
		
		File doc = new File(rasFile);
		
		XPathFactory factory = XPathFactory.newInstance();

		String words = new String();
		for(String path: xpaths){
			InputSource source = new InputSource(new FileInputStream(doc));
			XPath aPath = factory.newXPath();
			String value = aPath.evaluate(path, source);
			if (value != null)
				words += value + " ";
		}
		
		doc.delete();
		return words;
	}
	
	private String extractRasFile(File zipFile) throws IOException {
		ZipInputStream in = new ZipInputStream(new FileInputStream(zipFile));
		String fullZipPath = zipFile.getAbsolutePath();
		String rasFilePath = fullZipPath.substring(0, fullZipPath.lastIndexOf('\\') + 1);
		rasFilePath += RAS_FILE;
		
		FileOutputStream out = new FileOutputStream(rasFilePath);
		
		ZipEntry entry;
		while ((entry = in.getNextEntry()) != null){
			if (entry.getName().endsWith(RAS_FILE)){
				int len;
				byte[] buffer = new byte[1024];
				
				while ((len = in.read(buffer)) > 0){
					out.write(buffer, 0, len);
				}
			}
		}
		
		in.close();
		out.close();
		
		return rasFilePath;
	}
	
	public List<String> getXPathList(){
		return xpaths;
	}
	
	
}
