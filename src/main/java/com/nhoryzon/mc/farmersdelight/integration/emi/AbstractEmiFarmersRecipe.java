package com.nhoryzon.mc.farmersdelight.integration.emi;

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class AbstractEmiFarmersRecipe implements EmiRecipe {
        final EmiRecipeCategory category;
        final Identifier id;
        final List<EmiIngredient> inputs;
        final List<EmiStack> outputs;
        protected AbstractEmiFarmersRecipe(EmiRecipeCategory category, Identifier id, List<Ingredient> inputs, ItemStack outputs)
        {
            this.category = category;
            this.id = id;
            this.inputs = new ArrayList<>();
            for(final Ingredient input : inputs)
            {
                this.inputs.add(EmiIngredient.of(input));
            }
            this.outputs = List.of(EmiStack.of(outputs));
        }

    protected AbstractEmiFarmersRecipe(EmiRecipeCategory category, List<Block> inputs, Block outputs)
    {
        this.category = category;
        this.id = null;
        this.inputs = new ArrayList<>();
        for(Block input : inputs)
        {
            this.inputs.add(EmiStack.of(input));
        }
        this.outputs = List.of(EmiStack.of(outputs));
    }

        @Override
        public EmiRecipeCategory getCategory() {
            return category;
        }

        @Override
        public @Nullable Identifier getId() {
            return id;
        }

        @Override
        public List<EmiIngredient> getInputs() {
            return inputs;
        }

        @Override
        public List<EmiStack> getOutputs() {
            return outputs;
        }

    public static EmiStack getRandomStack(Random r, EmiIngredient ingredient)
    {
        if(ingredient.getEmiStacks().size() > 0)
        {
            return ingredient.getEmiStacks().get(r.nextInt(ingredient.getEmiStacks().size()));
        }
        else
        {
            // Someone could empty a tag completely after registering recipes involving that tag.
            return EmiStack.EMPTY;
        }
    }
}
