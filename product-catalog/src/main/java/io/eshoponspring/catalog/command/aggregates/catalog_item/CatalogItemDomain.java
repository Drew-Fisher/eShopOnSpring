package io.eshoponspring.catalog.command.aggregates.catalog_item;

import lombok.Builder;

public class CatalogItemDomain {
    private CatalogItemEntity catalogItemEntity;

    @Builder
    public CatalogItemDomain(String sku, String name, String description, String imgUrl){
        this.catalogItemEntity = CatalogItemEntity.builder()
                .SKU(sku)
                .name(name)
                .build();
    }
    public void updateInfo(String name,String description, String imgUrl){
        this.catalogItemEntity.name = name;
        this.catalogItemEntity.description = description;
        this.catalogItemEntity.imgUrl = imgUrl;
    }
}
