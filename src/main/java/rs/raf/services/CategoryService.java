package rs.raf.services;


import rs.raf.models.Category;
import rs.raf.repositories.IRepos.CategoryRepository;
import rs.raf.repositories.SQLRepos.SqlCategoryRepository;

import javax.inject.Inject;
import java.util.List;

public class CategoryService {

    @Inject
    private CategoryRepository categoryRepository;

    public Category addCategory(Category category) {
        return categoryRepository.addCategory(category);
    }
    public boolean deleteCategory(Integer id) {
        return categoryRepository.deleteCategory(id);
    }
    public Category updateCategory(Category category) {
        return categoryRepository.updateCategory(category);
    }
    public List<Category> allCategories() {
        return categoryRepository.allCategories();
    }
    public Category getSingleCategory(Integer id) {
        return categoryRepository.getSingleCategory(id);
    }
}
