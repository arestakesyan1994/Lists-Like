package am.listy.backend.web.service;

import am.listy.backend.common.model.Lists;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ListsPage {
    Page<Lists> findAllPageable(Pageable pageable);
}
