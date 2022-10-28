package io.eshoponspring.catalog.command.aggregates;

import io.eshoponspring.catalog.command.aggregates.catalog_item.CatalogItemDomain;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CatalogItemRepository extends PagingAndSortingRepository<CatalogItemDomain, String> {
}
