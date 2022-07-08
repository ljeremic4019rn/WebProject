package rs.raf.services;


import rs.raf.models.Category;
import rs.raf.repositories.IRepos.CategoryRepository;

import javax.inject.Inject;
import java.util.List;

public class CategoryService {

    @Inject
    private CategoryRepository categoryRepository;

    public Category addCategory(Category category) {
        return categoryRepository.addCategory(category);
    }

    public void deleteCategory(Integer id) {
        categoryRepository.deleteCategory(id);
    }

    public Category updateCategory(Category category) {
        return categoryRepository.updateCategory(category);
    }

    public List<Category> allCategories() {
        return categoryRepository.allCategories();
    }

    public Category findCategory(Integer id) {
        return categoryRepository.findCategory(id);
    }

    public Category findCategoryByName(String name) {
        return categoryRepository.findCategoryByName(name);
    }

    public List<Category> categoriesByPage(Integer pageNum) {
        return categoryRepository.categoriesByPage(pageNum);
    }

}
