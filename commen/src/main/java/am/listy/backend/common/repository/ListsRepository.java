package am.listy.backend.common.repository;

import am.listy.backend.common.model.Lists;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Email;
import java.util.List;

@Repository
public interface ListsRepository extends MongoRepository<Lists, String> {
    List<Lists> findAllByTitle(String title);
    List<Lists> findAllByUser_Email(String email);

}


