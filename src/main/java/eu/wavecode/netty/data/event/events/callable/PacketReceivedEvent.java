package eu.wavecode.netty.data.event.events.callable;

import eu.wavecode.netty.data.event.events.Event;
import eu.wavecode.netty.data.packet.Packet;
import io.netty5.channel.ChannelHandlerContext;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * #  || Created ||
 * #      By   >> GamingLPyt
 * #      Date >> February 06, 2024
 * #      Time >> 18:28
 * #
 * #  || Project ||
 * #      Name >> WaveNettyPackets
 */

@Data
@AllArgsConstructor
public class PacketReceivedEvent implements Event {

    private final Packet packet;
    private final ChannelHandlerContext channelHandlerContext;
}
