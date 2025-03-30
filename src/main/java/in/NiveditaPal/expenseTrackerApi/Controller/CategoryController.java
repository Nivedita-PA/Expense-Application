package in.NiveditaPal.expenseTrackerApi.Controller;

import in.NiveditaPal.expenseTrackerApi.DTO.CategoryDTO;
import in.NiveditaPal.expenseTrackerApi.IO.CategoryRequest;
import in.NiveditaPal.expenseTrackerApi.IO.CategoryResponse;
import in.NiveditaPal.expenseTrackerApi.Service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping
    public CategoryResponse createCategory(@RequestBody CategoryRequest categoryRequest){
        CategoryDTO categoryDTO = mapToDTO(categoryRequest);
         categoryDTO = categoryService.saveCategory(categoryDTO);
         return mapToResponse(categoryDTO);
    }
    @GetMapping
    public List<CategoryResponse> readCategories(){
        List<CategoryDTO> list = categoryService.getAllCategories();
        return list.stream().map(categoryDTO -> mapToResponse(categoryDTO)).collect(Collectors.toList());
    }
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping("/{categoryId}")
    public void deleteCategory(@PathVariable String categoryId){
        categoryService.deleteCategory(categoryId);
    }
    private CategoryResponse mapToResponse(CategoryDTO categoryDTO) {
        return CategoryResponse.builder()
                .categoryId(categoryDTO.getCategoryId())
                .name(categoryDTO.getName())
                .description(categoryDTO.getDescription())
                .icon(categoryDTO.getIcon())
                .createdAt(categoryDTO.getCreatedAt())
                .updatedAt(categoryDTO.getUpdatedAt())
                .build();
    }

    private CategoryDTO mapToDTO(CategoryRequest categoryRequest) {
        return CategoryDTO.builder()
                .name(categoryRequest.getName())
                .description(categoryRequest.getDescription())
                .icon(categoryRequest.getIcon())
                .build();
    }
}
