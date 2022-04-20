package recipes;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe,Long> {
    List<Recipe> findRecipesByName(String name);
    List<Recipe> findRecipesByCategory(String category);
}
