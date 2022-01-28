package ru.itmo.wp.model.service;

import ru.itmo.wp.model.domain.Event;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.repository.EventRepository;
import ru.itmo.wp.model.repository.impl.EventRepositoryImpl;

/**
 * @author Pavel Lymar
 */
public class EventService {
    private final EventRepository eventRepository = new EventRepositoryImpl();

    public void save(Event.Type type, User user) {
        Event event = new Event();
        event.setType(type);
        event.setUserId(user.getId());
        eventRepository.save(event);
    }
}
