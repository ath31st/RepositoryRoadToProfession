package recipes;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public Recipe findRecipeById(Long id) {
        return recipeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public String addNewRecipe(Recipe recipe) {
        recipe.setDate(LocalDateTime.now());
        recipeRepository.save(recipe);
        return "{\"id\": " + recipe.getId() + "}";
    }

    public ResponseEntity deleteRecipeById(Long id) {
        if (recipeRepository.findById(id).isPresent()) {
            recipeRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity updateRecipeById(Long id, Recipe recipe) {
        Recipe recipeFromDb = recipeRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        recipeFromDb = recipe;
        recipeFromDb.setId(id);
        recipeFromDb.setDate(LocalDateTime.now());
        recipeRepository.save(recipeFromDb);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public List<Recipe> findRecipeByParam(String category, String name) {
        if (isCorrectRequest(category, name)) {
            if (category != null) {
                return recipeRepository.findByCategoryIgnoreCaseOrderByDateDesc(category);
            }
            if (name != null) {
                return recipeRepository.findByNameIgnoreCaseOrderByDateDesc(name);
            }
        }
        return new ArrayList<>();
    }

    public static boolean isCorrectRequest(String category, String name) {
        if ((category == null & name == null) | (category != null & name != null)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return true;
    }

}
