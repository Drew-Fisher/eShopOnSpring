package io.eshoponspring.catalog.command.aggregates.catalog_item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(
        name = "catalog_item"
)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CatalogItem {
    @Id
    @Column(
            name = "item_sku",
            updatable = false
    )
    private UUID itemId;

    @Column(
            name = "item_name",
            nullable = false
    )
    public String name;

    @Column(
            name = "item_description"
    )
    public String description;

    @Column(
            name = "img_url"
    )
    public String imgUrl;
}
