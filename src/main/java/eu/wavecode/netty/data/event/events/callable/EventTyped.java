package eu.wavecode.netty.data.event.events.callable;

import eu.wavecode.netty.data.event.events.Event;
import eu.wavecode.netty.data.event.events.Typed;

/**
 * #  || Created ||
 * #      By   >> GamingLPyt
 * #      Date >> February 06, 2024
 * #      Time >> 18:13
 * #
 * #  || Project ||
 * #      Name >> WaveNettyPackets
 */
public abstract class EventTyped implements Event, Typed {

    private final byte type;

    protected EventTyped(final byte eventType) {
        this.type = eventType;
    }

    @Override
    public byte getType() {
        return this.type;
    }
}
