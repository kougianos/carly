package com.carly.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import javax.net.ssl.SSLContext;
import java.util.Collection;
import java.util.Collections;

@Configuration
@EnableMongoRepositories(basePackages = "com.carly.repository")
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Value("${spring.data.mongodb.uri}")
    private String connectionStringUrl;
    @Value("${spring.data.mongodb.database}")
    private String databaseName;

    /**
     * MongoClient bean that uses connection string taken from file, and SSL Context TLSv1.2 to avoid bug of
     * Java 11 with MongoDB Atlas integration (SSL Handshake exception). For more information see
     * <a href="https://developer.mongodb.com/community/forums/t/sslhandshakeexception-should-not-be-presented-in-certificate-request/12493">here</a>
     */
    @SneakyThrows
    @Override
    public MongoClient mongoClient() {
        ConnectionString connectionString = new ConnectionString(connectionStringUrl);
        SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
        sslContext.init(null, null, null);
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .applyToSslSettings(builder -> {
                    builder.enabled(true);
                    builder.context(sslContext);
                })
                .build();
        return MongoClients.create(mongoClientSettings);
    }

    @Override
    public Collection<String> getMappingBasePackages() {
        return Collections.singleton("com.carly");
    }

    @Override
    protected String getDatabaseName() {
        return databaseName;
    }

    @Primary
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), databaseName);
    }

}