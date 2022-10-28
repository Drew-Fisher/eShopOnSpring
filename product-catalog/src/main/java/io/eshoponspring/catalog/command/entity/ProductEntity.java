package io.eshoponspring.catalog.command.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(
        name = "product"
)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductEntity{
    @Id
    @Column(
            name = "item_sku",
            updatable = false
    )
    private String SKU;
}
