package io.productcatalog.productcatalog.command.aggregates;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity @Table(
        name = "product"
)
@AllArgsConstructor @NoArgsConstructor @Builder
public class Product {
    @Id
    @Column(
            name = "sku",
            updatable = false
    )
    private String SKU;

    @Column(
            name = "product_name",
            nullable = false
    )
    private String name;
}
