package gatewayservice.Configuration;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfiguration {

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/Questions/**")
                        .uri("lb://QUESTIONS-SERVICE/")
                        .id("questionsModule"))


                .route(r -> r.path("/Solutions/**")
                        .uri("lb://SOLUTIONS-SERVICE/")
                        .id("solutionsModule"))
                .build();
    }

}
