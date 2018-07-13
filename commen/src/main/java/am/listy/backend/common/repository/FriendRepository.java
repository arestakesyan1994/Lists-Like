package am.listy.backend.common.repository;

import am.listy.backend.common.model.Friend;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendRepository extends MongoRepository<Friend,String> {
    List<Friend> findAllByFirstId_Email(String email);
    List<Friend> findAllBySecondId_Email(String email);
    List<Friend> deleteByFirstId_EmailAndSecondId_Email(String s1,String s2);
}
