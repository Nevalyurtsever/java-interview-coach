package com.javainterviewcoach.question;

import com.javainterviewcoach.question.dto.ReportedQuestionResponse;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReportedQuestionService {
    public List<ReportedQuestionResponse> getReportedQuestions() {
        return List.of(
            q("Apple", "Java Backend Engineer", "Java Core", "Java neden platformdan bağımsızdır?", "https://leetcode.com/discuss/post/6461783/"),
            q("Apple", "Java Backend Engineer", "Collections", "Map ile Set arasındaki fark nedir?", "https://leetcode.com/discuss/post/6461783/"),
            q("Apple", "Java Backend Engineer", "Spring Boot", "Spring Bean'leri neden kullanırız?", "https://leetcode.com/discuss/post/6461783/"),
            q("Lenovo", "Java Backend Developer", "Collections", "HashMap ile Hashtable arasındaki fark nedir?", "https://leetcode.com/discuss/post/7614156/"),
            q("Lenovo", "Java Backend Developer", "Spring Boot", "Spring Boot uygulaması başlarken içeride neler olur?", "https://leetcode.com/discuss/post/7614156/"),
            q("Lenovo", "Java Backend Developer", "SQL", "İki tablo arasında INNER JOIN sorgusu yazın.", "https://leetcode.com/discuss/post/7614156/"),
            q("Paytm", "Java Backend Developer", "Java Core", "wait() ile sleep() arasındaki fark nedir?", "https://leetcode.com/discuss/interview-experience/435730/"),
            q("Paytm", "Java Backend Developer", "Java Core", "hashCode() neden gereklidir ve hangi sınıfa aittir?", "https://leetcode.com/discuss/interview-experience/435730/"),
            q("NPCI", "Java Developer", "Concurrency", "Deadlock nasıl önlenir?", "https://leetcode.com/discuss/post/7571344/"),
            q("NPCI", "Java Developer", "Streams", "Stream kullanarak bir List'i HashMap'e dönüştürün.", "https://leetcode.com/discuss/post/7571344/"),
            q("Zinier", "SDE-1 Backend", "Spring Boot", "Bir isteğin Spring Boot uygulamasındaki yaşam döngüsünü anlatın.", "https://leetcode.com/discuss/post/6863175/"),
            q("HashedIn", "Java Backend SDE-2", "Design Patterns", "Singleton, Decorator ve Strategy pattern'lerini kodlayın.", "https://leetcode.com/discuss/interview-experience/1758342/")
        );
    }

    private ReportedQuestionResponse q(String company, String role, String topic, String question, String url) {
        return new ReportedQuestionResponse(company, role, topic, question, url);
    }
}
