package am.listy.backend.api.restController;

import am.listy.backend.common.model.Comment;
import am.listy.backend.common.repository.CommentRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/comment")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @ApiOperation(value = "all users", notes = "get all users")
    @GetMapping
    public List<Comment> comments() {
        return commentRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity getCommentById(@PathVariable(name = "id") String id) {
        Optional<Comment> one = commentRepository.findById(id);
        if (one.get() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with " + id + " id no found");
        }
        return ResponseEntity.ok(one.get());
    }

    @GetMapping("/byUserId/{userId}")
    public ResponseEntity getCommentByUserId(@PathVariable(name = "userId") String id) {
        List<Comment> byUserId = commentRepository.findAllByUserId(id);
        if (byUserId == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task with " + id + " id no found");
        }
        return ResponseEntity.ok(byUserId);
    }

    @PostMapping()
    public ResponseEntity saveComment(@RequestBody Comment comment) {
        commentRepository.save(comment);
            return ResponseEntity.ok("created");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCommentById(@PathVariable(name = "id") String id) {
        Optional<Comment> optionalComment = commentRepository.findById(id);
        if (optionalComment.isPresent()) {
            commentRepository.deleteById(id);
            return ResponseEntity.ok("Delete");
        }
        else {
            return ResponseEntity.badRequest().body("user with " + id + "does not exist");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity updateComment(@PathVariable String id,
                                        @Valid @RequestBody Comment comment) {
        Optional<Comment> optionalComment = commentRepository.findById(id);
        Comment comment1 = optionalComment.get();
        comment1.setDescription(comment.getDescription());
        comment1.setUser(comment.getUser());
        commentRepository.save(comment1);
        return ResponseEntity.ok("update");
    }
}