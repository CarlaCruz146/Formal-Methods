package at.ac.tuwien.inso.sqm.service;

import at.ac.tuwien.inso.sqm.entity.Tag;
import at.ac.tuwien.inso.sqm.repository.TagRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TgaService {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(TagServiceImpl.class);


    @Autowired
    private TagRepository tagRepository;

    @Override
    @Transactional(readOnly = true)
    @Deprecated
    public List<Tag> findAll() {
        LOGGER.info("finding all tags");
        return tagRepository.findAll().stream().filter(Tag::getValid)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Tag> findAllValid() {
        LOGGER.info("finding all tags");
        return tagRepository.findByValidTrue();
    }

    @Override
    public Tag findByName(String name) {
        return tagRepository.findByName(name);
    }

}
