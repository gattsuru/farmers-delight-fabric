package com.nhoryzon.mc.farmersdelight.integration.emi.cooking;

import com.nhoryzon.mc.farmersdelight.FarmersDelightMod;
import com.nhoryzon.mc.farmersdelight.integration.emi.AbstractEmiFarmersRecipe;
import com.nhoryzon.mc.farmersdelight.integration.emi.FarmersDelightModEMI;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.List;

public class EmiCookingRecipe extends AbstractEmiFarmersRecipe {
    private static final Identifier GUI_TEXTURE = new Identifier(FarmersDelightMod.MOD_ID, "textures/gui/cooking_pot.png");

    final EmiStack container;
    final int cookTime;
    public EmiCookingRecipe(final Identifier id, final List<Ingredient> inputs, final ItemStack outputs, final ItemStack container, final int cookTime)
    {
        super(FarmersDelightModEMI.COOKING, id, inputs, outputs);
        this.container = EmiStack.of(container);
        this.cookTime = cookTime;
    }

    @Override
    public int getDisplayWidth() {
        return 116;
    }

    @Override
    public int getDisplayHeight() {
        return 56;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addTexture(GUI_TEXTURE, 0, 0, 116, 56, 29, 16, 118, 58, 256, 256);
        List<EmiIngredient> ingredientEntries = this.getInputs();
        if(ingredientEntries != null && !ingredientEntries.isEmpty())
        {
            for(int i = 0; i < ingredientEntries.size(); i++)
            {
                widgets.addSlot(ingredientEntries.get(i), -1 + (i % 3 * 18), (i / 3) * 18);
            }
        }
        widgets.addSlot(container, 57, 33).output(true).drawBack(false);
        widgets.addSlot(getOutputs().get(0), 89, 8).output(true).drawBack(false);
        widgets.addSlot(getOutputs().get(0), 89, 33).output(true).drawBack(false).recipeContext(this);

        widgets.addTexture(GUI_TEXTURE, 18, 39, 17, 15, 176, 0);

        widgets.addFillingArrow(58, 9, this.cookTime * 20);

        widgets.addText(new LiteralText(this.cookTime + " t").asOrderedText(),60, 2, Formatting.DARK_GRAY.getColorValue(), false);
    }
}
