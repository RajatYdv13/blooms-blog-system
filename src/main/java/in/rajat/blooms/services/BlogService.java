package in.rajat.blooms.services;

import in.rajat.blooms.dto.BlogRequest;
import in.rajat.blooms.dto.BlogResponse;
import in.rajat.blooms.models.Blog;
import in.rajat.blooms.repositories.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BlogService {


    @Autowired
    private BlogRepository blogRepository;

    public Blog createBlog(Blog request){
        request.setCreatedAt(new Date());
        return blogRepository.save(request);
    }

    public Optional<Blog> getBlogById(String id){
        return blogRepository.findById(id);
    }

    public List<Blog> getAllBlogs(){
        return blogRepository.findAll();
    }

    public boolean deleteBlog(String id){
        if(blogRepository.existsById(id)){
            blogRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Blog> getBlogsByAuthor(String authorId){
        return blogRepository.findByAuthorId(authorId);
    }


}
