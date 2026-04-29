package com.controller;


import com.model.Conversation;
import com.service.ChatService;
import com.service.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "*")
public class ChatController {

    @Autowired private ChatService chatService;
    @Autowired private ConversationService conversationService;

    private static final String BUSINESS_CONTEXT =
            "Restaurante La Esquina. " +
                    "Horario: lunes a sábado de 12:00 a 23:00hs. " +
                    "Menú: pizza $8000, pasta $7500, ensalada $5000, bebidas desde $1500. " +
                    "Dirección: Av. Corrientes 1234, Buenos Aires. " +
                    "Reservas por teléfono: 011-1234-5678.";

    @PostMapping
    public ResponseEntity<?> chat(@RequestBody Map<String, String> body) {
        String message = body.get("message");
        String sessionId = body.getOrDefault("sessionId", "default");

        if (message == null || message.isBlank()) {
            return ResponseEntity.badRequest().body("El mensaje no puede estar vacío.");
        }

        try {
            String response = chatService.askOpenAI(message, BUSINESS_CONTEXT);
            conversationService.saveConversation(sessionId, message, response);
            return ResponseEntity.ok(Map.of("response", response));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/history/{sessionId}")
    public ResponseEntity<List<Conversation>> getHistory(@PathVariable String sessionId) {
        return ResponseEntity.ok(conversationService.getHistory(sessionId));
    }
}