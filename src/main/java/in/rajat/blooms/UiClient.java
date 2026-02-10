package in.rajat.blooms;

import in.rajat.blooms.controller.CategoryController;
import in.rajat.blooms.controller.SubCategoryController;
import in.rajat.blooms.controller.UserController;
import in.rajat.blooms.dto.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class UiClient {
    public static void main(String[] args) {
        System.out.println("--- UI Client Started ---");

        CategoryController categoryController = new CategoryController();

        System.out.println("Step 1: Creating Categories...");
        CategoryRequest req1 = new CategoryRequest(
                "Technology",
                "All about Java and Coding",
                "https://tech-image.com/java.png");
        categoryController.createCategory(req1);

        System.out.println("\nStep 2: Fetching All Categories...");
        List<CategoryResponse> categoryResponseList = categoryController.getAllCategories();

        for (CategoryResponse cat : categoryResponseList) {
            System.out.println("----------------------------");
            System.out.println("ID: " + cat.getId());
            System.out.println("Title: " + cat.getTitle());
            System.out.println("----------------------------");
        }

        String techId = categoryResponseList.get(0).getId();
        System.out.println("Category ID for SubCategory test: " + techId);

        SubCategoryController subController = new SubCategoryController();
        System.out.println("\n--- Creating SubCategories ---");

        SubCategoryRequest javaReq = new SubCategoryRequest(techId, "Java", "Core Java");
        SubCategoryResponse javaRes = subController.create(javaReq);
        System.out.println("Created: " + javaRes.getName());

        System.out.println("\n--- Fetching SubCategories ---");
        List<SubCategoryResponse> techSub = subController.getSubCategoriesByCategory(techId);

        for (SubCategoryResponse sub : techSub) {
            System.out.println("Found: " + sub.getName());
        }

        System.out.println("\n--- TESTING USER MODULE ---");
        UserController userController = new UserController();

        UserRequest user1 = new UserRequest(
                "superadmin",
                "admin@blooms.in",
                "Super Admin",
                "Rajat@03",
                "avatar.png",
                "7004267042");

        String result = userController.registerUser(user1);
        System.out.println("Registration: " + result);

        System.out.println("\n--- Trying Login ---");
        UserRequest loginReq = new UserRequest();
        loginReq.setPhoneNumber("7004267042");
        loginReq.setPassword("Rajat@03");
        ResponseEntity<?> loginResponse = userController.login(loginReq);

        if (loginResponse.getStatusCode() == HttpStatus.OK) {
            UserResponse loggedInUser = (UserResponse) loginResponse.getBody();
            if (loggedInUser != null) {
                System.out.println("Welcome: " + loggedInUser.getName());
            }
        } else {
            System.out.println("Login failed: " + loginResponse.getBody());
        }
    }
}