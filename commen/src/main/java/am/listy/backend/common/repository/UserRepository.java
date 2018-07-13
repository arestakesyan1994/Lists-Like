package am.listy.backend.common.repository;

import am.listy.backend.common.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User,String> {
    User findByEmail(String email);

    List<User> findAllByNameOrSurname(String s, String s1);
    List<User> findAllByName(String s);
    List<User> findAllBySurname(String s);

}
