package rs.raf.repositories.SQLRepos;


import rs.raf.models.Category;
import rs.raf.repositories.IRepos.CategoryRepository;
import rs.raf.repositories.MySqlAbstractRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SqlCategoryRepository extends MySqlAbstractRepository implements CategoryRepository {


    @Override
    public void deleteCategory(Integer id) {

    }

    @Override
    public Category addCategory(Category category) {
        return null;
    }

    @Override
    public Category updateCategory(Category category) {
        return null;
    }

    @Override
    public Category findCategory(Integer id) {
        return null;
    }

    @Override
    public List<Category> allCategories() {
        return null;
    }
}
