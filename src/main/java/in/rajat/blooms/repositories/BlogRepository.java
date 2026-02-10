package in.rajat.blooms.repositories;

import in.rajat.blooms.models.Blog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends MongoRepository<Blog, String>{

    List<Blog> findByAuthorId(String authorId);

}
