package in.NiveditaPal.expenseTrackerApi.Controller;

import in.NiveditaPal.expenseTrackerApi.DTO.CategoryDTO;
import in.NiveditaPal.expenseTrackerApi.DTO.ExpenseDTO;
import in.NiveditaPal.expenseTrackerApi.IO.CategoryResponse;
import in.NiveditaPal.expenseTrackerApi.IO.ExpenseRequest;
import in.NiveditaPal.expenseTrackerApi.IO.ExpenseResponse;
import in.NiveditaPal.expenseTrackerApi.Model.Expense;
import in.NiveditaPal.expenseTrackerApi.Service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @GetMapping("/expenses")
    public List<ExpenseResponse> getAllExpenses(Pageable pageable){
        List<ExpenseDTO> list = expenseService.getAllExpenses(pageable);
        return list.stream().map(expenseDTO -> mapToResponse(expenseDTO)).collect(Collectors.toList());
    }

    @GetMapping("/expense/{expenseId}")
    public ExpenseResponse getExpenseById(@PathVariable String expenseId){
        ExpenseDTO expenseDTO = expenseService.getExpenseById(expenseId);
        return mapToResponse(expenseDTO);
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping("/expenses")
    public void deleteExpenseById(@RequestParam String expenseId){
          expenseService.deleteExpenseById(expenseId);
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("/expenses")
    public ExpenseResponse saveExpenseDetails(@Valid @RequestBody ExpenseRequest expenseRequest){
        ExpenseDTO expenseDTO = mapToDTO(expenseRequest);
        expenseDTO = expenseService.saveExpenseDetails(expenseDTO);
        return mapToResponse(expenseDTO);
    }

    private ExpenseResponse mapToResponse(ExpenseDTO expenseDTO) {
//        return ExpenseResponse.builder()
//                .expenseId(expenseDTO.getExpenseId())
//                .name(expenseDTO.getName())
//                .description(expenseDTO.getDescription())
//                .amount(expenseDTO.getAmount())
//                .date(expenseDTO.getDate())
//                .category(mapToCategoryResponse(expenseDTO.getCategoryDTO()))
//                .createdAt(expenseDTO.getCreatedAt())
//                .updatedAt(expenseDTO.getUpdatedAt())
//                .build();
        ExpenseResponse expenseResponse = new ExpenseResponse();
        CategoryResponse categoryResponse = new CategoryResponse();
        BeanUtils.copyProperties(expenseDTO.getCategoryDTO(),categoryResponse);
        BeanUtils.copyProperties(expenseDTO, expenseResponse);
        expenseResponse.setCategory(categoryResponse);
        return expenseResponse;
    }

    private CategoryResponse mapToCategoryResponse(CategoryDTO categoryDTO) {
        return CategoryResponse.builder()
                .categoryId(categoryDTO.getCategoryId())
                .name(categoryDTO.getName())
                .build();
    }

    private ExpenseDTO mapToDTO(ExpenseRequest expenseRequest) {
        return ExpenseDTO.builder()
                .name(expenseRequest.getName())
                .description(expenseRequest.getDescription())
                .amount(expenseRequest.getAmount())
                .date(expenseRequest.getDate())
                .categoryId(expenseRequest.getCategoryId())
                .build();
    }

    @PutMapping("/expenses/{expenseId}")
    public ExpenseResponse updateExpenseDetails(@RequestBody ExpenseRequest expenseRequest, @PathVariable String expenseId){
        ExpenseDTO updatedExpense = mapToDTO(expenseRequest);
        updatedExpense = expenseService.updateExpenseDetails(expenseId, updatedExpense);
        return mapToResponse(updatedExpense);
    }
    @GetMapping("/expenses/category")
    public List<ExpenseResponse> getExpenseByCategory(@RequestParam String category,Pageable page){
        List<ExpenseDTO> list = expenseService.readByCategory(category,page);
        return list.stream().map(expenseDTO -> mapToResponse(expenseDTO)).collect(Collectors.toList());
    }
    @GetMapping("/expenses/keyword")
    public List<ExpenseResponse> getExpensesByName(@RequestParam String keyword,Pageable page){
        List<ExpenseDTO> list = expenseService.readByName(keyword,page);
        return list.stream().map(expenseDTO -> mapToResponse(expenseDTO)).collect(Collectors.toList());
    }

    @GetMapping("/expenses/date")
    public List<ExpenseResponse> getExpenseByDates(@RequestParam(required = false) Date startDate,
                                           @RequestParam (required = false) Date endDate,
                                           Pageable page){
        List<ExpenseDTO> list = expenseService.readByDate(startDate,endDate,page);
        return list.stream().map(expenseDTO -> mapToResponse(expenseDTO)).collect(Collectors.toList());
    }
}
