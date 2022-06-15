package recipes.servicies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import recipes.entites.Recipe;
import recipes.repositories.RecipeRepository;
import recipes.repositories.UserRepository;
import recipes.securityconfig.IAuthenticationFacade;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class RecipeService {

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;

    public RecipeService(RecipeRepository recipeRepository, UserRepository userRepository) {
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
    }

    public Recipe findRecipeById(Long id) {
        return recipeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public String addNewRecipe(Recipe recipe) {
        Authentication authentication = authenticationFacade.getAuthentication();
        recipe.setAuthor(authentication.getName());
        recipe.setDate(LocalDateTime.now());
        recipeRepository.save(recipe);
        return "{\"id\": " + recipe.getId() + "}";
    }

    public ResponseEntity deleteRecipeById(Long id) {
        if (recipeRepository.findById(id).isPresent()) {
            Authentication authentication = authenticationFacade.getAuthentication();
            if (recipeRepository.findById(id).get().getAuthor().equals(authentication.getName())) {
                recipeRepository.deleteById(id);
            } else return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity updateRecipeById(Long id, Recipe recipe) {
        Recipe recipeFromDb = recipeRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Authentication authentication = authenticationFacade.getAuthentication();
        if (recipeFromDb.getAuthor().equals(authentication.getName())) {
            recipeFromDb = recipe;
            recipeFromDb.setAuthor(authentication.getName());
            recipeFromDb.setId(id);
            recipeFromDb.setDate(LocalDateTime.now());
            recipeRepository.save(recipeFromDb);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else return new ResponseEntity<>(HttpStatus.FORBIDDEN);

    }

    public List<Recipe> findRecipeByParam(String category, String name) {
        if (isCorrectRequest(category, name)) {
            if (category != null) {
                return recipeRepository.findByCategoryIgnoreCaseOrderByDateDesc(category);
            }
            if (name != null) {
                return recipeRepository.findByNameIgnoreCaseContainsOrderByDateDesc(name);
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
