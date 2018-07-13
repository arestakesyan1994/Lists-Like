package am.listy.backend.api.restController;

import am.listy.backend.common.model.Region;
import am.listy.backend.common.repository.RegionRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/region")
public class RegionController {

    @Autowired
    private RegionRepository regionRepository;

    @ApiOperation(value = "all users", notes = "get all users")
    @GetMapping
    public List<Region> regions() {
        return regionRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity getRegionById(@PathVariable(name = "id") String id) {
        Optional<Region> one = regionRepository.findById(id);
        if (one.get() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with " + id + " id no found");
        }
        return ResponseEntity.ok(one.get());
    }

    @PostMapping()
    public ResponseEntity saveRegion(@RequestBody Region region) {
            regionRepository.save(region);
            return ResponseEntity.ok("created");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteRegionById(@PathVariable(name = "id") String id) {
        Optional<Region> optionalRegion = regionRepository.findById(id);
        if (optionalRegion.isPresent()) {
            regionRepository.deleteById(id);
            return ResponseEntity.ok("Delete");
        }
        else {
            return ResponseEntity.badRequest().body("user with " + id + "does not exist");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity updateRegion(@PathVariable String id,
                                       @Valid @RequestBody Region region) {
        Optional<Region> optionalRegion = regionRepository.findById(id);
        Region region1 = optionalRegion.get();
        region1.setName(region.getName());
        regionRepository.save(region1);
        return ResponseEntity.ok("update");
    }
}

