package com.artemz.demo;


import org.osgi.framework.BundleException;
import org.osgi.framework.launch.Framework;

/**
 * Created with IntelliJ IDEA.
 * User: artemz
 * Date: 10/23/14
 * Time: 4:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class Main {
    private static Framework framework;

    public static void main(String[] args){
        /*try {
            framework = Launcher.launch();
        } catch (BundleException e){
            System.out.println("BundleException : " + e.getMessage());
        }*/
        System.out.println("OK");
        //System.exit(0);
    }
}
