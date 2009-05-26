package org.apache.maven.archiva.web.ras;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.Decoder;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.maven.archiva.database.ArchivaDAO;
import org.apache.maven.archiva.database.ArchivaDatabaseException;
import org.apache.maven.archiva.indexer.search.CrossRepositorySearch;
import org.apache.maven.archiva.indexer.search.SearchResultHit;
import org.apache.maven.archiva.indexer.search.SearchResultLimits;
import org.apache.maven.archiva.indexer.search.SearchResults;
import org.apache.maven.archiva.security.AccessDeniedException;
import org.apache.maven.archiva.security.ArchivaRoleConstants;
import org.apache.maven.archiva.security.ArchivaSecurityException;
import org.apache.maven.archiva.security.ArchivaXworkUser;
import org.apache.maven.archiva.security.PrincipalNotFoundException;
import org.apache.maven.archiva.security.ServletAuthenticator;
import org.apache.maven.archiva.security.UserRepositories;
import org.codehaus.plexus.redback.authentication.AuthenticationException;
import org.codehaus.plexus.redback.authentication.AuthenticationResult;
import org.codehaus.plexus.redback.authorization.AuthorizationException;
import org.codehaus.plexus.redback.authorization.UnauthorizedException;
import org.codehaus.plexus.redback.policy.AccountLockedException;
import org.codehaus.plexus.redback.policy.MustChangePasswordException;
import org.codehaus.plexus.redback.system.SecuritySession;
import org.codehaus.plexus.redback.users.UserNotFoundException;
import org.codehaus.plexus.redback.xwork.filter.authentication.HttpAuthenticator;
import org.codehaus.plexus.spring.PlexusToSpringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


public class RasSearchServlet
    extends HttpServlet
{
    public static final String MIME_TYPE = "text; charset=UTF-8";

    private static final String COULD_NOT_AUTHENTICATE_USER = "Could not authenticate user";

    private static final String USER_NOT_AUTHORIZED = "User not authorized to access search";

    private Logger log = LoggerFactory.getLogger( RasSearchServlet.class );

    private WebApplicationContext wac;

    private UserRepositories userRepositories;

    private ServletAuthenticator servletAuth;

    private HttpAuthenticator httpAuth;
    
    private ArchivaXworkUser archivaXworkUser;
    
    private ArchivaDAO dao;
    
    private CrossRepositorySearch crossRepo;

    public void init( javax.servlet.ServletConfig servletConfig )
        throws ServletException
    {
        super.init( servletConfig );
        wac = WebApplicationContextUtils.getRequiredWebApplicationContext( servletConfig.getServletContext() );
        userRepositories =
            (UserRepositories) wac.getBean( PlexusToSpringUtils.buildSpringId( UserRepositories.class.getName() ) );
        servletAuth =
            (ServletAuthenticator) wac.getBean( PlexusToSpringUtils.buildSpringId( ServletAuthenticator.class.getName() ) );
        httpAuth =
            (HttpAuthenticator) wac.getBean( PlexusToSpringUtils.buildSpringId( HttpAuthenticator.ROLE, "basic" ) );
        archivaXworkUser = (ArchivaXworkUser) wac.getBean( PlexusToSpringUtils.buildSpringId( ArchivaXworkUser.class ) );
		//instantiate DAO
        dao = (ArchivaDAO)wac.getBean(PlexusToSpringUtils.buildSpringId(ArchivaDAO.class.getName(),"jdo"));
        
        crossRepo = (CrossRepositorySearch)wac.getBean(PlexusToSpringUtils.buildSpringId(CrossRepositorySearch.class.getName(), "default"));
    }

    public void doGet( HttpServletRequest req, HttpServletResponse res )
        throws ServletException, IOException
    {
    	String keywords = req.getParameter("keyword");
    	SearchResultLimits limits = new SearchResultLimits(0);
    	limits.setPageSize(100);
    	//limits.setSelectedPage(SearchResultLimits.ALL_PAGES);
    	
        
        List<String> repos = getObservableRepos( archivaXworkUser.getGuest());
        SearchResults results = crossRepo.searchForTerm(archivaXworkUser.getGuest(), repos, keywords, limits);
        
        String body = new String();
        List<SearchResultHit> hits = results.getHits();
        for(SearchResultHit sh: hits)
        {
        	body += "Artifact Reference: ";
        	body += sh.getGroupId() + ".";
        	body += sh.getArtifactId() + ".";
        	body += sh.getVersion() + "\n";
        }
        	
        
        res.setContentType(MIME_TYPE);
        res.setContentLength(body.length());
        res.getWriter().print(body);

        //res.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED, keywords);
        
//        try
//        {
//			//Find 
//        }
//        catch ( UserNotFoundException unfe )
//        {
//            log.debug( COULD_NOT_AUTHENTICATE_USER, unfe );
//            res.sendError( HttpServletResponse.SC_UNAUTHORIZED, COULD_NOT_AUTHENTICATE_USER );
//        }
//        catch ( AccountLockedException acce )
//        {            
//            res.sendError( HttpServletResponse.SC_UNAUTHORIZED, COULD_NOT_AUTHENTICATE_USER );
//        }
//        catch ( AuthenticationException authe )
//        {   
//            log.debug( COULD_NOT_AUTHENTICATE_USER, authe );
//            res.sendError( HttpServletResponse.SC_UNAUTHORIZED, COULD_NOT_AUTHENTICATE_USER );
//        }
//        catch ( MustChangePasswordException e )
//        {            
//            res.sendError( HttpServletResponse.SC_UNAUTHORIZED, COULD_NOT_AUTHENTICATE_USER );
//        }
//        catch ( UnauthorizedException e )
//        {
//            log.debug( e.getMessage() );
//            if ( repoId != null )
//            {
//                res.setHeader("WWW-Authenticate", "Basic realm=\"Repository Archiva Managed " + repoId + " Repository" );
//            }
//            else
//            {
//                res.setHeader("WWW-Authenticate", "Basic realm=\"Artifact " + groupId + ":" + artifactId );
//            }
//            
//            res.sendError( HttpServletResponse.SC_UNAUTHORIZED, USER_NOT_AUTHORIZED );
//        }
    }

    /**
     * Basic authentication.
     * 
     * @param req
     * @param repositoryId TODO
     * @param groupId TODO
     * @param artifactId TODO
     * @return
     */
    private boolean isAllowed( HttpServletRequest req, String repositoryId, String groupId, String artifactId )
        throws UserNotFoundException, AccountLockedException, AuthenticationException, MustChangePasswordException,
        UnauthorizedException
    {
        String auth = req.getHeader( "Authorization" );
        List<String> repoIds = new ArrayList<String>();

        if ( repositoryId != null )
        {
            repoIds.add( repositoryId );
        }
        else if ( artifactId != null && groupId != null )
        {
            if ( auth != null )
            {
                if ( !auth.toUpperCase().startsWith( "BASIC " ) )
                {
                    return false;
                }

                Decoder dec = new Base64();
                String usernamePassword = "";

                try
                {
                    usernamePassword = new String( (byte[]) dec.decode( auth.substring( 6 ).getBytes() ) );
                }
                catch ( DecoderException ie )
                {
                    log.warn( "Error decoding username and password.", ie.getMessage() );
                }

                if ( usernamePassword == null || usernamePassword.trim().equals( "" ) )
                {
                    repoIds = getObservableRepos( archivaXworkUser.getGuest() );
                }
                else
                {
                    String[] userCredentials = usernamePassword.split( ":" );
                    repoIds = getObservableRepos( userCredentials[0] );
                }
            }
            else
            {
                repoIds = getObservableRepos( archivaXworkUser.getGuest() );
            }
        }
        else
        {
            return false;
        }

        for ( String repoId : repoIds )
        {
            try
            {
                AuthenticationResult result = httpAuth.getAuthenticationResult( req, null );
                SecuritySession securitySession = httpAuth.getSecuritySession();

                if ( servletAuth.isAuthenticated( req, result ) &&
                    servletAuth.isAuthorized( req, securitySession, repoId, false ) )
                {
                    return true;
                }
            }
            catch ( AuthorizationException e )
            {
                
            }
            catch ( UnauthorizedException e )
            {
             
            }
        }

        throw new UnauthorizedException( "Access denied." );
    }

    private List<String> getObservableRepos( String principal )
    {
        try
        {
            return userRepositories.getObservableRepositoryIds( principal );
        }
        catch ( PrincipalNotFoundException e )
        {
            log.warn( e.getMessage(), e );
        }
        catch ( AccessDeniedException e )
        {
            log.warn( e.getMessage(), e );
        }
        catch ( ArchivaSecurityException e )
        {
            log.warn( e.getMessage(), e );
        }

        return Collections.emptyList();
    }

}
