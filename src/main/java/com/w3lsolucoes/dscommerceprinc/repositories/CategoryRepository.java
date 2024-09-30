package com.w3lsolucoes.dscommerceprinc.repositories;

import com.w3lsolucoes.dscommerceprinc.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT DISTINCT obj FROM Category obj INNER JOIN obj.products WHERE obj.products IS NOT EMPTY")
    List<Category> findCategoriesHavingProducts();

}
