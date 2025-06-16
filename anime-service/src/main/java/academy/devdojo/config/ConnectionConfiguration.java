package academy.devdojo.config;

import external.dependency.Connection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConnectionConfiguration {

    @Bean(name = "connectionMySql")
    public Connection connectionMySql() {
        return new Connection("localhost", "devdojoMySQL", "goku");
    }

    @Bean(name = "connectionMongoDB")
//    @Primary
    public Connection connectionMongo() {
        return new Connection("localhost", "devdojoMongo", "goku");
    }
}