package eu.wavecode.netty.data.event;

import eu.wavecode.netty.data.event.events.Event;
import eu.wavecode.netty.data.event.events.EventStoppable;
import eu.wavecode.netty.data.event.types.Priority;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * #  || Created ||
 * #      By   >> GamingLPyt
 * #      Date >> February 06, 2024
 * #      Time >> 18:12
 * #
 * #  || Project ||
 * #      Name >> WaveNettyPackets
 */

public class EventManager {

    private static final Map<Class<? extends Event>, List<MethodData>> registry = new HashMap<>();

    private EventManager() {
    }

    public static void register(final Object object) {
        for (final Method method : object.getClass().getDeclaredMethods()) {
            if (isMethodBad(method)) continue;

            register(method, object);
        }
    }

    public static void register(final Object object, final Class<? extends Event> eventClass) {
        for (final Method method : object.getClass().getDeclaredMethods()) {
            if (isMethodBad(method, eventClass)) continue;

            register(method, object);
        }
    }

    public static void unregister(final Object object) {
        for (final List<MethodData> dataList : registry.values())
            dataList.removeIf(data -> data.getSource().equals(object));

        cleanMap(true);
    }

    public static void unregister(final Object object, final Class<? extends Event> eventClass) {
        if (registry.containsKey(eventClass)) {
            registry.get(eventClass).removeIf(data -> data.getSource().equals(object));

            cleanMap(true);
        }
    }

    private static void register(final Method method, final Object object) {
        final Class<? extends Event> indexClass = (Class<? extends Event>) method.getParameterTypes()[0];
        final MethodData data = new MethodData(object, method, method.getAnnotation(EventTarget.class).value());

        if (!data.getTarget().isAccessible()) data.getTarget().setAccessible(true);

        if (registry.containsKey(indexClass)) {
            if (!registry.get(indexClass).contains(data)) {
                registry.get(indexClass).add(data);
                sortListValue(indexClass);
            }
        } else registry.put(indexClass, new CopyOnWriteArrayList<>() {
            private static final long serialVersionUID = 666L;

            {
                this.add(data);
            }
        });
    }

    public static void removeEntry(final Class<? extends Event> indexClass) {
        final Iterator<Map.Entry<Class<? extends Event>, List<MethodData>>> mapIterator = registry.entrySet().iterator();

        while (mapIterator.hasNext()) if (mapIterator.next().getKey().equals(indexClass)) {
            mapIterator.remove();
            break;
        }
    }

    public static void cleanMap(final boolean onlyEmptyEntries) {
        final Iterator<Map.Entry<Class<? extends Event>, List<MethodData>>> mapIterator = registry.entrySet().iterator();

        while (mapIterator.hasNext())
            if (!onlyEmptyEntries || mapIterator.next().getValue().isEmpty()) mapIterator.remove();
    }

    private static void sortListValue(final Class<? extends Event> indexClass) {
        final List<MethodData> sortedList = new CopyOnWriteArrayList<>();

        for (final byte priority : Priority.VALUE_ARRAY)
            for (final MethodData data : registry.get(indexClass))
                if (data.getPriority() == priority) sortedList.add(data);

        registry.put(indexClass, sortedList);
    }

    private static boolean isMethodBad(final Method method) {
        return method.getParameterTypes().length != 1 || !method.isAnnotationPresent(EventTarget.class);
    }

    private static boolean isMethodBad(final Method method, final Class<? extends Event> eventClass) {
        return isMethodBad(method) || !method.getParameterTypes()[0].equals(eventClass);
    }

    public static Event call(final Event event) {
        final List<MethodData> dataList = registry.get(event.getClass());

        if (dataList != null) if (event instanceof final EventStoppable stoppable)
            for (final MethodData data : dataList) {
                invoke(data, event);

                if (stoppable.isStopped()) break;
            }
        else for (final MethodData data : dataList) invoke(data, event);

        return event;
    }

    private static void invoke(final MethodData data, final Event argument) {
        try {
            data.getTarget().invoke(data.getSource(), argument);
        } catch (final IllegalAccessException | IllegalArgumentException | InvocationTargetException ignored) {
        }
    }

    private static final class MethodData {

        private final Object source;

        private final Method target;

        private final byte priority;

        public MethodData(final Object source, final Method target, final byte priority) {
            this.source = source;
            this.target = target;
            this.priority = priority;
        }

        public Object getSource() {
            return this.source;
        }

        public Method getTarget() {
            return this.target;
        }

        public byte getPriority() {
            return this.priority;
        }
    }
}
