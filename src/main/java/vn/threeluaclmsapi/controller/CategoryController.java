package vn.threeluaclmsapi.controller;

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

    @GetMapping
    public ResponseData<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        if (categories.isEmpty()) {
            return new ResponseData<>("404", "No categories found");
        } else {
            return new ResponseData<>("200", "Success", categories);
        }
    }

    @GetMapping("/{id}")
    public ResponseData<Category> getCategoryById(@PathVariable String id) {
        Category category = categoryService.getCategoryById(id);
        if (category != null) {
            return new ResponseData<>("200", "Category found", category);
        } else {
            return new ResponseData<>("404", "Category not found");
        }
    }

    @PostMapping
    public ResponseData<Category> createCategory(@RequestBody Category category) {
        Category createdCategory = categoryService.createCategory(category);
        return new ResponseData<>("201", "Category created successfully", createdCategory);
    }

    @PutMapping("/{id}")
    public ResponseData<Category> updateCategory(@PathVariable String id, @RequestBody Category category) {
        Category updatedCategory = categoryService.updateCategory(id, category);
        if (updatedCategory != null) {
            return new ResponseData<>("200", "Category updated successfully", updatedCategory);
        } else {
            return new ResponseData<>("404", "Category not found");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseData<String> deleteCategory(@PathVariable String id) {
        if (categoryService.deleteCategory(id)) {
            return new ResponseData<>("204", "Category deleted successfully");
        } else {
            return new ResponseData<>("404", "Category not found");
        }
    }

    @PutMapping("/{categoryId}/status")
    public ResponseData<String> updateCategoryStatus(@PathVariable String categoryId) {
        categoryService.updateCategoryStatus(categoryId);
        return new ResponseData<>("200", "Category status updated successfully");
    }
}
