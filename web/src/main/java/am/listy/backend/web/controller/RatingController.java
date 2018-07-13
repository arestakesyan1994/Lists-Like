package am.listy.backend.web.controller;

import am.listy.backend.common.model.Lists;
import am.listy.backend.common.model.Rating;
import am.listy.backend.common.model.User;
import am.listy.backend.common.repository.ListsRepository;
import am.listy.backend.common.repository.RatingRepository;
import am.listy.backend.web.security.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class RatingController {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private ListsRepository listsRepository;

    @PostMapping("/addRating")
    public String addRating(@AuthenticationPrincipal CurrentUser currentUser,
                            @RequestParam(value = "id") String id,
                            @RequestParam(value = "id1") String id1,
                            @ModelAttribute Rating rating) {
        User user = currentUser.getUser();
        Optional<Lists> listing = listsRepository.findById(id);
        Lists lists = listing.get();
        int count = Integer.parseInt(id1);
        rating.setCount(count);
        rating.setLists(lists);
        rating.setUser(user);
        ratingRepository.save(rating);
        return "redirect:/";
    }
}
