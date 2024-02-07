package eu.wavecode.netty.test;

import eu.wavecode.netty.data.client.SimpleNettyClient;
import eu.wavecode.netty.test.packets.TestPacket;
import io.netty5.channel.Channel;

/**
 * #  || Created ||
 * #      By   >> GamingLPyt
 * #      Date >> February 06, 2024
 * #      Time >> 18:53
 * #
 * #  || Project ||
 * #      Name >> WaveNettyPackets
 */
public class Thread implements Runnable {

    private final SimpleNettyClient simpleNettyClient;

    public Thread(final SimpleNettyClient simpleNettyClient) {
        this.simpleNettyClient = simpleNettyClient;
        new java.lang.Thread(this).start();
    }

    @Override
    public void run() {
        int i = 0;


        Channel channel = this.simpleNettyClient.getChannel();
        final TestPacket testPacket = new TestPacket();
        testPacket.setMessage("Hello, World!");

        while (true)
            if (channel != null) {
                if (i <= 10) {
                    this.simpleNettyClient.write(testPacket);
                    i++;
                } else break;

                try {
                    java.lang.Thread.sleep(1000);
                } catch (final InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Channel is null or not active");
                channel = this.simpleNettyClient.getChannel();
            }
    }
}
