package com.mj;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class App 
{
    public static void main( String[] args )
    {
        String propertiesPath = Paths.get(System.getProperty("user.dir"), "aws-sdk-kinesis-producer","src", "main", "resources", "app.properties").toString();
        Properties appProps = new Properties();

        try(InputStream fileContents = Files.newInputStream(Paths.get(propertiesPath))){
            appProps.load(fileContents);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        StockTradesKinesisSDKProducer.run(appProps.getProperty("aws.kinesis.stream.name"), appProps.getProperty("aws.region"));
    }

}
