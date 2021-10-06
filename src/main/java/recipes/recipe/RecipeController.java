package recipes.recipe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@Validated
public class RecipeController {
    RecipeService recipeService;

    @Autowired
    RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping("api/recipe/new")
    Map<String, Integer> newRecipe(@Valid @RequestBody Recipe recipe,
                                   Principal principal) {
        recipe.setAuthor(principal.getName());
        recipeService.save(recipe);
        return Collections.singletonMap("id", recipe.getId());
    }

    @GetMapping("/api/recipe/{id}")
    Recipe getRecipe(@PathVariable int id) {
        Recipe recipe = recipeService.findRecipeById(id);
        if (recipe == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return recipe;
    }

    @DeleteMapping("/api/recipe/{id}")
    public ResponseEntity deleteRecipe(@PathVariable int id,
                                       Principal principal) {
        Recipe recipe = recipeService.findRecipeById(id);
        if ( recipe == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else if (!recipe.getAuthor().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        recipeService.deleteRecipeById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/api/recipe/{id}")
    public ResponseEntity updateRecipe(@PathVariable int id,
                                       @Valid @RequestBody Recipe recipe,
                                       Principal principal) {
        Recipe oldRecipe = recipeService.findRecipeById(id);
        if (oldRecipe == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else if (!oldRecipe.getAuthor().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        recipe.setId(id);
        recipe.setAuthor(oldRecipe.getAuthor());
        recipeService.save(recipe);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/api/recipe/search/")
    public List<Recipe> searchByCategory(@RequestParam(required = false) String category,
                                         @RequestParam(required = false) String name) {
        if (category != null) return recipeService.findRecipesByCategory(category);
        if (name != null) return recipeService.findRecipesByName(name);
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
}
