package com.sda.jdbc;

import lombok.extern.java.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;

@Log
public class JdbcConfig {
    //pod kluczem znajduje się wartość
private Properties jdbcProperties;

public JdbcConfig(){
    this("/jdbc.properties");
}


    public JdbcConfig(String filName) {
        jdbcProperties = loadProperties(filName);
    }
    private Properties loadProperties(String fileName){
        Properties properties = new Properties();
        try{ //ładujemy z resources
            InputStream stream = JdbcConfig.class.getResourceAsStream(fileName);

            if(stream == null){ // jeśli coś pójdzie nietak
                throw new IOException("File read error!");
            }
            properties.load(stream);
        }catch (IOException ioe){
            log.log(Level.SEVERE, "Error", ioe);
        }
        return properties;
    }
    public  String getProperty(String name){
    return jdbcProperties.getProperty(name);
    }
}
