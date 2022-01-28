package ru.itmo.wp.model.repository;

import ru.itmo.wp.model.domain.Talk;

import java.util.List;
import java.util.Set;

/**
 * @author Pavel Lymar
 */
public interface TalkRepository {
    void save(Talk talk);
    Talk find(long id);
    List<Talk> getMessages(long sourceUserId, long targetUserId);
}
