package vn.threeluaclmsapi.service.impl;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import vn.threeluaclmsapi.model.Category;
import vn.threeluaclmsapi.repository.CategoryRepository;
import vn.threeluaclmsapi.service.CategoryService;
import vn.threeluaclmsapi.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        try {
            return categoryRepository.findAll();
        } catch (Exception e) {
            log.error("Error while fetching all categories: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to fetch categories", e);
        }
    }

    @Override
    public Category getCategoryById(String id) {
        try {
            Optional<Category> category = categoryRepository.findById(id);
            return category.orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        } catch (Exception e) {
            log.error("Error while fetching category by ID: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to fetch category", e);
        }
    }

    @Override
    public Category createCategory(Category category) {
        try {
            category.setStatus(true);
            return categoryRepository.save(category);
        } catch (Exception e) {
            log.error("Error while creating category: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to create category", e);
        }
    }

    @Override
    public Category updateCategory(String id, Category category) {
        try {
            if (categoryRepository.existsById(id)) {
                category.setId(id);
                return categoryRepository.save(category);
            } else {
                throw new ResourceNotFoundException("Category not found");
            }
        } catch (Exception e) {
            log.error("Error while updating category: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to update category", e);
        }
    }

    @Override
    public boolean deleteCategory(String id) {
        try {
            if (categoryRepository.existsById(id)) {
                categoryRepository.deleteById(id);
                return true;
            } else {
                throw new ResourceNotFoundException("Category not found");
            }
        } catch (Exception e) {
            log.error("Error while deleting category: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to delete category", e);
        }
    }

    @Override
    public void updateCategoryStatus(String categoryId) {
        try {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

            category.setStatus(!category.getStatus());
            categoryRepository.save(category);
        } catch (Exception e) {
            log.error("Error while updating category status: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to update category status", e);
        }
    }
}
