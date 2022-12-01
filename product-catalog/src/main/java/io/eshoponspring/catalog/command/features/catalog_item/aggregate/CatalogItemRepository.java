package io.eshoponspring.catalog.command.features.catalog_item.aggregate;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface CatalogItemRepository extends PagingAndSortingRepository<CatalogItem, String> {
}
