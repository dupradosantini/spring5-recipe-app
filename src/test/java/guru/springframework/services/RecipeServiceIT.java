package guru.springframework.services;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.converters.RecipeCommandToRecipe;
import guru.springframework.converters.RecipeToRecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class RecipeServiceIT {

    public static final String NEW_DESCRIPTION = "New Description";

    @Autowired
    RecipeService recipeService;

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    RecipeCommandToRecipe recipeCommandToRecipe;

    @Autowired
    RecipeToRecipeCommand recipeToRecipeCommand;

    @Transactional //Required because we are working with lazily loaded properties
    @Test
    public void testSaveOfDescription() throws Exception{
        //given
        Iterable<Recipe> recipes = recipeRepository.findAll();//grabs all the data initialized
        Recipe testRecipe = recipes.iterator().next();//gets the first entity
        RecipeCommand testRecipeCommand = recipeToRecipeCommand.convert(testRecipe);//convert to command object

        //when
        testRecipeCommand.setDescription(NEW_DESCRIPTION);//changing description
        RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(testRecipeCommand);//saving the commandObj

        //then
        assertEquals(NEW_DESCRIPTION, savedRecipeCommand.getDescription());//asserting the saved object indeed has the values we expect
        assertEquals(testRecipe.getId(),savedRecipeCommand.getId());        //  (that we defined before)
        assertEquals(testRecipe.getCategories().size(),savedRecipeCommand.getCategories().size());
        assertEquals(testRecipe.getIngredients().size(), savedRecipeCommand.getIngredients().size());

    }

}