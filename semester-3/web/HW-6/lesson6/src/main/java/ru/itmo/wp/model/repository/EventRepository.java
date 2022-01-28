package ru.itmo.wp.model.repository;

import ru.itmo.wp.model.domain.Event;

/**
 * @author Pavel Lymar
 */
public interface EventRepository {
    Event find(long id);
    void save(Event event);
}
