package am.listy.backend.api.restController;

import am.listy.backend.common.model.Lists;
import am.listy.backend.common.repository.ListsRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/listy")
public class ListtyController {

    @Autowired
    private ListsRepository listRepository;

    @ApiOperation(value = "all users", notes = "get all users")
    @GetMapping
    public List<Lists> listties() {
        return listRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity getListtyById(@PathVariable(name = "id") String id) {
        Optional<Lists> one = listRepository.findById(id);
        if (one.get() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with " + id + " id no found");
        }
        return ResponseEntity.ok(one.get());
    }

    @PostMapping()
    public ResponseEntity saveList(@RequestBody Lists listty) {
            listRepository.save(listty);
            return ResponseEntity.ok("created");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteListtyById(@PathVariable(name = "id") String id) {
        Optional<Lists> optionalListty = listRepository.findById(id);
        if (optionalListty.isPresent()) {
            listRepository.deleteById(id);
            return ResponseEntity.ok("Delete");
        }
        else {
            return ResponseEntity.badRequest().body("user with " + id + "does not exist");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity updateListty(@PathVariable String id,
                                       @RequestBody Lists listty) {
        Optional<Lists> optionalListty = listRepository.findById(id);
        Lists listty1 = optionalListty.get();
        listty1.setTitle(listty.getTitle());
        listty1.setCategory(listty.getCategory());
        listty1.setDescription(listty.getDescription());
        listty1.setTogs(listty.getTogs());
        listty1.setRegion(listty.getRegion());
        listty1.setUser(listty.getUser());
        listty1.setWebsite(listty.getWebsite());
        listty1.setVideoUrl(listty.getVideoUrl());
        listty1.setMs(listty.getMs());
        listRepository.save(listty1);
        return ResponseEntity.ok("update");
    }
}

