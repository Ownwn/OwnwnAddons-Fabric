package com.ownwn.event;

import com.google.common.eventbus.Subscribe;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventBus {
    private List<Object> listeners = new ArrayList<>();
    private Map<Object, List<Object>> cancelledEvents = new HashMap<>();

    public void register(Object listener) {
        listeners.add(listener);
    }

    public void post(Object event) {
        List<Object> cancelledBy = cancelledEvents.get(event);
        for (Object listener : listeners) {
            // Check if the listener has a method that can handle this event
            for (Method method : listener.getClass().getMethods()) {
                if (method.isAnnotationPresent(Subscribe.class) && method.getParameterCount() == 1 && method.getParameterTypes()[0].isAssignableFrom(event.getClass())) {
                    if (cancelledBy == null || !cancelledBy.contains(listener)) {
                        try {
                            // Invoke the listener method with the event
                            method.invoke(listener, event);
                        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    public void cancel(Object event, Object listener) {
        List<Object> cancelledBy = cancelledEvents.computeIfAbsent(event, k -> new ArrayList<>());
        cancelledBy.add(listener);
    }
}
