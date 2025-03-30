package in.NiveditaPal.expenseTrackerApi.IO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpenseRequest {
    @NotBlank(message = "Expense name must not be null")
    @Size(min = 3, message = "The size of expense name should be at least 3")
    private String name;
    @NotNull(message = "Expense amount must not be null")
    private String description;
    private BigDecimal amount;

    @NotBlank(message = "Category must not be null")
    private String categoryId;
    @NotNull(message = "Date must not be null")
    private Date date;
}
