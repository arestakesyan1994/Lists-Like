package am.listy.backend.web.service.impl;

import am.listy.backend.common.model.Lists;
import am.listy.backend.common.repository.ListsRepository;
import am.listy.backend.web.service.ListsPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ListsServiceImpl implements ListsPage {

    private final ListsRepository listsRepository;

    @Autowired
    public ListsServiceImpl(ListsRepository listsRepository) {
        this.listsRepository = listsRepository;
    }


    @Override
    public Page<Lists> findAllPageable(Pageable pageable) {
        return listsRepository.findAll(pageable);
    }

}
