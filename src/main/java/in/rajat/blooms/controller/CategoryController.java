package in.rajat.blooms.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import in.rajat.blooms.dto.CategoryRequest;
import in.rajat.blooms.dto.CategoryResponse;
import in.rajat.blooms.models.Category;
import in.rajat.blooms.models.Status;
import in.rajat.blooms.repositories.CategoryRepository;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @PostMapping
    public CategoryResponse createCategory(@RequestBody CategoryRequest request) {
        Category category = new Category();
        category.setName(request.getTitle());
        category.setDescription(request.getDesc());
        category.setImageUrl(request.getcUrl());
        category.setStatus(Status.PUBLISHED.getDisplayName());
        category.setCreatedBy("Admin");
        category.setActive(true);
        category.setCreatedDTTM(LocalDateTime.now());

        Category savedCategory = categoryRepository.save(category);
        return new CategoryResponse(savedCategory.getId(), savedCategory.getName(), savedCategory.getDescription(),
                savedCategory.getImageUrl());
    }

    @GetMapping("/{id}")
    public CategoryResponse getCategoryById(@PathVariable String id) {
        return categoryRepository.findById(id)
                .map(c -> new CategoryResponse(c.getId(), c.getName(), c.getDescription(), c.getImageUrl()))
                .orElse(null);
    }

    @GetMapping("/all")
    public List<CategoryResponse> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryResponse> responses = new ArrayList<>();

        for (Category c : categories) {
            responses.add(new CategoryResponse(c.getId(), c.getName(), c.getDescription(), c.getImageUrl()));
        }
        return responses;
    }

    @DeleteMapping("/{id}")
    public boolean deleteCategory(@PathVariable String id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
