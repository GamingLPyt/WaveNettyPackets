package eu.wavecode.netty.data.event.events;

/**
 * #  || Created ||
 * #      By   >> GamingLPyt
 * #      Date >> February 06, 2024
 * #      Time >> 18:12
 * #
 * #  || Project ||
 * #      Name >> WaveNettyPackets
 */
public abstract class EventStoppable implements Event {

    private boolean stopped;

    protected EventStoppable() {
    }

    public void stop() {
        this.stopped = true;
    }

    public boolean isStopped() {
        return this.stopped;
    }
}
