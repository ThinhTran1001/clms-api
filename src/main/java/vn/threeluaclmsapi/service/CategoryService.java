package vn.threeluaclmsapi.service;

import vn.threeluaclmsapi.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();

    Category getCategoryById(String id);

    Category createCategory(Category category);

    Category updateCategory(String id, Category category);

    boolean deleteCategory(String id);
}
