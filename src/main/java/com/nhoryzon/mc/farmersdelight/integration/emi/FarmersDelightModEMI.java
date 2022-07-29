package com.nhoryzon.mc.farmersdelight.integration.emi;

import com.nhoryzon.mc.farmersdelight.FarmersDelightMod;
import com.nhoryzon.mc.farmersdelight.integration.emi.cooking.EmiCookingRecipe;
import com.nhoryzon.mc.farmersdelight.integration.emi.cutting.EmiCuttingRecipe;
import com.nhoryzon.mc.farmersdelight.integration.emi.decomposition.EmiDecompositionRecipe;
import com.nhoryzon.mc.farmersdelight.recipe.CookingPotRecipe;
import com.nhoryzon.mc.farmersdelight.recipe.CuttingBoardRecipe;
import com.nhoryzon.mc.farmersdelight.registry.BlocksRegistry;
import com.nhoryzon.mc.farmersdelight.tag.Tags;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiStack;
import net.minecraft.inventory.Inventory;
import net.minecraft.recipe.Recipe;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryEntryList;

import static com.nhoryzon.mc.farmersdelight.registry.RecipeTypesRegistry.COOKING_RECIPE_SERIALIZER;
import static com.nhoryzon.mc.farmersdelight.registry.RecipeTypesRegistry.CUTTING_RECIPE_SERIALIZER;

public class FarmersDelightModEMI implements EmiPlugin{

    private static final Identifier SIMPLIFIED_ICON_COOKING = new Identifier(FarmersDelightMod.MOD_ID, "textures/gui/emi/simple_cooking.png");
    private static final Identifier SIMPLIFIED_ICON_CUTTING = new Identifier(FarmersDelightMod.MOD_ID, "textures/gui/emi/simple_cutting.png");
    private static final Identifier SIMPLIFIED_ICON_DECOMPOSITION = new Identifier(FarmersDelightMod.MOD_ID, "textures/gui/emi/simple_decomposition.png");

    public static final EmiRecipeCategory COOKING = new EmiRecipeCategory(new Identifier(FarmersDelightMod.MOD_ID, "cooking"),
            EmiStack.of(BlocksRegistry.COOKING_POT.get()),
            new EmiTexture(SIMPLIFIED_ICON_COOKING, 0, 0, 16, 16, 16, 16, 16, 16));

    public static final EmiRecipeCategory CUTTING = new EmiRecipeCategory(new Identifier(FarmersDelightMod.MOD_ID, "cutting"),
            EmiStack.of(BlocksRegistry.CUTTING_BOARD.get()),
            new EmiTexture(SIMPLIFIED_ICON_CUTTING, 0, 0, 16, 16, 16, 16, 16, 16));

    public static final EmiRecipeCategory DECOMPOSITION = new EmiRecipeCategory(new Identifier(FarmersDelightMod.MOD_ID, "decomposition"),
            EmiStack.of(BlocksRegistry.RICH_SOIL.get()),
            new EmiTexture(SIMPLIFIED_ICON_DECOMPOSITION, 0, 0, 16, 16, 16, 16, 16, 16));

    @Override
    public void register(EmiRegistry registry)
    {
        registry.addCategory(COOKING);
        registry.addCategory(CUTTING);
        registry.addCategory(DECOMPOSITION);

        registry.addWorkstation(COOKING, EmiStack.of(BlocksRegistry.COOKING_POT.get()));
        registry.addWorkstation(CUTTING, EmiStack.of(BlocksRegistry.CUTTING_BOARD.get()));
        registry.addWorkstation(DECOMPOSITION, EmiStack.of(BlocksRegistry.RICH_SOIL.get()));

        for (Recipe<Inventory> recipe : registry.getRecipeManager().listAllOfType(COOKING_RECIPE_SERIALIZER.type()))
        {
            if (recipe instanceof CookingPotRecipe)
            {
                registry.addRecipe(new EmiCookingRecipe(recipe.getId(),
                  recipe.getIngredients(),
                  recipe.getOutput(),
                  ((CookingPotRecipe) recipe).getContainer(),
                  ((CookingPotRecipe) recipe).getCookTime()));
            }
        }
        for (Recipe<Inventory> recipe : registry.getRecipeManager().listAllOfType(CUTTING_RECIPE_SERIALIZER.type()))
        {
            registry.addRecipe(new EmiCuttingRecipe(recipe.getId(), recipe.getIngredients(), recipe.getOutput(), ((CuttingBoardRecipe)recipe).getTool()));
        }

        registry.addRecipe(new EmiDecompositionRecipe(BlocksRegistry.ORGANIC_COMPOST.get(),
          BlocksRegistry.RICH_SOIL.get(),
          Registry.BLOCK.getEntryList(Tags.COMPOST_ACTIVATORS).stream().flatMap(RegistryEntryList::stream).map(RegistryEntry::value).toList()
        ));
    }
}
