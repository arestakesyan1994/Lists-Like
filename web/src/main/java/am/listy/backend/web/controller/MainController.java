package am.listy.backend.web.controller;

import am.listy.backend.common.model.*;
import am.listy.backend.common.repository.*;
import am.listy.backend.web.security.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class MainController {
    @Autowired
    private FriendRepository friendRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ListsRepository listsRepository;

    @GetMapping("/category-list-left")
    public String categoryListLeft(ModelMap map,
                                   @AuthenticationPrincipal UserDetails userDetails) {
        map.addAttribute("isLoggedIn", userDetails != null);
        map.addAttribute("Lists", listsRepository.findAll());
        return "category-list-left";
    }

    @GetMapping("/404-page")
    public String page(ModelMap map,
                       @AuthenticationPrincipal UserDetails userDetails) {
        map.addAttribute("isLoggedIn", userDetails != null);
        return "404-page";
    }

    @GetMapping("/all-business")
    public String allBusiness(ModelMap map,
                              @AuthenticationPrincipal UserDetails userDetails) {
        map.addAttribute("isLoggedIn", userDetails != null);
        return "all-business";
    }

    @GetMapping("/blog")
    public String blog(ModelMap map,
                       @AuthenticationPrincipal UserDetails userDetails) {
        map.addAttribute("isLoggedIn", userDetails != null);
        return "blog";
    }

    @GetMapping("/blog-details")
    public String blogDetails(ModelMap map,
                              @RequestParam(value = "id") String id,
                              @AuthenticationPrincipal UserDetails userDetails) {
        map.addAttribute("commentLists", new Comment());
        map.addAttribute("isLoggedIn", userDetails != null);
        map.addAttribute("oneLists", listsRepository.findById(id).get());
        map.addAttribute("comments", commentRepository.findAllByListsId(id));
        map.addAttribute("rating", new Lists());
        return "blog-details";
    }

    @GetMapping("/booking-list")
    public String bookingList(ModelMap map,
                              @RequestParam(value = "id") String id,
                              @AuthenticationPrincipal UserDetails userDetails) {
        Optional<User> user = userRepository.findById(id);
        User user1 = user.get();
        map.addAttribute("isLoggedIn", userDetails != null);
        map.addAttribute("user1", user1);
        map.addAttribute("userLists", listsRepository.findAllByUser_Email(user1.getEmail()));
        return "booking-list";
    }

    @GetMapping("/category-grid")
    public String categoryGrid(ModelMap map,
                               @AuthenticationPrincipal UserDetails userDetails,
                               @RequestParam(value = "keyword") String text) {
        map.addAttribute("isLoggedIn", userDetails != null);
        map.addAttribute("AllLists", listsRepository.findAll());
        map.addAttribute("list", listsRepository.findAll());
        String[] split = text.split(";");
        if (split.length == 1) {
            if (listsRepository.findAllByTitle(split[0]).size() != 0) {
                map.addAttribute("searchLists", listsRepository.findAllByTitle(split[0]));
            }
        }
        return "category-grid";
    }

    @GetMapping("/search-user")
    public String searchUser(ModelMap map,
                             @AuthenticationPrincipal UserDetails userDetails,
                             @RequestParam(value = "keyword") String text) {
        map.addAttribute("isLoggedIn", userDetails != null);
        String[] split = text.split(" ");
        if (split.length == 2) {
            map.addAttribute("searchUser", userRepository.findAllByNameOrSurname(split[0], split[1]));
        }
        if (split.length == 1) {
            if (userRepository.findAllByName(split[0]).size() != 0) {
                map.addAttribute("searchUser", userRepository.findAllByName(split[0]));
            } else {
                map.addAttribute("searchUser", userRepository.findAllBySurname(split[0]));
            }
        }
        return "search-user";
    }

    @GetMapping("/comming-soon")
    public String comingSoon(ModelMap map,
                             @AuthenticationPrincipal UserDetails userDetails) {
        map.addAttribute("isLoggedIn", userDetails != null);
        return "comming-soon";
    }

    @GetMapping("/contact-us")
    public String contactUs(ModelMap map,
                            @AuthenticationPrincipal UserDetails userDetails) {
        map.addAttribute("isLoggedIn", userDetails != null);
        map.addAttribute("allSubjects", subjectRepository.findAll());
        return "contact-us";
    }

    @GetMapping("/dashboard")
    public String dashboard(ModelMap map,
                            @AuthenticationPrincipal UserDetails userDetails) {
        map.addAttribute("isLoggedIn", userDetails != null);
        return "dashboard";
    }

    @GetMapping("/dashboard-reviews")
    public String dashboardReviews(ModelMap map,
                                   @AuthenticationPrincipal UserDetails userDetails) {
        map.addAttribute("isLoggedIn", userDetails != null);
        map.addAttribute("allUser", userRepository.findAll());
        return "dashboard-reviews";
    }

    @GetMapping("/edit-listings")
    public String editListings(ModelMap map,
                               @AuthenticationPrincipal UserDetails userDetails) {
        map.addAttribute("isLoggedIn", userDetails != null);
        map.addAttribute("allCategoreis", categoryRepository.findAll());
        return "edit-listings";
    }

    @GetMapping("/how-it-works")
    public String howItWorks(ModelMap map,
                             @AuthenticationPrincipal UserDetails userDetails) {
        map.addAttribute("isLoggedIn", userDetails != null);
        return "how-it-works";
    }

    @GetMapping("/index-2")
    public String index2(ModelMap map,
                         @AuthenticationPrincipal UserDetails userDetails) {
        map.addAttribute("isLoggedIn", userDetails != null);
        map.addAttribute("allLists", listsRepository.findAll());
        return "index-2";
    }

    @GetMapping("/index-4")
    public String index4(ModelMap map,
                         @AuthenticationPrincipal UserDetails userDetails) {
        map.addAttribute("isLoggedIn", userDetails != null);
        return "index-4";
    }

    @GetMapping("/listing-details-left")
    public String listingDetailsLeft(ModelMap map,
                                     @AuthenticationPrincipal UserDetails userDetails) {
        map.addAttribute("allComment", commentRepository.findAll());
        map.addAttribute("isLoggedIn", userDetails != null);
        map.addAttribute("comments", new Comment());
        return "listing-details-left";
    }

    @GetMapping("/listings")
    public String listings(ModelMap map,
                           @AuthenticationPrincipal CurrentUser currentUser) {
        map.addAttribute("isLoggedIn", currentUser != null);
        map.addAttribute("userLists", listsRepository.findAllByUser_Email(currentUser.getUser().getEmail()));
        return "listings";
    }

    @GetMapping("/listing-sidebar-map-left")
    public String listingSidebarMapLeft(ModelMap map,
                                        @AuthenticationPrincipal UserDetails userDetails) {
        map.addAttribute("isLoggedIn", userDetails != null);
        map.addAttribute("allCategoris", categoryRepository.findAll());
        map.addAttribute("AllListsiss", listsRepository.findAll());
        return "listing-sidebar-map-left";
    }

    @GetMapping("/login")
    public String login(ModelMap map,
                        @AuthenticationPrincipal UserDetails userDetails) {
        map.addAttribute("isLoggedIn", userDetails != null);
        map.addAttribute("allUser", new User());
        return "login";
    }

    @GetMapping("/loginSuccess")
    public String loginSuccess(@AuthenticationPrincipal UserDetails userDetails) {
        CurrentUser currentUser = (CurrentUser) userDetails;
        if (currentUser != null) {
            if (currentUser.getUser().getType() == UserType.ADMIN) {
                return "redirect:/profile-admin";
            } else if (currentUser.getUser().getType() == UserType.USER) {
                return "redirect:/user-profile";
            }
        }
        return "redirect:/login";
    }

    @GetMapping("/profile-admin")
    public String profileAdmin(ModelMap map,
                               @AuthenticationPrincipal CurrentUser currentUser) {
        map.addAttribute("alComment", commentRepository.findAll());
        map.addAttribute("isLoggedIn", currentUser != null);
        map.addAttribute("comments", new Comment());
        map.addAttribute("user", currentUser.getUser());
        map.addAttribute("currentFollowing", friendRepository.findAllByFirstId_Email(currentUser.getUser().getEmail()));
        map.addAttribute("currentFolowers",friendRepository.findAllBySecondId_Email(currentUser.getUser().getEmail()));
        map.addAttribute("DeleteFollowing", new Friend());
        return "profile-admin";
    }

    @GetMapping("/user-profile")
    public String userProfile(ModelMap map,
                              @AuthenticationPrincipal CurrentUser currentUser) {
        map.addAttribute("allComment", commentRepository.findAll());
        map.addAttribute("isLoggedIn", currentUser != null);
        map.addAttribute("comment", new Comment());
        map.addAttribute("user", currentUser.getUser());
        map.addAttribute("currentFollowing", friendRepository.findAllByFirstId_Email(currentUser.getUser().getEmail()));
        map.addAttribute("currentFollowers",friendRepository.findAllBySecondId_Email(currentUser.getUser().getEmail()));
        map.addAttribute("deleteFollowing", new Friend());
        return "user-profile";
    }

    @GetMapping("/oders")
    public String oders(ModelMap map,
                        @AuthenticationPrincipal UserDetails userDetails) {
        map.addAttribute("isLoggedIn", userDetails != null);
        return "oders";
    }

    @GetMapping("/payment-process")
    public String paymentProcess(ModelMap map,
                                 @AuthenticationPrincipal UserDetails userDetails) {
        map.addAttribute("isLoggedIn", userDetails != null);
        return "payment-process";
    }

    @GetMapping("/pricing-table")
    public String pricingTable(ModelMap map,
                               @AuthenticationPrincipal UserDetails userDetails) {
        map.addAttribute("isLoggedIn", userDetails != null);
        return "pricing-table";
    }

    @GetMapping("/sign-up")
    public String signUp(ModelMap map,
                         @AuthenticationPrincipal UserDetails userDetails) {
        map.addAttribute("isLoggedIn", userDetails != null);
        map.addAttribute("users", new User());
        return "sign-up";
    }

    @GetMapping("/terms-of-services")
    public String termsOfServices(ModelMap map,
                                  @AuthenticationPrincipal UserDetails userDetails) {
        map.addAttribute("isLoggedIn", userDetails != null);
        return "terms-of-services";
    }
}