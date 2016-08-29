//package com.messagehub.components;
//
//import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.apache.kafka.clients.consumer.KafkaConsumer;
//import java.util.ArrayList;
//import java.util.Collection;
//
//
//import org.apache.log4j.Level;
//import org.apache.log4j.Logger;
//
//import java.util.Iterator;
//import java.util.Properties;
//import java.io.ByteArrayInputStream;
//import java.io.File;
//import java.nio.charset.Charset;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.FileInputStream;
//
//
//import org.apache.kafka.common.TopicPartition;
//import org.apache.kafka.common.serialization.ByteArrayDeserializer;
//
//
//
///**
//     * Kafka consumer runnable which can be used to create and run consumer as a
//     * separate thread.
//     *
//     * @author Admin
//     *
//     */
//    public class ConsumerRunnable implements Runnable {
//        private KafkaConsumer<byte[], byte[]> kafkaConsumer;
//        private ArrayList<String> topicList;
//        private boolean closing;
//        public ArrayList<String> consumedMessages;
//        public String currentConsumedMessage;
//        private final Logger logger = Logger.getLogger(KafkaServlet.class);
//        private boolean canProduce = false;
//        private final String userDir = System.getProperty("user.dir");
//        private final String resourceDir = userDir + File.separator + "apps" + File.separator + "MessageHubLibertyApp.war"
//                + File.separator + "resources";
//        
//
//        ConsumerRunnable(String broker, String apiKey, String topic) {
//            consumedMessages = new ArrayList<String>();
//            closing = false;
//            topicList = new ArrayList<String>();
//
//            // Provide configuration and deserialisers
//            // for the key and value fields received.
//            kafkaConsumer = new KafkaConsumer<byte[], byte[]>(getClientConfiguration(broker, apiKey, false),
//                    new ByteArrayDeserializer(), new ByteArrayDeserializer());
//
//            topicList.add(topic);
//            kafkaConsumer.subscribe(topicList, new ConsumerRebalanceListener() {
//
//                @Override
//                public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
//                }
//
//                @Override
//                public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
//                    try {
//                        logger.log(Level.WARN, "Partitions assigned, consumer seeking to end.");
//
//                        for (TopicPartition partition : partitions) {
//                            long position = kafkaConsumer.position(partition);
//                            logger.log(Level.WARN, "current Position: " + position);
//
//                            logger.log(Level.WARN, "Seeking to end...");
//                            kafkaConsumer.seekToEnd(partition);
//                            logger.log(Level.WARN,
//                                    "Seek from the current position: " + kafkaConsumer.position(partition));
//                            kafkaConsumer.seek(partition, position);
//                        }
//                        logger.log(Level.WARN, "Producer can now begin producing messages.");
//                    } catch (final Exception e) {
//                        e.printStackTrace();
//                    }
//
//                    canProduce = true;
//                }
//            });
//        }
//
//        @Override
//        public void run() {
//            logger.log(Level.WARN, "Consumer is starting.");
//
//            while (!closing) {
//                try {
//                	currentConsumedMessage = "consumer listening for query ..";
//                    // Poll on the Kafka consumer every second.
//                    Iterator<ConsumerRecord<byte[], byte[]>> it = kafkaConsumer.poll(1000).iterator();
//
//                    // Iterate through all the messages received and print their
//                    // content.
//                    // After a predefined number of messages has been received,
//                    // the
//                    // client will exit.
//                    while (it.hasNext()) {
//                        ConsumerRecord<byte[], byte[]> record = it.next();
//                        currentConsumedMessage = new String(record.value(), Charset.forName("UTF-8"));
//
//                        currentConsumedMessage = currentConsumedMessage;
//                        consumedMessages.add(currentConsumedMessage);
//                    }
//
//                    kafkaConsumer.commitSync();
//
//                    Thread.sleep(1000);
//                } catch (final InterruptedException e) {
//                    logger.log(Level.ERROR, "Producer/Consumer loop has been unexpectedly interrupted");
//                    shutdown();
//                } catch (final Exception e) {
//                    logger.log(Level.ERROR, "Consumer has failed with exception: " + e);
//                    shutdown();
//                }
//            }
//
//            logger.log(Level.WARN, "Consumer is shutting down.");
//            kafkaConsumer.close();
//        }
//
//        public void shutdown() {
//            closing = true;
//        }
//        
//        public final Properties getClientConfiguration(String broker, String apiKey, boolean isProducer) {
//            Properties props = new Properties();
//            InputStream propsStream;
//            String fileName;
//
//            if (isProducer) {
//                fileName = "producer.properties";
//            } else {
//                fileName = "consumer.properties";
//            }
//
//            try {
//                propsStream = new FileInputStream(resourceDir + File.separator + fileName);
//                props.load(propsStream);
//                propsStream.close();
//            } catch (IOException e) {
//                return props;
//            }
//
//            props.put("bootstrap.servers", broker);
//
//            props.put("ssl.truststore.location", userDir + "/../../../../.java/jre/lib/security/cacerts");
//
//            return props;
//        }
//
//    }