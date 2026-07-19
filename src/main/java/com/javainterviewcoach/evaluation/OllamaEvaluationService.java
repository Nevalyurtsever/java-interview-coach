package com.javainterviewcoach.evaluation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javainterviewcoach.exception.EvaluationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.Map;

@Service
public class OllamaEvaluationService {
    private final ObjectMapper objectMapper;
    private final HttpClient httpClient;
    private final URI generateUri;
    private final String model;

    public OllamaEvaluationService(ObjectMapper objectMapper,
                                   @Value("${ollama.base-url:http://localhost:11434}") String baseUrl,
                                   @Value("${ollama.model:qwen2.5:3b}") String model) {
        this.objectMapper = objectMapper;
        this.model = model;
        this.generateUri = URI.create(baseUrl.replaceAll("/+$", "") + "/api/generate");
        this.httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(5)).build();
    }

    public EvaluationResponse evaluate(String question, String sampleAnswer, String explanation, String userAnswer) {
        return evaluate(question, sampleAnswer, explanation, userAnswer, "tr");
    }

    public EvaluationResponse evaluate(String question, String sampleAnswer, String explanation,
                                       String userAnswer, String language) {
        boolean english = "en".equalsIgnoreCase(language);
        if (isClearlyInsufficient(userAnswer)) {
            return new EvaluationResponse(0,
                    english ? "The answer is too short or unclear for a technical evaluation."
                            : "Cevap, teknik bir değerlendirme yapabilmek için çok kısa veya anlamsız.",
                    List.of(),
                    List.of(english ? "There is no relevant technical explanation."
                            : "Soruyla ilgili teknik bir açıklama bulunmuyor."),
                    List.of(english ? "The core concepts requested by the question are missing."
                            : "Soruda istenen temel kavramların açıklaması eksik."),
                    sampleAnswer);
        }

        try {
            Map<String, Object> schema = Map.of(
                    "type", "object",
                    "properties", Map.of(
                            "score", Map.of("type", "integer"),
                            "summary", Map.of("type", "string"),
                            "correctPoints", Map.of("type", "array", "items", Map.of("type", "string")),
                            "incorrectPoints", Map.of("type", "array", "items", Map.of("type", "string")),
                            "missingPoints", Map.of("type", "array", "items", Map.of("type", "string")),
                            "improvedAnswer", Map.of("type", "string")),
                    "required", new String[]{"score", "summary", "correctPoints", "incorrectPoints", "missingPoints", "improvedAnswer"});

            String system = english ? """
                    You are a Java interview coach. Write all feedback in clear English. Evaluate technical
                    accuracy, coverage, and clarity. Be encouraging but honest. Never credit information the
                    candidate did not provide. Give 0 for irrelevant or meaningless answers and no more than
                    20 for answers with very little correct information. Score technical merit, not effort.
                    Return only the requested JSON structure.
                    """ : """
                    Sen Türkçe konuşan bir Java mülakat koçusun. Tüm geri bildirimi Türkçe yaz; yalnızca
                    yerleşik teknik terimleri gerektiğinde İngilizce kullan. Teknik doğruluk, kapsam ve
                    açıklığı değerlendir. Cesaretlendirici fakat dürüst ol. Adayın söylemediği bilgiye puan
                    verme. İlgisiz veya anlamsız cevaba 0, çok az doğru bilgiye en fazla 20 puan ver.
                    Çabayı değil teknik içeriği puanla. Yalnızca istenen JSON yapısını döndür.
                    """;

            String prompt = (english ? "Question: %s\nReference answer: %s\nExplanation: %s\nCandidate answer: %s"
                    : "Soru: %s\nReferans cevap: %s\nAçıklama: %s\nAdayın cevabı: %s")
                    .formatted(question, sampleAnswer, explanation, userAnswer);

            Map<String, Object> body = Map.of("model", model, "system", system, "prompt", prompt,
                    "stream", false, "format", schema, "options", Map.of("temperature", 0.15));
            HttpRequest request = HttpRequest.newBuilder(generateUri).timeout(Duration.ofMinutes(2))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(body))).build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() < 200 || response.statusCode() >= 300) {
                throw new EvaluationException(errorMessage(response.statusCode(), response.body(), english));
            }
            JsonNode root = objectMapper.readTree(response.body());
            String json = root.path("response").asText();
            if (json.isBlank()) throw new EvaluationException(english ? "Ollama returned no evaluation." : "Ollama değerlendirme döndürmedi.");
            EvaluationResponse result = objectMapper.readValue(json, EvaluationResponse.class);
            int score = Math.max(0, Math.min(100, result.score()));
            return new EvaluationResponse(score, result.summary(), result.correctPoints(), result.incorrectPoints(),
                    result.missingPoints(), result.improvedAnswer());
        } catch (EvaluationException e) {
            throw e;
        } catch (java.net.ConnectException e) {
            throw new EvaluationException(english ? "Ollama is not running. Start Ollama first."
                    : "Ollama çalışmıyor. Önce Ollama'yı açın.", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new EvaluationException(english ? "The evaluation was interrupted." : "Değerlendirme yarıda kesildi.", e);
        } catch (Exception e) {
            throw new EvaluationException(english ? "The local AI evaluation could not be completed."
                    : "Yerel yapay zekâ değerlendirmesi tamamlanamadı.", e);
        }
    }

    private boolean isClearlyInsufficient(String answer) {
        if (answer == null) return true;
        String value = answer.trim();
        return value.length() < 15 || value.split("\\s+").length < 3;
    }

    private String errorMessage(int status, String body, boolean english) {
        try {
            String message = objectMapper.readTree(body).path("error").asText();
            return message.isBlank() ? "Ollama error (" + status + ")" : "Ollama: " + message;
        } catch (Exception ignored) {
            return english ? "Ollama error (" + status + ")" : "Ollama hatası (" + status + ")";
        }
    }
}
