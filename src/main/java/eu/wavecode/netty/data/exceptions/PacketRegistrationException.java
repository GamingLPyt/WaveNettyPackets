package eu.wavecode.netty.data.exceptions;

/**
 * #  || Created ||
 * #      By   >> GamingLPyt
 * #      Date >> February 06, 2024
 * #      Time >> 18:03
 * #
 * #  || Project ||
 * #      Name >> WaveNettyPackets
 */
public class PacketRegistrationException extends Exception {

    public PacketRegistrationException(final String message) {
        super(message);
    }

    public PacketRegistrationException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
