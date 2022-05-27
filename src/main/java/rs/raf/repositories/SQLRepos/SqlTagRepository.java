package rs.raf.repositories.SQLRepos;


import rs.raf.models.Tag;
import rs.raf.repositories.IRepos.TagRepository;
import rs.raf.repositories.MySqlAbstractRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SqlTagRepository extends MySqlAbstractRepository implements TagRepository {

    @Override
    public Tag addTag(Tag tag) {
        return null;
    }

    @Override
    public List<Tag> allTags() {
        return null;
    }

    @Override
    public List<Integer> tagsForPost(Integer id) {
        return null;
    }
}
