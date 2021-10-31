package org.tihm.pki.ra.utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.LogManager;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @author Salaheddin Darwish @ 30/01/2017  , Salaheddin.Darwish@gmail.com
 *
 */

@WebListener
    public class InitializeListner implements ServletContextListener {

        @Override
        public final void contextInitialized(final ServletContextEvent sce) {
        	
        	try {
        		
				   		LogManager.getLogManager().readConfiguration(new FileInputStream("/home/salah/workspace_pki/tihm-pki-ra/WebContent/WEB-INF/lib/logging.properties"));
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

        }

        @Override
        public final void contextDestroyed(final ServletContextEvent sce) {

        }
    }