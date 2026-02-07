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

        // Category Controller
        CategoryController categoryController = new CategoryController();

        System.out.println("Step 1: Creating Categories...");
        CategoryRequest req1 = new CategoryRequest(
                "Technology",
                "All about Java and Coding",
                "https://tech-image.com/java.png");
        categoryController.createCategory(req1);

        CategoryRequest req2 = new CategoryRequest(
                "Health",
                "Fitness and Diet Tips",
                "https://health-image.com/yoga.png");
        categoryController.createCategory(req2);

        System.out.println("\nStep 2: Fetching All Categories...");
        List<CategoryResponse> categoryResponseList = categoryController.getCategories();

        for (CategoryResponse cat : categoryResponseList) {
            System.out.println("----------------------------");
            System.out.println("ID: " + cat.getId());
            System.out.println("Title: " + cat.getTitle());
            System.out.println("Description: " + cat.getDesc());
            // FIXED getter name
            System.out.println("Image URL: " + cat.getcUrl());
            System.out.println("----------------------------");
        }

        CategoryController catController = new CategoryController();
        CategoryRequest catReq = new CategoryRequest("Technology", "Tech Stuff", "url");
        catController.createCategory(catReq);

        String techId = catController.getCategories().get(0).getId();
        System.out.println("Tech Category ID mil gayi: " + techId);

        SubCategoryController subController = new SubCategoryController();
        System.out.println("\n--- Creating SubCategories ---");

        SubCategoryRequest javaReq = new SubCategoryRequest(techId, "Java", "Core Java");
        SubCategoryResponse javaRes = subController.create(javaReq);
        System.out.println("Created: " + javaRes.getName());

        SubCategoryRequest pythonReq = new SubCategoryRequest(techId, "Python", "AI ML");
        SubCategoryResponse pythonRes = subController.create(pythonReq);
        System.out.println("Created: " + pythonRes.getName());

        SubCategoryRequest errorReq = new SubCategoryRequest("9999", "Alien Tech", "Sci-Fi Stuff");
        SubCategoryResponse errorRes = subController.create(errorReq);
        System.out.println("Created: " + errorRes.getName());

        System.out.println("\n--- Fetching SubCategories for Technology ---");
        List<SubCategoryResponse> techSub = subController.getCategory(techId);

        for (SubCategoryResponse sub : techSub) {
            System.out.println("Found: " + sub.getName());
        }

        System.out.println("\n--- TESTING USER MODULE ---");
        UserController userController = new UserController();

        // Register a New User (6 args constructor)
        UserRequest user1 = new UserRequest(
                "superadmin",
                "admin@blooms.in",
                "Super Admin",
                "Rajat@03",
                "avatar.png",
                "7004267042" // phone number required
        );

        String result = userController.registerUser(user1);
        System.out.println(result);

        // Try Registering duplicate (Validation Check)
        String result2 = userController.registerUser(user1);
        System.out.println("Duplicate Check: " + result2);

        // Login with WRONG password
        System.out.println("\n--- Trying Wrong Login ---");
        UserRequest wrongLogin = new UserRequest();
        wrongLogin.setPhoneNumber("7004267042");
        wrongLogin.setPassword("Rajat@03");
        ResponseEntity<?> wrongResponse = userController.login(wrongLogin);
        System.out.println("Login Response: " + wrongResponse.getBody());

        // Login with CORRECT password
        System.out.println("\n--- Trying Correct Login ---");
        UserRequest correctLogin = new UserRequest();
        correctLogin.setPhoneNumber("7004267042");
        correctLogin.setPassword("Rajat@03");
        ResponseEntity<?> loginResponse = userController.login(correctLogin);

        if (loginResponse.getStatusCode() == HttpStatus.OK) {
            UserResponse loggedInUser = (UserResponse) loginResponse.getBody();
            if (loggedInUser != null) {
                System.out.println("Welcome Dashboard: " + loggedInUser.getName());
            }
        } else {
            System.out.println("Login failed: " + loginResponse.getBody());
        }
    }
}