package com.artemz.demo;

import org.apache.felix.bundlerepository.RepositoryAdmin;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Created with IntelliJ IDEA.
 * User: artemz
 * Date: 10/25/14
 * Time: 4:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class RepositoryAdminConsumer implements BundleActivator{
    private BundleContext context = null;
    private ServiceTracker repoAdminTracker = null;
    private RepositoryAdmin admin = null;
    @Override
    public void start(BundleContext bundleContext){
        /*synchronized (this){
            bundleContext.addServiceListener(new RepositoryAdminListener());
        }*/
        context = bundleContext;
    }
    public RepositoryAdmin getRepositoryAdmin(){
        admin = (RepositoryAdmin) repoAdminTracker.getService();
        return admin;
    }
    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        context = null;
    }
    public Bundle[] getBundles(){
        if (context != null){
            return context.getBundles();
        }
        return null;
    }
    public BundleContext getContext(){
        return context;
    }
}
