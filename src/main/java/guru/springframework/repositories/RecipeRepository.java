package guru.springframework.repositories;

import guru.springframework.domain.Recipe;
import org.springframework.data.repository.CrudRepository;
                                                        //<entity type, id type>
public interface RecipeRepository extends CrudRepository<Recipe, Long> {
}
