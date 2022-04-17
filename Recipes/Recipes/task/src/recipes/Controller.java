package recipes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class Controller {

    @Autowired
    RecipeService recipeService;

    @GetMapping("/api/recipe/{id}")
    public Recipe getRecipe(@PathVariable Long id) {
        return recipeService.findRecipeById(id);
    }

    @PostMapping("/api/recipe/new")
    public String saveNewRecipe(@RequestBody Recipe recipe) {
       return recipeService.addNewRecipe(recipe);
    }
}
