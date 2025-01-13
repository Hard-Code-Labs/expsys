package ec.com.expensys.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
public class CategoryPageableDto {
    private List<PersonCategoryDto> categories;
    private int totalPages;
    private boolean hasNextPage;
}
