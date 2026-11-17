package ne.fnfal113.fnamplifications.machines.implementation;

import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;

import javax.annotation.Nonnull;

import org.bukkit.Material;

public enum DiscDurationsEnum {

    MUSIC_DISC_13(178),
    MUSIC_DISC_CAT(185),
    MUSIC_DISC_BLOCKS(345),
    MUSIC_DISC_CHIRP(185),
    MUSIC_DISC_FAR(174),
    MUSIC_DISC_MALL(197),
    MUSIC_DISC_MELLOHI(96),
    MUSIC_DISC_STAL(150),
    MUSIC_DISC_STRAD(188),
    MUSIC_DISC_WARD(251),
    MUSIC_DISC_11(71),
    MUSIC_DISC_WAIT(238),
    MUSIC_DISC_OTHERSIDE(195),
    MUSIC_DISC_PIGSTEP(149),
    MUSIC_DISC_5(178),
    MUSIC_DISC_RELIC(218),
    // 1.21
    MUSIC_DISC_CREATOR(176),
    MUSIC_DISC_CREATOR_MUSIC_BOX(73),
    MUSIC_DISC_PRECIPICE(299),
    // 1.21.6+
    MUSIC_DISC_TEARS(175),
    MUSIC_DISC_LAVA_CHICKEN(134),
    ;

    /**
     * Fallback duration in seconds for discs that are not known yet (newer versions or custom discs)
     */
    private static final int DEFAULT_DURATION_SECONDS = 180;

    private final int duration;

    DiscDurationsEnum(int durationInSeconds) {
        this.duration = toTicks(durationInSeconds);
    }

    public int getDuration() {
        return duration;
    }

    /**
     * Get the duration of the given music disc in slimefun ticks, never throws
     * for unknown discs unlike {@link #valueOf(String)}
     *
     * @param disc the music disc material
     * @return the disc duration in slimefun ticks
     */
    public static int getDuration(@Nonnull Material disc) {
        for (DiscDurationsEnum value : values()) {
            if (value.name().equals(disc.name())) {
                return value.getDuration();
            }
        }

        return toTicks(DEFAULT_DURATION_SECONDS);
    }

    private static int toTicks(int durationInSeconds) {
        return Math.max(1, durationInSeconds * 20 / Slimefun.getTickerTask().getTickRate());
    }

}
