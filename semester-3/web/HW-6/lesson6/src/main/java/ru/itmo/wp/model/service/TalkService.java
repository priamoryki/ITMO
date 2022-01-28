package ru.itmo.wp.model.service;

import ru.itmo.wp.model.domain.Talk;
import ru.itmo.wp.model.repository.TalkRepository;
import ru.itmo.wp.model.repository.impl.TalkRepositoryImpl;

import java.util.List;

/**
 * @author Pavel Lymar
 */
public class TalkService {
    private final TalkRepository talkRepository = new TalkRepositoryImpl();

    public void saveMessage(long sourceUserId, long targetUserId, String message) {
        Talk talk = new Talk();
        talk.setSourceUserId(sourceUserId);
        talk.setTargetUserId(targetUserId);
        talk.setText(message);
        talkRepository.save(talk);
    }

    public List<Talk> getMessages(long sourceUserId, long targetUserId) {
        return talkRepository.getMessages(sourceUserId, targetUserId);
    }
}
