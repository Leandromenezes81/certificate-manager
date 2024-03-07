package br.com.hgisystem.certificatemanager.core.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaginationBase<T> {
    private String totalRecords;
    private String totalPages;
    private String currentPage;
    private String pageSize;
    private boolean hasNextPage;
    private boolean hasPreviousPage;
    private T data;
}
