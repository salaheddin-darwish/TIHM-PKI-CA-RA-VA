package org.tihm.pki.ra.utilities;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class BackgroundJobManager implements ServletContextListener {

    private ScheduledExecutorService scheduler;

    @Override
    public void contextInitialized(ServletContextEvent event) 
    {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new CAConnMangProcess (), 40, 300, TimeUnit.SECONDS);
        
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        scheduler.shutdownNow();
    }

}