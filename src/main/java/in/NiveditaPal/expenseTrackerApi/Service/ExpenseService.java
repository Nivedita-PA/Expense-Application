package in.NiveditaPal.expenseTrackerApi.Service;

import in.NiveditaPal.expenseTrackerApi.DTO.ExpenseDTO;
import in.NiveditaPal.expenseTrackerApi.IO.ExpenseResponse;
import in.NiveditaPal.expenseTrackerApi.Model.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Date;
import java.util.List;

public interface ExpenseService {
    List<ExpenseDTO> getAllExpenses(Pageable pageable);
    ExpenseDTO getExpenseById(String expenseId);
    void deleteExpenseById(String expenseId);
    ExpenseDTO saveExpenseDetails(ExpenseDTO expenseDTO);
    ExpenseDTO updateExpenseDetails(String expenseId, ExpenseDTO expenseDTO);
    List<ExpenseDTO> readByCategory(String category,Pageable page);
    List<ExpenseDTO> readByName(String keyword,Pageable page);
    List<ExpenseDTO> readByDate(Date startDate, Date endDate, Pageable page);
}
