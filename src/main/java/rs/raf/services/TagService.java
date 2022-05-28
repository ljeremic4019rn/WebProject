package rs.raf.services;


import rs.raf.models.Tag;
import rs.raf.repositories.IRepos.TagRepository;

import javax.inject.Inject;
import java.util.List;

public class TagService {

    @Inject
    private TagRepository tagRepository;

    public List<Tag> allTags() {
        return tagRepository.allTags();
    }

    public List<Integer> tagsFromArticle(Integer id) {
        return tagRepository.tagsFromArticle(id);
    }

}
