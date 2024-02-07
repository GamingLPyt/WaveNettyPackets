package eu.wavecode.netty.data.event;

import eu.wavecode.netty.data.event.types.Priority;

import java.lang.annotation.*;

/**
 * #  || Created ||
 * #      By   >> GamingLPyt
 * #      Date >> February 06, 2024
 * #      Time >> 18:12
 * #
 * #  || Project ||
 * #      Name >> WaveNettyPackets
 */

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventTarget {

    byte value() default Priority.MEDIUM;
}
