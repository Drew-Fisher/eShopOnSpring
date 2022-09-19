package io.productcatalog.productcatalog.command.features.products;

import com.acme.TaskBoard.generated.types.CreateProductInput;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import io.productcatalog.productcatalog.command.aggregates.Product;
import io.productcatalog.productcatalog.command.aggregates.ProductRepository;
import lombok.Builder;
import lombok.ToString;
import lombok.Value;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class CreateProduct {

    @DgsComponent
    public class Controller{
        private final CommandGateway gateway;

        public Controller(CommandGateway gateway) {
            this.gateway = gateway;
        }

        @DgsMutation
        public CompletableFuture<String> createProduct(@InputArgument CreateProductInput input){
            Command command = Command.builder()
                    .SKU(input.getSku())
                    .Name(input.getName())
                    .build();
            return gateway.send(command);
        }
    }

    @Value @Builder
    public static class Command{
        String SKU;
        String Name;
    }

    @Value @Builder @ToString
    public static class IntegrationEvent{
        String SKU;
        String Name;
    }
    @Component
    public class Handler{
        private final ProductRepository productRepository;
        private final ApplicationEventPublisher applicationEventPublisher;

        public Handler(ProductRepository productRepository, ApplicationEventPublisher applicationEventPublisher) {
            this.productRepository = productRepository;
            this.applicationEventPublisher = applicationEventPublisher;
        }

        @CommandHandler
        public String handel(Command command){

            //create the entity with input
            Product product = Product.builder()
                    .SKU(command.SKU)
                    .name(command.Name)
                    .build();

            //save entity
            productRepository.save(product);

            //create integration event
            IntegrationEvent integrationEvent = IntegrationEvent.builder()
                    .SKU(command.getSKU())
                    .Name(command.getName())
                    .build();

            //publish integration event
            applicationEventPublisher.publishEvent(integrationEvent);

            //return SKU
            return command.SKU;
        }
    }

    @Component
    public class Consumer{
        @EventListener
        public void consume(IntegrationEvent event){
            System.out.println(event);
        }
    }
}
