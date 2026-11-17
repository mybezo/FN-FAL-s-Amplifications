package ne.fnfal113.fnamplifications.integrations;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;

import ne.fnfal113.fnamplifications.FNAmplifications;

/**
 * Optional recipe integrations with other slimefun addons.
 * Items are looked up by id at runtime so no compile time dependency is needed,
 * if the addon is missing, disabled or the config toggle is off the default recipe item is used.
 */
public final class RecipeIntegration {

    private RecipeIntegration() {}

    /**
     * LiteXpansion integration (config: LiteX-Recipe-Integration), replaces PowerXpansion
     * generator ingots with LiteXpansion's iridium
     *
     * @param fallback the default recipe item
     * @param fallbackAmount the default recipe item amount
     * @param iridiumAmount the iridium amount used when the integration is active
     * @return the recipe item to use
     */
    @Nonnull
    public static ItemStack liteXpansion(@Nonnull ItemStack fallback, int fallbackAmount, int iridiumAmount) {
        if (isEnabled("LiteXpansion", "LiteX-Recipe-Integration")) {
            ItemStack iridium = getItem("IRIDIUM");

            if (iridium != null) {
                return new CustomItemStack(iridium, iridiumAmount);
            }
        }

        return new CustomItemStack(fallback, fallbackAmount);
    }

    /**
     * SimpleMaterialGenerators integration (config: SMG-Recipe-Integration), replaces some
     * material generator recipe items with SMG generators
     *
     * @param smgId the SMG generator id (e.g. SMG_GENERATOR_COBBLESTONE)
     * @param fallback the default recipe item
     * @return the recipe item to use
     */
    @Nonnull
    public static ItemStack simpleMaterialGenerators(@Nonnull String smgId, @Nonnull ItemStack fallback) {
        if (isEnabled("SimpleMaterialGenerators", "SMG-Recipe-Integration")) {
            ItemStack item = getItem(smgId);

            if (item != null) {
                return item;
            }
        }

        return fallback.clone();
    }

    private static boolean isEnabled(@Nonnull String pluginName, @Nonnull String configKey) {
        return Bukkit.getPluginManager().isPluginEnabled(pluginName)
            && FNAmplifications.getInstance().getConfig().getBoolean(configKey, true);
    }

    @Nullable
    private static ItemStack getItem(@Nonnull String id) {
        SlimefunItem item = SlimefunItem.getById(id);

        if (item == null || item.isDisabled()) {
            return null;
        }

        return item.getItem().clone();
    }

}
