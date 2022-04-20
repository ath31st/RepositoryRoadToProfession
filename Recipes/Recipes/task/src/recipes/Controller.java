package recipes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class Controller {

    @Autowired
    RecipeService recipeService;

    @Autowired
    UserService userService;

    @GetMapping("/api/recipe/{id}")
    public Recipe getRecipe(@PathVariable Long id) {
        return recipeService.findRecipeById(id);
    }

    @PostMapping("/api/recipe/new")
    public String saveNewRecipe(@Valid @RequestBody Recipe recipe) {
        return recipeService.addNewRecipe(recipe);
    }

    @DeleteMapping("/api/recipe/{id}")
    public ResponseEntity deleteRecipeById(@PathVariable Long id) {
        return recipeService.deleteRecipeById(id);
    }

    @PutMapping("/api/recipe/{id}")
    public ResponseEntity updateRecipeById(@PathVariable Long id, @Valid @RequestBody Recipe recipe) {
        return recipeService.updateRecipeById(id, recipe);
    }

    @GetMapping("/api/recipe/search")
    public List<Recipe> findRecipeByParam(@RequestParam(required = false) String category, @RequestParam(required = false) String name) {
        return recipeService.findRecipeByParam(category, name);
    }

    @GetMapping("/test")
    public String test() {
        return "/test is accessed";
    }

    @PostMapping("/api/register")
    public void register(@Valid @RequestBody User user) {
        userService.registerNewUser(user);
    }
}
