package in.NiveditaPal.expenseTrackerApi.Repository;

import in.NiveditaPal.expenseTrackerApi.Model.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense,Long> {
      Page<Expense> findByUserIdAndCategory(Long id,String category, Pageable page);
      Page<Expense> findByUserIdAndCategoryId(Long id,Long categoryId, Pageable page);
      Page<Expense> findByUserIdAndNameContaining(Long id, String name, Pageable page);
      Page<Expense> findByUserIdAndDateBetween(Long id,Date startDate, Date endDate, Pageable page);
      Page<Expense> findByUserId(Long userid,Pageable page);
      Optional<Expense> findByUserIdAndExpenseId(Long userId, String expenseId);

}
