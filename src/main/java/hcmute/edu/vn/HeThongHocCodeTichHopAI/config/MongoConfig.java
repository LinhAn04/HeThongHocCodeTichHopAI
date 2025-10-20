package hcmute.edu.vn.HeThongHocCodeTichHopAI.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "hcmute.edu.vn.HeThongHocCodeTichHopAI.repository")
public class MongoConfig {
    @Value("${MONGODB_URI}")
    private String mongoUri;

    @Value("${MONGODB_DATABASE}")
    private String mongoDatabase;

    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create(mongoUri);
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), mongoDatabase);
    }
}
