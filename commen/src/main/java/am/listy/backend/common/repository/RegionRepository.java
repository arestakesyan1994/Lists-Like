package am.listy.backend.common.repository;

import am.listy.backend.common.model.Region;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RegionRepository extends MongoRepository<Region,String> {

}
