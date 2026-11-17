package ne.fnfal113.fnamplifications.utils.compatibility;

import java.util.Collections;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;

/**
 * Tags that do not exist on every supported version.
 * The "tall_flowers" block tag was removed in Minecraft 1.21.5.
 */
public final class VersionedTag {

    @Nullable
    public static final Tag<Material> TALL_FLOWERS = getBlockTag("tall_flowers");

    private VersionedTag() {}

    @Nullable
    public static Tag<Material> getBlockTag(@Nonnull String key) {
        try {
            return Bukkit.getTag(Tag.REGISTRY_BLOCKS, NamespacedKey.minecraft(key), Material.class);
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean isTagged(@Nullable Tag<Material> tag, @Nonnull Material material) {
        return tag != null && tag.isTagged(material);
    }

    @Nonnull
    public static Set<Material> getValues(@Nullable Tag<Material> tag) {
        return tag != null ? tag.getValues() : Collections.emptySet();
    }

}
