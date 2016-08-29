package com.messagehub.credentials;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class MessageHubCredentials {

    private String apiKey, kafkaRestUrl, user, password;
    private String[] kafkaBrokers, kafkaBrokersSasl;

    @JsonProperty("api_key")
    public String getApiKey() {
        return apiKey;
    }

    @JsonProperty("api_key")
    public void setLabel(String apiKey) {
        this.apiKey = apiKey;
    }

    @JsonProperty("kafka_rest_url")
    public String getKafkaRestUrl() {
        return kafkaRestUrl;
    }

    @JsonProperty("kafka_rest_url")
    public void setKafkaRestUrl(String kafkaRestUrl) {
        this.kafkaRestUrl = kafkaRestUrl;
    }

    @JsonProperty
    public String getUser() {
        return user;
    }

    @JsonProperty
    public void setUser(String user) {
        this.user = user;
    }

    @JsonProperty
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    @JsonProperty("kafka_brokers")
    public String[] getKafkaBrokers() {
        return kafkaBrokers;
    }

    @JsonProperty("kafka_brokers")
    public void setKafkaBrokers(String[] kafkaBrokers) {
        this.kafkaBrokers = kafkaBrokers;
    }

    @JsonProperty("kafka_brokers_sasl")
    public String[] getKafkaBrokersSasl() {
        return kafkaBrokersSasl;
    }

    @JsonProperty("kafka_brokers_sasl")
    public void setKafkaBrokersSasl(String[] kafkaBrokersSasl) {
        this.kafkaBrokersSasl = kafkaBrokersSasl;
    }
}
