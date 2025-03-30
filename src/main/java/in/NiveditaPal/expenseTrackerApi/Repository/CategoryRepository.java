package in.NiveditaPal.expenseTrackerApi.Repository;

import in.NiveditaPal.expenseTrackerApi.Model.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity,Long> {
    List<CategoryEntity> findByUserId(Long userId);
    //finder method fetch the category by userId and categoryId
    Optional<CategoryEntity> findByUserIdAndAndCategoryId(Long userId,String categoryId);
    //checks if the category with the name already exists for the same user
    boolean existsByNameAndUserId(String name, Long userId);
    Optional<CategoryEntity> findByNameAndUserId(String name, Long userId);
}
