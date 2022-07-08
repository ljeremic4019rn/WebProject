package rs.raf.repositories.IRepos;


import rs.raf.models.Article;
import rs.raf.models.Category;

import java.util.List;

public interface CategoryRepository {
    public void deleteCategory(Integer id);
    public Category addCategory(Category category);
    public Category updateCategory(Category category);
    public Category findCategory(Integer id);
    public Category findCategoryByName(String name);
    public List<Category> allCategories();
    public List<Category> categoriesByPage(Integer pageNum);
}
