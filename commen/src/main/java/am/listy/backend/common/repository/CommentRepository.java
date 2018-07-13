package am.listy.backend.common.repository;

import am.listy.backend.common.model.Comment;
import am.listy.backend.common.model.Lists;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface CommentRepository extends MongoRepository<Comment,String> {
    List<Comment> findAllByUserId(String id);
    List<Comment> findAllByListsId(String id);
}
