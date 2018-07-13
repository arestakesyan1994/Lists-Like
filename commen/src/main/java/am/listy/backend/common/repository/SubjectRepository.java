package am.listy.backend.common.repository;

import am.listy.backend.common.model.Subject;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SubjectRepository extends MongoRepository<Subject,String> {

}
