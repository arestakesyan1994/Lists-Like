package am.listy.backend.web.controller;

import am.listy.backend.common.model.*;
import am.listy.backend.common.repository.*;
import am.listy.backend.common.repository.SubjectRepository;
import am.listy.backend.web.security.CurrentUser;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class AdminController {

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private ListsRepository listsRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${image.folder}")
    private String imageUploadDir;

    @GetMapping("/add-listings")
    public String addListings(ModelMap map,
                              @AuthenticationPrincipal UserDetails userDetails) {
        map.addAttribute("isLoggedIn", userDetails != null);
        map.addAttribute("allCategory", categoryRepository.findAll());
        map.addAttribute("allRegions", regionRepository.findAll());
        map.addAttribute("lists", new Lists());
        return "add-listings";
    }

    @GetMapping("/addCategory")
    public String addCategory(ModelMap map,
                              @AuthenticationPrincipal UserDetails userDetails) {
        map.addAttribute("isLoggedIn", userDetails != null);
        map.addAttribute("category", new Category());
        return "addCategory";
    }

    @PostMapping("/addUser")
    public String addUser(@ModelAttribute("user") User user) {
        user.setType(UserType.USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "redirect:/sign-up";
    }

    @PostMapping("/add-listings")
    public String addLists(@AuthenticationPrincipal CurrentUser currentUser,
                           @ModelAttribute(name = "lists") Lists lists,
                           @RequestParam("picture") MultipartFile multipartFile) throws IOException {
        String pictureName = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
        File imageDir = new File(imageUploadDir);
        if (!imageDir.exists()) {
            imageDir.mkdirs();
        }
        File file = new File(imageUploadDir + pictureName);
        multipartFile.transferTo(file);
        User user = currentUser.getUser();
        lists.setUser(user);
        lists.setPicUrl(pictureName);
        listsRepository.save(lists);
        return "redirect:/add-listings";
    }

    @GetMapping("/addSubject")
    public String addSubject(ModelMap map,
                             @AuthenticationPrincipal UserDetails userDetails) {
        map.addAttribute("isLoggedIn", userDetails != null);
        map.addAttribute("subject", new Category());
        return "addSubject";
    }

    @GetMapping("/addRegion")
    public String addRegion(ModelMap map,
                            @AuthenticationPrincipal UserDetails userDetails) {
        map.addAttribute("isLoggedIn", userDetails != null);
        map.addAttribute("region", new Region());
        return "addRegion";
    }

    @GetMapping("/image")
    public void getImage(HttpServletResponse response,
                         @RequestParam("fileName") String fileName) throws IOException {
        InputStream in = new FileInputStream(imageUploadDir + fileName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        IOUtils.copy(in, response.getOutputStream());
    }

    @PostMapping("/addSubject")
    public String addSubject(@ModelAttribute("subjects") Subject subject) {
        subjectRepository.save(subject);
        return "redirect:/addSubject";
    }

    @PostMapping("/addRegion")
    public String addRegion(@ModelAttribute("region") Region region) {
        regionRepository.save(region);
        return "redirect:/addRegion";
    }

    @PostMapping("/addCategory")
    public String addCategory(@ModelAttribute("category") Category category) {
        categoryRepository.save(category);
        return "redirect:/addCategory";
    }



}