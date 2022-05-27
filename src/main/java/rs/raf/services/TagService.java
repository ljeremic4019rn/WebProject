package rs.raf.services;


import rs.raf.models.Tag;
import rs.raf.repositories.IRepos.TagRepository;
import rs.raf.repositories.SQLRepos.SqlTagRepository;

import javax.inject.Inject;
import java.util.List;

public class TagService {

    @Inject
    private TagRepository tagRepository;

    public List<Tag> allTags() {
        return tagRepository.allTags();
    }

    public List<Integer> tagsForPost(Integer id) {
        return tagRepository.tagsForPost(id);
    }

}
