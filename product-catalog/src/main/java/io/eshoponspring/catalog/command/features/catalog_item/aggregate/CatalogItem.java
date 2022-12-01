package io.eshoponspring.catalog.command.features.catalog_item.aggregate;

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
            name = "product_sku",
            updatable =false,
            nullable = false
    )
    private String sku;

    @Column(
            name = "state",
            nullable = false
    )
    private String state;

    @Column(
            name = "is_visible",
            nullable = false
    )
    private boolean isVisible;

    private CatalogItemInfo itemInfo;
}
