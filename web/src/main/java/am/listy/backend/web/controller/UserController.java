package am.listy.backend.web.controller;

import am.listy.backend.common.model.Comment;
import am.listy.backend.common.model.Friend;
import am.listy.backend.common.model.User;
import am.listy.backend.common.repository.CommentRepository;
import am.listy.backend.common.repository.FriendRepository;
import am.listy.backend.common.repository.ListsRepository;
import am.listy.backend.common.repository.UserRepository;
import am.listy.backend.web.security.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ListsRepository listsRepository;

    @GetMapping("/guest-profile")
    public String guestProfile(ModelMap map,
                               @AuthenticationPrincipal CurrentUser currentUser,
                               @RequestParam(value = "id") String id) {
        map.addAttribute("allFriend", new Friend());
        Optional<User> user = userRepository.findById(id);
        User user1 = user.get();
        map.addAttribute("allComment", commentRepository.findAll());
        map.addAttribute("isLoggedIn", currentUser != null);
        map.addAttribute("comment", new Comment());
        map.addAttribute("user", user1);
        map.addAttribute("userLists", listsRepository.findAllByUser_Email(user1.getEmail()));
        return "guest-profile";
    }

}

