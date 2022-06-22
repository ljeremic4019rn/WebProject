package rs.raf.repositories.IRepos;


import rs.raf.models.Tag;

import java.util.List;

public interface TagRepository {
    public Tag addTag(Tag tag);
    public List<Tag> allTags();
    public List<Tag> tagsFromArticle(Integer id);
    public void addTagsArticles (Integer tagId, Integer articleId);
}
