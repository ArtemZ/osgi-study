package com.artemz.demo;

import org.apache.felix.bundlerepository.RepositoryAdmin;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: artemz
 * Date: 10/28/14
 * Time: 9:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class BundleManager {
    /*private BundleContext bundleContext;*/
    private Bundle managedBundle;
    /*public BundleManager(BundleContext ctx){
        bundleContext = ctx;
    }*/
    public BundleManager(Bundle bundle){
        managedBundle = bundle;
    }
    public Bundle getChildBundle(long childId){
        if (managedBundle != null){
            return managedBundle.getBundleContext().getBundle(childId);
        }
        return null;
    }
    public Bundle getChildBundle(String location){
        if (managedBundle != null){
            return managedBundle.getBundleContext().getBundle(location);
        }
        return null;
    }
    public Object getService(String clazz){
        ServiceReference reference = managedBundle.getBundleContext().getServiceReference(clazz);
        if (reference != null){
            return managedBundle.getBundleContext().getService(reference);
        }
        return null;
    }
    public Map<String, String> listAvailableServices(){
        ServiceReference<?>[] services = managedBundle.getRegisteredServices();
        Map<String,String> betterServiceCollection = new HashMap<String, String>();
            /*
                Printing out all available services in this bundle
             */
        for (int o = 0; o < services.length; o++){

            System.out.println(services[o].getProperty("objectClass"));
        }
        return betterServiceCollection;
    }
}
