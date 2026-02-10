package in.rajat.blooms.controller;

import in.rajat.blooms.dto.SubCategoryRequest;
import in.rajat.blooms.dto.SubCategoryResponse;
import in.rajat.blooms.models.SubCategory;
import in.rajat.blooms.services.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/subcategory")
public class SubCategoryController {

    @Autowired
    private SubCategoryService subCategoryService;

    @PostMapping
    public SubCategoryResponse create(@RequestBody SubCategoryRequest request) {
        SubCategory subCategory = new SubCategory();
        subCategory.setName(request.getName());
        subCategory.setDescription(request.getDescription());
        subCategory.setCategoryId(request.getCategoryId());

        SubCategory saved = subCategoryService.createSubCategory(subCategory);
        return new SubCategoryResponse(saved.getId(), saved.getCategoryId(), saved.getName(), saved.getDescription());
    }

    @GetMapping("/category/{categoryId}")
    public List<SubCategoryResponse> getSubCategoriesByCategory(@PathVariable String categoryId) {
        List<SubCategory> list = subCategoryService.getSubCategoriesByCategory(categoryId);
        List<SubCategoryResponse> responses = new ArrayList<>();
        for (SubCategory s : list) {
            SubCategoryResponse res = new SubCategoryResponse();
            res.setId(s.getId());
            res.setName(s.getName());
            res.setDescription(s.getDescription());
            res.setCategoryId(s.getCategoryId());
            responses.add(res);
        }
        return responses;
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable String id) {
        return subCategoryService.deleteSubCategory(id);
    }

    @GetMapping
    public List<SubCategory> getAll() {
        return subCategoryService.getAll();
    }
}
