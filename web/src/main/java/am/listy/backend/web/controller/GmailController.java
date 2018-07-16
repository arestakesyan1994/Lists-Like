package am.listy.backend.web.controller;

import am.listy.backend.common.mail.EmailServiceImpl;
import am.listy.backend.common.model.User;
import am.listy.backend.common.model.UserType;
import am.listy.backend.common.repository.UserRepository;
import am.listy.backend.web.security.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
public class GmailController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailServiceImpl emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/verify")
    public String verify(ModelMap map,
                         @RequestParam("token") String token,
                         @RequestParam("email") String email,
                         @AuthenticationPrincipal CurrentUser currentUser){
        map.addAttribute("isLoggedIn", currentUser != null);
        User oneByEmail = userRepository.findByEmail(email);
        if(oneByEmail!=null){
            if(oneByEmail.getToken()!=null && oneByEmail.getToken().equals(token)){
                oneByEmail.setToken(null);
                oneByEmail.setVerify(true);
                userRepository.save(oneByEmail);
            }
        }
        return "redirect:/";
    }

    @PostMapping("/addUser")
    public String addUser(@ModelAttribute("user") User user) {
        if (userRepository.findByEmail(user.getEmail())== null) {
            user.setType(UserType.USER);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setPassword(user.getPassword());
            user.setToken(UUID.randomUUID().toString());
            userRepository.save(user);
            String url = String.format("http://http://localhost:8081/verify?token=%s&email=%s", user.getToken(), user.getEmail());
            String text = String.format("Dear %s Thank you, you have successfully registered to  our Evens_Tracker. Please visit by link in order to activate your profile.  %s", user.getName(), url);
            emailService.sendSipmleMessage(user.getEmail(), "Welcome", text);
            return "redirect:/login";
        }else
            return "redirect:/sign-up";
    }

    @GetMapping("/verifyError")
        public String verifyError(ModelMap map,
                                  @AuthenticationPrincipal CurrentUser currentUser){
        map.addAttribute("isLoggedIn", currentUser != null);
        return "verifyError";
    }

}

