package com.nhoryzon.mc.farmersdelight.integration.emi.cutting;

import com.nhoryzon.mc.farmersdelight.FarmersDelightMod;
import com.nhoryzon.mc.farmersdelight.integration.emi.AbstractEmiFarmersRecipe;
import com.nhoryzon.mc.farmersdelight.integration.emi.FarmersDelightModEMI;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;

import java.util.List;

public class EmiCuttingRecipe extends AbstractEmiFarmersRecipe {
    private static final Identifier GUI_TEXTURE = new Identifier(FarmersDelightMod.MOD_ID, "textures/gui/emi/cutting_board.png");
    private final EmiIngredient tool;
    public EmiCuttingRecipe(final Identifier id, final List<Ingredient> inputs, final ItemStack outputs, final Ingredient tool)
    {
        super(FarmersDelightModEMI.CUTTING, id, inputs, outputs);
        this.tool = EmiIngredient.of(tool);
    }

    @Override
    public int getDisplayWidth() {
        return 108;
    }

    @Override
    public int getDisplayHeight() {
        return 44;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addTexture(GUI_TEXTURE, 0, 0, 39, 44, 7, 0);

        // EMI doesn't (yet) have a good way to draw blocks or items bigger than the default size.
        // Instead, embedded it into the texture.
        //widgets.addSlot(EmiStack.of(BlocksRegistry.CUTTING_BOARD.get()), 8, 28)
        // .drawBack(false);

        widgets.addGeneratedSlot((r) -> getRandomStack(r, tool), 0,9, 8).drawBack(false).catalyst(true);

        widgets.addGeneratedSlot((r) -> getRandomStack(r, EmiIngredient.of(this.getInputs())),0, 8, 22).drawBack(false);

        widgets.addFillingArrow(36, 12, 160);
        final List<EmiStack> outputs = this.getOutputs();

        for (int i = 0; i < outputs.size(); i++)
        {
            int x = i % 2 * 19;
            int y = i / 2 * 19;
            widgets.addSlot(outputs.get(i), x + 67, y + 9).recipeContext(this);
        }
    }
}
