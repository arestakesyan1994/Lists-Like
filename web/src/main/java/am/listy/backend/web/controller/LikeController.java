package am.listy.backend.web.controller;

import am.listy.backend.common.model.Lists;
import am.listy.backend.common.model.User;
import am.listy.backend.common.repository.ListsRepository;
import am.listy.backend.web.security.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class LikeController {

    @Autowired
    private ListsRepository listsRepository;

    @PostMapping("/addLike")
    public String addLike(@RequestParam(value = "id") String id,
                          @AuthenticationPrincipal CurrentUser currentUser) {
        User user = currentUser.getUser();
        Optional<Lists> listing = listsRepository.findById(id);
        Lists lists = listing.get();
        lists.getLike().add(user);
        listsRepository.save(lists);
        return "redirect:/";
    }

}
