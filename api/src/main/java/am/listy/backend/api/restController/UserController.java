package am.listy.backend.api.restController;

import am.listy.backend.api.security.JwtTokenUtil;
import am.listy.backend.common.dto.AuthenticationRequest;
import am.listy.backend.common.dto.AuthenticationResponse;
import am.listy.backend.common.dto.UserDto;
import am.listy.backend.common.model.User;
import am.listy.backend.common.repository.UserRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @ApiOperation(value = "all users", notes = "get all users")
    @GetMapping
    public List<User> users() {
        return userRepository.findAll();
    }

    @GetMapping("/names")
    public List<UserDto> userNames() {
        List<User> name = userRepository.findAll();
        List<UserDto> userDtos = new LinkedList<>();
        name.forEach(e -> userDtos.add(new UserDto(e.getId(), e.getName(), e.getSurname(), e.getUsername(), e.getPhone(), e.getEmail(), e.getType())));
        return userDtos;
    }

    @GetMapping("/{id}")
    public ResponseEntity getUserById(@PathVariable(name = "id") String id) {
        Optional<User> one = userRepository.findById(id);
        if (one.get() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with " + id + " id no found");
        }
        return ResponseEntity.ok(one.get());
    }

    @PostMapping()
    public ResponseEntity saveUser(@RequestBody User user) {
        if (userRepository.findByEmail(user.getEmail()) == null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return ResponseEntity.ok("created");
        }
        return ResponseEntity.badRequest().body("User with " + user.getEmail() + " already exist");
    }

    @PostMapping("/user")
    public ResponseEntity users(@RequestBody AuthenticationRequest authenticationRequest) {
        User user = userRepository.findByEmail(authenticationRequest.getEmail());
        if (user != null && passwordEncoder.matches(authenticationRequest.getPassword(), user.getPassword())) {
            String token = jwtTokenUtil.generateToken(user.getEmail());
            return ResponseEntity.ok(AuthenticationResponse.builder()
                    .token(token)
                    .userDto(UserDto.builder()
                            .id(user.getId())
                            .name(user.getName())
                            .surname(user.getSurname())
                            .email(user.getEmail())
                            .userType(user.getType())
                            .build())
                    .build());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUserById(@PathVariable(name = "id") String id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            userRepository.deleteById(id);
            return ResponseEntity.ok("Delete");
        } else {
            return ResponseEntity.badRequest().body("user with " + id + "does not exist");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable String id,
                                 @RequestBody User user) {
        Optional<User> optionalUser = userRepository.findById(id);
        User user1 = optionalUser.get();
        user1.setName(user.getName());
        user1.setSurname(user.getSurname());
        user1.setUsername(user.getUsername());
        user1.setPhone(user.getPhone());
        user1.setEmail(user.getEmail());
        user1.setPassword(user.getPassword());
        userRepository.save(user1);
        return ResponseEntity.ok("update");
    }
}

