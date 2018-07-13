package am.listy.backend.common.repository;

import am.listy.backend.common.model.Rating;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RatingRepository extends MongoRepository<Rating,String> {
    List<Rating> findAllByListsId(String id);

}
