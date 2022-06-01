package com.mj;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.kinesis.KinesisClient;
import software.amazon.awssdk.services.kinesis.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;



public final class StockTradesKinesisSDKProducer {

    private StockTradesKinesisSDKProducer() {

    }
    static final Logger logger = LogManager.getLogger(StockTradesKinesisSDKProducer.class);
    static final ObjectMapper objMapper = new ObjectMapper();

    /**
     * Checks if the stream exists and is active
     *
     * @param kinesisClient Amazon Kinesis client instance
     * @param streamName Name of stream
     */
    private static void validateStream(KinesisClient kinesisClient, String streamName) {
        try {
            DescribeStreamRequest describeStreamRequest =  DescribeStreamRequest.builder().streamName(streamName).build();
            DescribeStreamResponse describeStreamResponse = kinesisClient.describeStream(describeStreamRequest);
            if(!describeStreamResponse.streamDescription().streamStatus().toString().equals("ACTIVE")) {
                logger.warn(String.format("Stream %s is not active. Please wait a few moments and try again.", streamName));
                System.exit(1);
            }
        }catch (Exception e) {
            logger.warn(String.format("Error found while describing the stream %s", streamName));
            logger.warn(e.toString());
            System.exit(1);
        }
    }

    public static void run(String streamName, String region){
        logger.info("Starting PutRecord Producer");

        KinesisClient client = KinesisClient.builder().region(Region.of(region)).build();
        validateStream(client, streamName);

        OrderBundle<StockOrder> singleBundle = new StockOrderGenerator().generateOrderBundle();

        try {
            PutRecordRequest putRequest = PutRecordRequest.builder().streamName(streamName).partitionKey(singleBundle.bundleId()).data(SdkBytes.fromByteArray(objMapper.writeValueAsBytes(singleBundle))).build();
            PutRecordResponse response = client.putRecord(putRequest);
            System.out.println(String.format("Produced Record %s to Shard %s", response.sequenceNumber(), response.shardId()));
        } catch (JsonProcessingException e) {
            logger.error(String.format("Failed to serialize %s", singleBundle), e);
        } catch (KinesisException e) {
            logger.error(String.format("Failed to produce %s", singleBundle), e);
        }

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("Shutting down program");
            client.close();
        }, "producer-shutdown"));

    }}