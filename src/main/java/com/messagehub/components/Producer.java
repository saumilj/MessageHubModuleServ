package com.messagehub.components;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import com.messagehub.work.MessageList;


import org.apache.log4j.Level;
import org.apache.log4j.Logger;


public class Producer{
	
	public int producedMessages = 0;
	 public String producedMessage;
	 public boolean messageProduced;
	 private final Logger logger = Logger.getLogger(KafkaServlet.class);
	    
	 public Producer(){}
	 
	 /**
	     * Produce a message to a <code>topic</code>
	     *
	     * @param topic
	     */
	 
	 public void produce(String topic, String querysb, KafkaProducer<byte[], byte[]> kafkaProducer) {
	        logger.log(Level.WARN, "Producer is starting.");

	        String fieldName = "records";
	        // Push a message into the list to be sent.
	        MessageList list = new MessageList();
	        //producedMessage = "This is a test message, msgId=" + producedMessages;
	        list.push(querysb.toString());

	        try {
	            // Create a producer record which will be sent
	            // to the Message Hub service, providing the topic
	            // name, field name and message. The field name and
	            // message are converted to UTF-8.
	            ProducerRecord<byte[], byte[]> record = new ProducerRecord<byte[], byte[]>(topic,
	                    fieldName.getBytes("UTF-8"), list.build().getBytes("UTF-8"));

	            // Synchronously wait for a response from Message Hub / Kafka.
	            RecordMetadata m = kafkaProducer.send(record).get();
	            producedMessages++;

	            logger.log(Level.WARN, "Message produced, offset: " + m.offset());

	            Thread.sleep(1000);
	        } catch (final Exception e) {
	            e.printStackTrace();
	            // Consumer will hang forever, so exit program.
	            System.exit(-1);
	        }
	        messageProduced = true;
	        logger.log(Level.WARN, "Producer is shutting down.");
	    }

}