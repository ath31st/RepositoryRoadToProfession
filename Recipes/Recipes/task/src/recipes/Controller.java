package recipes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @Autowired
    RecipeService recipeService;

    @GetMapping("/api/recipe")
    public Recipe getRecipe(){
        return recipeService.getRecipeFromBD();
    }

    @PostMapping("/api/recipe")
    public void saveNewRecipe(@RequestBody Recipe recipe){
        recipeService.saveNewRecipe(recipe);
    }
}
