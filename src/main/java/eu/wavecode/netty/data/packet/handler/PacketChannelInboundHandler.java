package eu.wavecode.netty.data.packet.handler;

import eu.wavecode.netty.data.event.EventManager;
import eu.wavecode.netty.data.event.events.callable.PacketReceivedEvent;
import eu.wavecode.netty.data.packet.Packet;
import io.netty5.channel.ChannelHandlerContext;
import io.netty5.channel.SimpleChannelInboundHandler;

/**
 * #  || Created ||
 * #      By   >> GamingLPyt
 * #      Date >> February 06, 2024
 * #      Time >> 18:26
 * #
 * #  || Project ||
 * #      Name >> WaveNettyPackets
 */
public class PacketChannelInboundHandler extends SimpleChannelInboundHandler<Packet> {

    @Override
    protected void messageReceived(final ChannelHandlerContext ctx, final Packet msg) throws Exception {
        EventManager.call(new PacketReceivedEvent(msg, ctx));
    }
}
