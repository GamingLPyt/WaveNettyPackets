package eu.wavecode.netty.data.event.types;

/**
 * #  || Created ||
 * #      By   >> GamingLPyt
 * #      Date >> February 06, 2024
 * #      Time >> 18:12
 * #
 * #  || Project ||
 * #      Name >> WaveNettyPackets
 */
public class Priority {

    public static final byte
            HIGHEST = 0,
            HIGH = 1,
            MEDIUM = 2,
            LOW = 3,
            LOWEST = 4;

    public static final byte[] VALUE_ARRAY;

    static {
        VALUE_ARRAY = new byte[]{
                HIGHEST,
                HIGH,
                MEDIUM,
                LOW,
                LOWEST
        };
    }
}
