package am.listy.backend.common.repository;

import am.listy.backend.common.model.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoryRepository extends MongoRepository<Category,String> {

}
