package ne.fnfal113.fnamplifications.utils.compatibility;

import java.util.Locale;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.attribute.Attribute;

/**
 * Resolves {@link Attribute}s through the registry so the plugin works on every
 * version from 1.21 up to 26.x. Minecraft 1.21.3 dropped the "generic." prefix
 * (e.g. GENERIC_MAX_HEALTH became MAX_HEALTH) and newer API versions removed the
 * old constants entirely, so direct field access is not safe.
 */
public final class VersionedAttribute {

    public static final Attribute MAX_HEALTH = getOrThrow("max_health");
    public static final Attribute MOVEMENT_SPEED = getOrThrow("movement_speed");
    public static final Attribute ATTACK_DAMAGE = getOrThrow("attack_damage");

    private VersionedAttribute() {}

    /**
     * Get an attribute by name, accepts both legacy names (GENERIC_ARMOR, generic.armor)
     * and modern names (ARMOR, armor, minecraft:armor)
     *
     * @param name the attribute name
     * @return the attribute or null if it does not exist on this server version
     */
    @Nullable
    public static Attribute get(@Nonnull String name) {
        String key = name.trim().toLowerCase(Locale.ROOT);

        if (key.startsWith("minecraft:")) {
            key = key.substring("minecraft:".length());
        }

        if (key.startsWith("generic_") || key.startsWith("generic.")) {
            key = key.substring("generic_".length());
        }

        Attribute attribute = lookup(key);

        if (attribute == null) {
            // pre 1.21.3 keys
            attribute = lookup("generic." + key);
        }

        return attribute;
    }

    @Nullable
    private static Attribute lookup(@Nonnull String key) {
        try {
            return Registry.ATTRIBUTE.get(NamespacedKey.minecraft(key));
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    @Nonnull
    private static Attribute getOrThrow(@Nonnull String name) {
        Attribute attribute = get(name);

        if (attribute == null) {
            throw new IllegalStateException("Could not resolve attribute " + name);
        }

        return attribute;
    }

}
