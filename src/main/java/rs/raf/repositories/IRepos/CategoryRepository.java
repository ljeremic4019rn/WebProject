package rs.raf.repositories.IRepos;


import rs.raf.models.Category;

import java.util.List;

public interface CategoryRepository {
    public boolean deleteCategory(Integer id);
    public Category addCategory(Category category);
    public Category updateCategory(Category category);
    public Category getSingleCategory(Integer id);
    public List<Category> allCategories();
}
