package org.omg.maven.archiva.consumers.ras;



import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.io.FileUtils;
import org.apache.maven.archiva.configuration.ArchivaConfiguration;
import org.apache.maven.archiva.configuration.ConfigurationNames;
import org.apache.maven.archiva.configuration.FileTypes;
import org.apache.maven.archiva.configuration.ManagedRepositoryConfiguration;
import org.apache.maven.archiva.consumers.AbstractMonitoredConsumer;
import org.apache.maven.archiva.consumers.ConsumerException;
import org.apache.maven.archiva.consumers.KnownRepositoryContentConsumer;
import org.apache.maven.archiva.indexer.RepositoryContentIndex;
import org.apache.maven.archiva.indexer.RepositoryContentIndexFactory;
import org.apache.maven.archiva.indexer.RepositoryIndexException;
import org.apache.maven.archiva.indexer.filecontent.FileContentRecord;
import org.apache.maven.archiva.model.ArchivaArtifact;
import org.apache.maven.archiva.model.ArtifactReference;
import org.apache.maven.archiva.repository.ManagedRepositoryContent;
import org.apache.maven.archiva.repository.RepositoryContentFactory;
import org.apache.maven.archiva.repository.RepositoryException;
import org.apache.maven.archiva.repository.layout.LayoutException;
import org.apache.maven.archiva.repository.metadata.MetadataTools;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.Initializable;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.InitializationException;
import org.codehaus.plexus.registry.Registry;
import org.codehaus.plexus.registry.RegistryListener;

/**
 * IndexContentConsumer - generic full file content indexing consumer.
 *
 * @version $Id: RasConsumer.java 1 2008-12-04 06:33:35Z felipe $
 * @plexus.component role="org.apache.maven.archiva.consumers.KnownRepositoryContentConsumer"
 * role-hint="index-ras"
 * instantiation-strategy="per-lookup"
 */
public class RasConsumer
	extends AbstractMonitoredConsumer
	implements KnownRepositoryContentConsumer, RegistryListener, Initializable
{
	    /**
	     * @plexus.configuration default-value="index-ras"
	     */
	    private String id;

	    /**
	     * @plexus.configuration default-value="RAS Content indexer"
	     */
	    private String description;

	    /**
	     * @plexus.requirement
	     */
	    private ArchivaConfiguration configuration;

	    /**
	     * @plexus.requirement
	     */
	    private FileTypes filetypes;

	    /**
	     * @plexus.requirement
	     */
	    private RepositoryContentFactory repositoryFactory;
	    
	    /**
	     * @plexus.requirement role-hint="lucene"
	     */
	    private RepositoryContentIndexFactory indexFactory;
	    
	    private List<String> includes = new ArrayList<String>();

	    private RepositoryContentIndex index;

	    private ManagedRepositoryContent repository;

	    private File repositoryDir;

	    public String getId()
	    {
	        return this.id;
	    }

	    public String getDescription()
	    {
	        return this.description;
	    }

	    public boolean isPermanent()
	    {
	        return false;
	    }

	    public List<String> getExcludes()
	    {
	        return null;
	    }

	    public List<String> getIncludes()
	    {
	        return this.includes;
	    }

	    public void beginScan( ManagedRepositoryConfiguration repo, Date whenGathered )
	        throws ConsumerException
	    {
	        try
	        {
	            this.repository = repositoryFactory.getManagedRepositoryContent( repo.getId() );
	            this.repositoryDir = new File( repository.getRepoRoot() );
	            this.index = indexFactory.createFileContentIndex( repository.getRepository() );
	        }
	        catch ( RepositoryException e )
	        {
	            throw new ConsumerException( "Unable to start IndexContentConsumer: " + e.getMessage(), e );
	        }
	    }

	    public void processFile( String path )
	        throws ConsumerException
	    {
	        if ( !path.endsWith(".ras") )
	        {
	            //log.debug( "File is a metadata file. Not indexing." );
	            return;
	        }
	        
	        FileContentRecord record = new FileContentRecord();
	        try
	        {
	            File file = new File( repositoryDir, path );
	            record.setRepositoryId( this.repository.getId() );
	            record.setFilename( path );
	   
	    		List<String> paths = new Vector<String>();
	    		
	    		paths.add("//@shortDescription");
	    		paths.add("//classification/descriptorGroup/freeFormDescriptor[@name=\"Keyword\"]/description/@value");
	    		
	    		RassetReader indexer = new RassetReader(paths);
	    		
	    		String indexWords = indexer.getIndexWords(file);
	            
	            
	            record.setContents(indexWords);

	            // Test for possible artifact reference syntax.
//	            try
//	            {
//	                ArtifactReference ref = repository.toArtifactReference( path );
//	                ArchivaArtifact artifact = new ArchivaArtifact( ref );
//	                artifact.getModel().setRepositoryId( repository.getId() );
//	                record.setArtifact( artifact );                
//	            }
//	            catch ( LayoutException e )
//	            {
//	                // Not an artifact.
//	            }

	            index.modifyRecord( record );
	        }
	        catch ( RepositoryIndexException e )
	        {
	            //triggerConsumerError( INDEX_ERROR, "Unable to index file contents: " + e.getMessage() );
	        } catch (XPathExpressionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }

	    public void completeScan()
	    {
	        /* do nothing */
	    }

	    public void afterConfigurationChange( Registry registry, String propertyName, Object propertyValue )
	    {
	        if ( ConfigurationNames.isRepositoryScanning( propertyName ) )
	        {
	            initIncludes();
	        }
	    }

	    public void beforeConfigurationChange( Registry registry, String propertyName, Object propertyValue )
	    {
	        /* do nothing */
	    }

	    private void initIncludes()
	    {
	        includes.clear();

	        includes.addAll( filetypes.getFileTypePatterns( FileTypes.INDEXABLE_CONTENT ) );
	    }

	    public void initialize()
	        throws InitializationException
	    {
	        configuration.addChangeListener( this );

	        initIncludes();
	    }
}
