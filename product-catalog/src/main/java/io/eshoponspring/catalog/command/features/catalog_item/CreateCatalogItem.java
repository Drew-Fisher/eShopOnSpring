package io.eshoponspring.catalog.command.features.catalog_item;

import com.acme.TaskBoard.generated.types.CreateProductInput;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import io.eshoponspring.catalog.command.aggregates.catalog_item.CatalogItemDomain;
import io.eshoponspring.catalog.command.aggregates.CatalogItemRepository;
import lombok.Builder;
import lombok.ToString;
import lombok.Value;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

/**
 * parent class of the CreateCatalogItem Feature
 *
 * contains all classes that are involved in creating a CatalogItem
 */
@Component
public class CreateCatalogItem {
    /**
     * Controller class
     */
    @DgsComponent
    public class Controller{
        private final CommandGateway gateway;

        public Controller(CommandGateway gateway) {
            this.gateway = gateway;
        }

        /**
         * end point for createCatalogItem mutation
         *
         * @param input
         * @return
         */
        @DgsMutation(field = "createCatalogItem")
        public CompletableFuture<String> createCatalogItem(@InputArgument CreateProductInput input){

            //build Command POJO from input
            Command command = Command.builder()
                    .sku(input.getSku())
                    .name(input.getName())
                    .build();

            //publish command to axios command gateway
            return gateway.send(command);
        }
    }

    /**
     * Command POJO
     */
    @Value @Builder
    public static class Command{
        String sku;
        String name;
    }

    /**
     * IntegrationEvent POJO
     */
    @Value @Builder @ToString
    public static class IntegrationEvent{
        String sku;
        String name;
        String description;
    }

    /**
     * Handler class
     */
    @Component
    public class Handler{
        private final CatalogItemRepository catalogItemRepository;
        private final ApplicationEventPublisher applicationEventPublisher;

        public Handler(CatalogItemRepository catalogItemRepository, ApplicationEventPublisher applicationEventPublisher) {
            this.catalogItemRepository = catalogItemRepository;
            this.applicationEventPublisher = applicationEventPublisher;
        }

        /**
         * Handles Commands from axon CommandGateway
         *
         * @param command
         * @return
         */
        @CommandHandler
        public String handel(Command command){

            //create the entity with input
            CatalogItemDomain catalogItemDomain = CatalogItemDomain.builder()
                    .sku(command.sku)
                    .name(command.name)
                    .build();

            //save entity
            catalogItemRepository.save(catalogItemDomain);

            //create integration event
            IntegrationEvent integrationEvent = IntegrationEvent.builder()
                    .sku(command.getSku())
                    .name(command.getName())
                    .build();

            //publish integration event
            applicationEventPublisher.publishEvent(integrationEvent);

            //return SKU
            return command.sku;
        }
    }

    /**
     * dummy class for testing
     */
    @Component
    public class Consumer{
        @EventListener
        public void consume(IntegrationEvent event){
            System.out.println(event);
        }
    }
}
