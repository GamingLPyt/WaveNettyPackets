package eu.wavecode.netty.data.event.events.callable;

import eu.wavecode.netty.data.event.events.Cancellable;
import eu.wavecode.netty.data.event.events.Event;

/**
 * #  || Created ||
 * #      By   >> GamingLPyt
 * #      Date >> February 06, 2024
 * #      Time >> 18:13
 * #
 * #  || Project ||
 * #      Name >> WaveNettyPackets
 */
public abstract class EventCancellable implements Event, Cancellable {

    private boolean cancelled;

    protected EventCancellable() {
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(final boolean state) {
        this.cancelled = state;
    }
}
