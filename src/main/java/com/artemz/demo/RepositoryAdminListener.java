package com.artemz.demo;

import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;

/**
 * Created with IntelliJ IDEA.
 * User: artemz
 * Date: 10/26/14
 * Time: 10:20 AM
 * To change this template use File | Settings | File Templates.
 */
public class RepositoryAdminListener implements ServiceListener {
    @Override
    public void serviceChanged(ServiceEvent serviceEvent) {
        if (serviceEvent.getType() == ServiceEvent.REGISTERED){
            System.out.println("Registered bundle: " + serviceEvent.getServiceReference().getBundle().getSymbolicName());
            String[] props = serviceEvent.getServiceReference().getPropertyKeys();
            for (int i = 0; i < props.length; i++){
                System.out.println("property: " + props[i]);
                if (props[i].equals("objectClass")){
                    for (int o = 0; o < ((String[])serviceEvent.getServiceReference().getProperty(props[i])).length; o++){
                        System.out.println(((String[])serviceEvent.getServiceReference().getProperty(props[i]))[o]);
                    }
                }
            }
        }
    }
}
