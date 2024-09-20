package vn.threeluaclmsapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.threeluaclmsapi.model.Category;

public interface CategoryRepository extends JpaRepository<Category, String> {
}
