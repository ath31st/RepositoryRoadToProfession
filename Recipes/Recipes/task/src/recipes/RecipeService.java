package recipes;

import org.springframework.stereotype.Service;

@Service
public class RecipeService {

  //  private final RecipeRepository recipeRepository;
    private Recipe recipe = new Recipe();

//    public RecipeService(RecipeRepository recipeRepository) {
//        this.recipeRepository = recipeRepository;
//    }

    public Recipe getRecipeFromBD() {
        return recipe;
    }

    public void saveNewRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}
