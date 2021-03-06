package com.artemz.demo;

import org.apache.felix.bundlerepository.RepositoryAdmin;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    /*
	Does it accept service interface to load it?
    */
    public Object getService(Class<?> clazz){
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
            listServiceProperties(services[o].getPropertyKeys());

        }
        return betterServiceCollection;
    }
    /*
	Accepts array of keys returned by ServiceReference.getPropertyKeys();
	Transfors them into a flat list
    */
    private List<String> listServiceProperties(String[] keys){
        List<String> props = new ArrayList<String>();
        for (int i = 0; i < keys.length; i++){
		props.add(keys[i]);
        }
        return props;
    }
}
