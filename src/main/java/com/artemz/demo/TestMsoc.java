package com.artemz.demo;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import ru.multicabinet.bundle.BundleManagerService;

/**
 * Created with IntelliJ IDEA.
 * User: artemz
 * Date: 11/22/14
 * Time: 11:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestMsoc {
    /*private BundleContext ;*/
    private BundleManagerService bms;

    public TestMsoc(Bundle msoc){
        ServiceReference reference = msoc.getBundleContext().getServiceReference(BundleManagerService.class.getName());
        bms = (BundleManagerService)msoc.getBundleContext().getService(reference);
    }
    public BundleManagerService getBms(){
        return bms;
    }



}
