package in.rajat.blooms.controller;

import in.rajat.blooms.dto.BlogRequest;
import in.rajat.blooms.dto.BlogResponse;
import in.rajat.blooms.models.Blog;
import in.rajat.blooms.services.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/blog")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @PostMapping
    public BlogResponse createBlog(@RequestBody BlogRequest blogRequest) {
        Blog blog = new Blog();
        blog.setId(blogRequest.getId());
        blog.setContent(blogRequest.getContent());
        blog.setDescription(blogRequest.getDescription());
        blog.setAuthorId(blogRequest.getAuthorId());
        blog.setCategoryMappings(blogRequest.getCategoryMappings());

        Blog savedBlog = blogService.createBlog(blog);
        return mapToResponse(savedBlog);
    }

    @GetMapping
    public List<BlogResponse> getAllBlogs() {
        List<Blog> blogs = blogService.getAllBlogs();
        List<BlogResponse> responses = new ArrayList<>();

        for (Blog blog : blogs) {
            responses.add(mapToResponse(blog));
        }
        return responses;
    }

    @GetMapping("/{id}")
    public BlogResponse getBlogById(@PathVariable String id) {
        return blogService.getBlogById(id)
                .map(this::mapToResponse)
                .orElse(null);
    }

    @GetMapping("/author/{authorId}")
    public List<BlogResponse> getBlogByAuthor(@PathVariable String authorId) {
        List<Blog> blogs = blogService.getBlogsByAuthor(authorId);
        List<BlogResponse> blogResponses = new ArrayList<>();

        for (Blog b : blogs) {
            blogResponses.add(mapToResponse(b));
        }
        return blogResponses;
    }

    @DeleteMapping("/{id}")
    public boolean deleteBlog(@PathVariable String id) {
        return blogService.deleteBlog(id);
    }

    private BlogResponse mapToResponse(Blog blog) {
        return new BlogResponse(
                blog.getId(),
                blog.getTitle(),
                blog.getDescription(),
                blog.getContent(),
                blog.getAuthorId(),
                blog.getCategoryMappings());
    }
}
