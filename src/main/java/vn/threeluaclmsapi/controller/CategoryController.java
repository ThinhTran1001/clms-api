package vn.threeluaclmsapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import vn.threeluaclmsapi.dto.response.ResponseData;
import vn.threeluaclmsapi.model.Category;
import vn.threeluaclmsapi.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public ResponseData<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        if (categories.isEmpty()) {
            return new ResponseData<>("404", "No categories found");
        } else {
            return new ResponseData<>(HttpStatus.OK.toString(), "Success", categories);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}")
    public ResponseData<Category> getCategoryById(@PathVariable String id) {
        Category category = categoryService.getCategoryById(id);
        if (category != null) {
            return new ResponseData<>("200", "Category found", category);
        } else {
            return new ResponseData<>("404", "Category not found");
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseData<Category> createCategory(@RequestBody Category category) {
        Category createdCategory = categoryService.createCategory(category);
        return new ResponseData<>("201", "Category created successfully", createdCategory);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public ResponseData<Category> updateCategory(@PathVariable String id, @RequestBody Category category) {
        Category updatedCategory = categoryService.updateCategory(id, category);
        if (updatedCategory != null) {
            return new ResponseData<>("200", "Category updated successfully", updatedCategory);
        } else {
            return new ResponseData<>("404", "Category not found");
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseData<String> deleteCategory(@PathVariable String id) {
        if (categoryService.deleteCategory(id)) {
            return new ResponseData<>("204", "Category deleted successfully");
        } else {
            return new ResponseData<>("404", "Category not found");
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{categoryId}/status")
    public ResponseData<String> updateCategoryStatus(@PathVariable String categoryId) {
        categoryService.updateCategoryStatus(categoryId);
        return new ResponseData<>("200", "Category status updated successfully");
    }

}
