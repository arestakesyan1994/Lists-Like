package am.listy.backend.web.controller;

import am.listy.backend.common.model.Lists;
import am.listy.backend.common.repository.CategoryRepository;
import am.listy.backend.web.security.CurrentUser;
import am.listy.backend.web.service.ListsPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class PageController {

    private static final int INITIAL_PAGE = 0;
    private static final int INITIAL_PAGE_SIZE = 5;

    @Autowired
    private CategoryRepository categoryRepository;

    private final ListsPage listsPage;

    public PageController(ListsPage listsPage) {
        this.listsPage = listsPage;
    }

    @GetMapping("/")
    public String indexPage(ModelMap map,
                            @AuthenticationPrincipal CurrentUser currentUser,
                            @RequestParam("pageSize") Optional<Integer> pageSize,
                            @RequestParam("page") Optional<Integer> page) {
        map.addAttribute("allCategorys", categoryRepository.findAll());
        map.addAttribute("isLoggedIn", currentUser != null);
        int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
        int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;
        Page<Lists> lists = listsPage.findAllPageable(PageRequest.of(evalPage, evalPageSize));
        map.addAttribute("lists", lists);
        map.addAttribute("selectedPageSize", evalPageSize);
        map.addAttribute("Like", new Lists());
        return "index";
    }
}
