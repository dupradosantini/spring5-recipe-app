package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService{
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final RecipeRepository recipeRepository;

    public IngredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand, RecipeRepository recipeRepository){
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId){
        Optional<Recipe> recipeOptional= recipeRepository.findById(recipeId);
        //check id not found
        if(recipeOptional.isPresent()){
            //impl error handling
            log.error("recipe id not found for ID: "+recipeId);
        }
        Recipe recipe = recipeOptional.get();
        //using java stream to search for the id that was passed
        Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId)) //filter based on ingredent id = passed id
                .map(ingredient -> ingredientToIngredientCommand.convert(ingredient)).findFirst();//map it over to an ingredient command object
        //check id not found
        if(!ingredientCommandOptional.isPresent()){
            //impl error handling
            log.error("Ingredient id not found: "+ ingredientId);
        }
        return ingredientCommandOptional.get();

    }

}
