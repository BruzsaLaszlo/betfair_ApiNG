package com.betfair.aping;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * This is a demonstration class to show a quick demo of the new Betfair API-NG.
 * When you execute the class will: <li>find a market (next horse race in the
 * UK)</li> <li>get prices and runners on this market</li> <li>place a bet on 1
 * runner</li> <li>handle the error</li>
 */
public class ApiNGDemo {

    private static Properties prop = new Properties();
    private static String applicationKey = "PWXyR1ihpVYw4dFe";
    private static String sessionToken = "Y5R4h3QEnzqvOFKth6owPT79nHJp/KhZu3aaWVLv4wo=";
    private static boolean debug;


    static {
        try (InputStream in = ApiNGDemo.class.getResourceAsStream("/apingdemo.properties")) {
            prop.load(in);
            debug = Boolean.valueOf(prop.getProperty("DEBUG"));
        } catch (IOException e) {
            System.out.println("Error loading the properties file: " + e.toString());
        }
    }

    public static void main(String[] args) {

        System.out.println("Welcome to the Betfair API NG!");

        ApiNGJRescriptDemo rescriptDemo = new ApiNGJRescriptDemo();
        rescriptDemo.start(applicationKey, sessionToken);

    }

    public static Properties getProp() {
        return prop;
    }

    public static boolean isDebug() {
        return debug;
    }
}
