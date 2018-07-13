package am.listy.backend.web.controller;

import am.listy.backend.common.model.Friend;
import am.listy.backend.common.model.User;
import am.listy.backend.common.repository.FriendRepository;
import am.listy.backend.common.repository.UserRepository;
import am.listy.backend.web.security.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class FollowingUserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FriendRepository friendRepository;

    @PostMapping("/following")
    public String following(@AuthenticationPrincipal CurrentUser currentUser,
                            @RequestParam(value = "id") String id,
                            @ModelAttribute("friend") Friend friend) {
        User user = currentUser.getUser();
        friend.setFirstId(user);
        Optional<User> byId = userRepository.findById(id);
        User user1 = byId.get();
        friend.setSecondId(user1);
        friendRepository.save(friend);
        return "redirect:/";
    }
}
