package com.service;

import com.model.Conversation;
import com.repository.ConversationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ConversationService {

    @Autowired
    private ConversationRepository repository;

    public void saveConversation(String sessionId, String userMessage, String botResponse) {
        Conversation conv = new Conversation();
        conv.setSessionId(sessionId);
        conv.setUserMessage(userMessage);
        conv.setBotResponse(botResponse);
        repository.save(conv);
    }

    public List<Conversation> getHistory(String sessionId) {
        return repository.findBySessionIdOrderByCreatedAtAsc(sessionId);
    }
}