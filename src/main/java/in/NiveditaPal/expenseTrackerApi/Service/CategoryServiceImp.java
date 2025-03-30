package in.NiveditaPal.expenseTrackerApi.Service;

import in.NiveditaPal.expenseTrackerApi.DTO.CategoryDTO;
import in.NiveditaPal.expenseTrackerApi.DTO.UserDTO;
import in.NiveditaPal.expenseTrackerApi.Exceptions.ItemAlreadyExitsException;
import in.NiveditaPal.expenseTrackerApi.Exceptions.ResourceNotFoundException;
import in.NiveditaPal.expenseTrackerApi.Model.CategoryEntity;
import in.NiveditaPal.expenseTrackerApi.Model.User;
import in.NiveditaPal.expenseTrackerApi.Repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImp implements CategoryService{
    private final CategoryRepository categoryRepository;
    private final UserService userService;
    @Override
    public List<CategoryDTO> getAllCategories() {
        List<CategoryEntity> list = categoryRepository.findByUserId(userService.getLoggedInUser().getId());
        return list.stream()
                .map(categoryEntity -> mapToDTO(categoryEntity))
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDTO saveCategory(CategoryDTO categoryDTO) {
        boolean optionalCategoryEntity = categoryRepository.existsByNameAndUserId(categoryDTO.getName(), userService.getLoggedInUser().getId());
        if(optionalCategoryEntity){
            throw new ItemAlreadyExitsException("Category already present for the name "+ categoryDTO.getName());
        }
        CategoryEntity categoryEntity = mapToEntity(categoryDTO);
        categoryEntity = categoryRepository.save(categoryEntity);
        return mapToDTO(categoryEntity);
    }

    @Override
    public void deleteCategory(String categoryId) {
        Optional<CategoryEntity> category = categoryRepository.findByUserIdAndAndCategoryId(userService.getLoggedInUser().getId(),categoryId);
        if(!category.isPresent()) throw new ResourceNotFoundException("Category not present with the id "+categoryId);
        categoryRepository.delete(category.get());
    }

    private CategoryEntity mapToEntity(CategoryDTO categoryDTO) {
        return  CategoryEntity.builder()
                .name(categoryDTO.getName())
                .description(categoryDTO.getDescription())
                .icon(categoryDTO.getIcon())
                .categoryId(UUID.randomUUID().toString())
                .user(userService.getLoggedInUser())
                .build();
    }

    private CategoryDTO mapToDTO(CategoryEntity categoryEntity) {
        return CategoryDTO.builder()
                .categoryId(categoryEntity.getCategoryId())
                .name(categoryEntity.getName())
                .description(categoryEntity.getDescription())
                .icon(categoryEntity.getIcon())
                .createdAt(categoryEntity.getCreatedAt())
                .updatedAt(categoryEntity.getUpdatedAt())
                .userDTO(mapToUserDTO(categoryEntity.getUser()))
                .build();
    }

    private UserDTO mapToUserDTO(User user) {
        return UserDTO.builder()
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }
}
