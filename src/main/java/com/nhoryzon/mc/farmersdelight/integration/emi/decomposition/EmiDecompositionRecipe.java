package com.nhoryzon.mc.farmersdelight.integration.emi.decomposition;

import com.nhoryzon.mc.farmersdelight.FarmersDelightMod;
import com.nhoryzon.mc.farmersdelight.integration.emi.AbstractEmiFarmersRecipe;
import com.nhoryzon.mc.farmersdelight.integration.emi.FarmersDelightModEMI;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.block.Block;
import net.minecraft.client.gui.tooltip.OrderedTextTooltipComponent;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class EmiDecompositionRecipe extends AbstractEmiFarmersRecipe
{
    private static final Identifier GUI_TEXTURE = new Identifier(FarmersDelightMod.MOD_ID, "textures/gui/rei/decomposition.png");

    private final List<EmiStack> modifiers = new ArrayList<>();

    public EmiDecompositionRecipe(final Block input, final Block output, final List<Block> compostActivators)
    {
        super(FarmersDelightModEMI.DECOMPOSITION, List.of(input), output);

        for (final Block activator : compostActivators)
        {
            this.modifiers.add(EmiStack.of(activator));
        }
    }



    @Override
    public int getDisplayWidth() {
        return 102;
    }

    @Override
    public int getDisplayHeight() {
        return 62;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addTexture(GUI_TEXTURE, 0, 0, 102, 40, 7,8);

        widgets.addSlot(this.getInputs().get(0), 1, 17).drawBack(false);
        widgets.addSlot(this.getOutputs().get(0), 85, 17).drawBack(false).recipeContext(this);

        widgets.addGeneratedSlot((r) -> getRandomStack(r, EmiIngredient.of(modifiers)), 0, 55, 44);

        widgets.addDrawable(31, 30, 16, 13, (x, y, mx, my) -> {}).tooltip((mouseX, mouseY) ->
            List.of(new OrderedTextTooltipComponent(new TranslatableText(FarmersDelightMod.i18n("jei.decomposition.light").getString()).asOrderedText())));
        widgets.addDrawable(44, 30, 16, 13, (x, y, mx, my) -> {}).tooltip((mouseX, mouseY) ->
            List.of(new OrderedTextTooltipComponent(new TranslatableText(FarmersDelightMod.i18n("jei.decomposition.fluid").getString()).asOrderedText())));
        widgets.addDrawable(57, 30, 16, 13, (x, y, mx, my) -> {}).tooltip((mouseX, mouseY) ->
            List.of(new OrderedTextTooltipComponent(new TranslatableText(FarmersDelightMod.i18n("jei.decomposition.accelerators").getString()).asOrderedText())));
    }
}
