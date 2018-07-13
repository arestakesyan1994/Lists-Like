package am.listy.backend.web.controller;


import am.listy.backend.common.model.Comment;
import am.listy.backend.common.model.Lists;
import am.listy.backend.common.model.User;
import am.listy.backend.common.repository.CommentRepository;
import am.listy.backend.common.repository.ListsRepository;
import am.listy.backend.web.security.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class CommentController {

    @Autowired
    private ListsRepository listsRepository;

    @Autowired
    private CommentRepository commentRepository;

    @PostMapping("/addComment")
    public String addComment(@AuthenticationPrincipal CurrentUser currentUser,
                             @ModelAttribute("comment") Comment comment) {
        User user = currentUser.getUser();
        comment.setUser(user);
        commentRepository.save(comment);
        return "redirect:/listing-details-left";
    }

    @PostMapping("/addListsComment")
    public String addListsComment(@AuthenticationPrincipal CurrentUser currentUser,
                                  @RequestParam(value = "id") String id,
                                  @ModelAttribute("comment") Comment comment) {
        User user = currentUser.getUser();
        comment.setUser(user);
        Optional<Lists> listing = listsRepository.findById(id);
        Lists lists = listing.get();
        comment.setLists(lists);
        commentRepository.save(comment);
        return "redirect:/";
    }
}
