package am.listy.backend.web.controller;

import am.listy.backend.common.model.User;
import am.listy.backend.common.repository.UserRepository;
import am.listy.backend.web.security.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Controller
public class UpdateController {

    @Value("${image.folder}")
    private String imageUploadDir;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/updateUserPicUrl")
    public String addLists(@AuthenticationPrincipal CurrentUser currentUser,
                           @RequestParam("picture") MultipartFile multipartFile) throws IOException {
        User user = currentUser.getUser();
        String pictureName = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
        File imageDir = new File(imageUploadDir);
        if (!imageDir.exists()) {
            imageDir.mkdirs();
        }
        File file = new File(imageUploadDir + pictureName);
        multipartFile.transferTo(file);
        user.setPicUrl(pictureName);
        userRepository.save(user);
        return "redirect:/user-profile";
    }

    @PostMapping("/updatePicUrl")
    public String updateName(@AuthenticationPrincipal CurrentUser currentUser,
                         @ModelAttribute("user") User user,
                         @RequestParam("picture") MultipartFile multipartFile) throws IOException {
        Optional<User> optionalUser = userRepository.findById(currentUser.getUser().getId());
        User user1 = optionalUser.get();
        String pictureName = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
        File imageDir = new File(imageUploadDir);
        if (!imageDir.exists()) {
            imageDir.mkdirs();
        }
        File file = new File(imageUploadDir + pictureName);
        multipartFile.transferTo(file);
        user1.setPicUrl(pictureName);
        userRepository.save(user1);
        return "redirect:/profile-admin";
    }

    @PostMapping("/updateName")
    public String updateName(@AuthenticationPrincipal CurrentUser currentUser,
                             @ModelAttribute("user") User user) {
        Optional<User> optionalUser = userRepository.findById(currentUser.getUser().getId());
        User user1 = optionalUser.get();
        user1.setName(user.getName());
        userRepository.save(user1);
        return "redirect:/update-user";
    }

    @PostMapping("/updateSurname")
    public String updateSurname(@AuthenticationPrincipal CurrentUser currentUser,
                                @ModelAttribute("user") User user) {
        Optional<User> optionalUser = userRepository.findById(currentUser.getUser().getId());
        User user1 = optionalUser.get();
        user1.setSurname(user.getSurname());
        userRepository.save(user1);
        return "redirect:/update-user";
    }

    @PostMapping("/updateEmail")
    public String updateEmail(@AuthenticationPrincipal CurrentUser currentUser,
                              @ModelAttribute("user") User user) {
        Optional<User> optionalUser = userRepository.findById(currentUser.getUser().getId());
        User user1 = optionalUser.get();
        user1.setEmail(user.getEmail());
        userRepository.save(user1);
        return "redirect:/update-user";
    }

    @PostMapping("/updatePhone")
    public String updatePhone(@AuthenticationPrincipal CurrentUser currentUser,
                              @ModelAttribute("user") User user) {
        Optional<User> optionalUser = userRepository.findById(currentUser.getUser().getId());
        User user1 = optionalUser.get();
        user1.setPhone(user.getPhone());
        userRepository.save(user1);
        return "redirect:/update-user";
    }

    @PostMapping("/updateDescription")
    public String updateDescription(@AuthenticationPrincipal CurrentUser currentUser,
                                    @ModelAttribute("user") User user) {
        Optional<User> optionalUser = userRepository.findById(currentUser.getUser().getId());
        User user1 = optionalUser.get();
        user1.setDescription(user.getDescription());
        userRepository.save(user1);
        return "redirect:/update-user";
    }

    @PostMapping("/updateLinkUrl")
    public String updateLinkUrl(@AuthenticationPrincipal CurrentUser currentUser,
                                @ModelAttribute("user") User user) {
        Optional<User> optionalUser = userRepository.findById(currentUser.getUser().getId());
        User user1 = optionalUser.get();
        user1.setLinkedUrl(user.getLinkedUrl());
        userRepository.save(user1);
        return "redirect:/update-user";
    }

    @PostMapping("/updateFacebookUrl")
    public String updateFacebookUrl(@AuthenticationPrincipal CurrentUser currentUser,
                                    @ModelAttribute("user") User user) {
        Optional<User> optionalUser = userRepository.findById(currentUser.getUser().getId());
        User user1 = optionalUser.get();
        user1.setFacebookUrl(user.getFacebookUrl());
        userRepository.save(user1);
        return "redirect:/update-user";
    }

    @PostMapping("/updateTwitterUrl")
    public String updateTwitterUrl(@AuthenticationPrincipal CurrentUser currentUser,
                                   @ModelAttribute("user") User user) {
        Optional<User> optionalUser = userRepository.findById(currentUser.getUser().getId());
        User user1 = optionalUser.get();
        user1.setTwitterUrl(user.getTwitterUrl());
        userRepository.save(user1);
        return "redirect:/update-user";
    }

    @PostMapping("/updateYouTubeUrl")
    public String updateYouTubeUrl(@AuthenticationPrincipal CurrentUser currentUser,
                                   @ModelAttribute("user") User user) {
        Optional<User> optionalUser = userRepository.findById(currentUser.getUser().getId());
        User user1 = optionalUser.get();
        user1.setYouTubeUrl(user.getYouTubeUrl());
        userRepository.save(user1);
        return "redirect:/update-user";
    }

    @PostMapping("/updatePassword")
    public String updatePassword(@AuthenticationPrincipal CurrentUser currentUser,
                                 @ModelAttribute("user") User user) {
        Optional<User> optionalUser = userRepository.findById(currentUser.getUser().getId());
        User user1 = optionalUser.get();
        user1.setPassword(user.getPassword());
        userRepository.save(user1);
        return "redirect:/update-user";
    }

    @GetMapping("/update-user")
    public String updateUser(ModelMap map,
                             @AuthenticationPrincipal CurrentUser currentUser) {
        map.addAttribute("isLoggedIn", currentUser != null);
        map.addAttribute("user", currentUser.getUser());
        return "update-user";
    }

}
