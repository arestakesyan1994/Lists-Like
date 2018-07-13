package am.listy.backend.api.restController;


import am.listy.backend.common.model.Category;
import am.listy.backend.common.repository.CategoryRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/category")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @ApiOperation(value = "all users", notes = "get all users")
    @GetMapping
    public List<Category> categories() {
        return categoryRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity getCategoryById(@PathVariable(name = "id") String id) {
        Optional<Category> one = categoryRepository.findById(id);
        if (one.get() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with " + id + " id no found");
        }
        return ResponseEntity.ok(one.get());
    }

    @PostMapping()
    public ResponseEntity saveCategory(@RequestBody Category category) {
            categoryRepository.save(category);
            return ResponseEntity.ok("created");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCategoryById(@PathVariable(name = "id") String id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent()) {
            categoryRepository.deleteById(id);
            return ResponseEntity.ok("Delete");
        }
        else {
            return ResponseEntity.badRequest().body("user with " + id + "does not exist");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity updateCategory(@PathVariable String id,
                                          @RequestBody Category category) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        Category category1 = optionalCategory.get();
        category1.setName(category.getName());
        categoryRepository.save(category1);
        return ResponseEntity.ok("update");
    }
}