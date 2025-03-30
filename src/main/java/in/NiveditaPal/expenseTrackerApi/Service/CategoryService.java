package in.NiveditaPal.expenseTrackerApi.Service;


import in.NiveditaPal.expenseTrackerApi.DTO.CategoryDTO;

import java.util.List;

public interface CategoryService {
    List<CategoryDTO> getAllCategories();
    CategoryDTO saveCategory(CategoryDTO categoryDTO);
    void deleteCategory(String categoryId);
}
