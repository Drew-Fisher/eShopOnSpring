package io.eshoponspring.catalog.command.features.catalog_item.aggregate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data @AllArgsConstructor @Builder
public class CatalogItemInfo {
    public String name;
    public String description;
    public String imgUrl;
}
