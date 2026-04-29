package com.service;

import com.google.gson.*;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    @Value("${groq.api.key}")
    private String groqKey;

    private final OkHttpClient client = new OkHttpClient();

    public String askOpenAI(String userMessage, String businessContext) throws Exception {

        String prompt = "Sos el asistente virtual de: " + businessContext +
                ". Respondé únicamente preguntas relacionadas al negocio de forma amable y concisa. " +
                "Pregunta del cliente: " + userMessage;

        JsonObject message = new JsonObject();
        message.addProperty("role", "user");
        message.addProperty("content", prompt);

        JsonArray messages = new JsonArray();
        messages.add(message);

        JsonObject body = new JsonObject();
        body.addProperty("model", "llama-3.3-70b-versatile");
        body.addProperty("max_tokens", 300);
        body.add("messages", messages);

        Request request = new Request.Builder()
                .url("https://api.groq.com/openai/v1/chat/completions")
                .addHeader("Authorization", "Bearer " + groqKey)
                .addHeader("Content-Type", "application/json")
                .post(RequestBody.create(body.toString(), MediaType.get("application/json")))
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new Exception("Error Groq: " + response.code() + " " + response.body().string());
            }
            String responseBody = response.body().string();
            JsonObject json = JsonParser.parseString(responseBody).getAsJsonObject();
            return json.getAsJsonArray("choices")
                    .get(0).getAsJsonObject()
                    .getAsJsonObject("message")
                    .get("content").getAsString().trim();
        }
    }
}