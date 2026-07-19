# Java Interview Coach

Junior Java geliştiricilerin mülakat sorularıyla pratik yapmasını sağlayan Spring Boot uygulaması.

## Özellikler

- Java Core, OOP, Collections, Spring Boot ve SQL kategorileri
- Seçilen kategoriden rastgele soru
- Gerçek mülakat deneyimlerinden derlenmiş soru modu
- IntelliJ görünümüne benzeyen Java kodlama çalışma alanı
- Ollama ile bilgisayarda çalışan ücretsiz yapay zekâ değerlendirmesi
- Doğru, yanlış ve eksik noktaların ayrı gösterimi; 0-100 puan ve geliştirilmiş cevap
- H2 veritabanına başlangıçta yüklenen 30 soru
- Validation, merkezi hata yanıtları ve REST API
- JUnit 5, Mockito ve MockMvc testleri

## Gereksinimler

- Java 17
- Maven 3.9+
- [Ollama](https://ollama.com/download)
- Önerilen: en az 8 GB RAM

Sürümleri kontrol edin:

```powershell
java -version
mvn -version
ollama --version
```

## İlk kurulum (Windows)

1. PowerShell'de bu oturum için Java 17'yi etkinleştirin:

```powershell
$env:JAVA_HOME="C:\Program Files\Java\jdk-17"
$env:Path="$env:JAVA_HOME\bin;$env:Path"
java -version
mvn -version
```

Her iki sürüm çıktısında da Java 17 görünmelidir.

2. Ollama'yı indirip kurun: <https://ollama.com/download>
3. PowerShell açıp ücretsiz yerel modeli indirin:

```powershell
ollama pull qwen2.5:3b
```

Model bir defa indirilir. Sonraki kullanımlarda yeniden indirmeniz gerekmez. Model bilgisayarınızda çalışır; OpenAI API anahtarı, kredi kartı ve kullanım ücreti gerekmez.

4. Proje klasöründe uygulamayı çalıştırın:

```powershell
cd "C:\Users\neval\Documents\Codex\2026-07-18\java-geli-tirici-m-lakatlar-na"
mvn spring-boot:run
```

5. Tarayıcıda [http://localhost:8081](http://localhost:8081) adresini açın.

İlk değerlendirme modelin belleğe yüklenmesi nedeniyle biraz uzun sürebilir. Terminali kapatırsanız Spring Boot uygulaması durur.

### Ollama bağlantı hatası olursa

Ollama uygulamasının açık olduğundan emin olun ve şunları çalıştırın:

```powershell
ollama list
ollama serve
```

Windows'ta Ollama zaten arka planda çalışıyorsa `ollama serve` komutu portun kullanımda olduğunu söyleyebilir; bu normaldir.

Farklı bir yerel model kullanmak isterseniz uygulamayı başlatmadan önce:

```powershell
$env:OLLAMA_MODEL="qwen2.5:3b"
```

## Test ve paketleme

```powershell
mvn test
mvn clean package
```

Oluşan paketi çalıştırmak için:

```powershell
java -jar target/java-interview-coach-0.0.1-SNAPSHOT.jar
```

## REST API

| Metot | Adres | Açıklama |
|---|---|---|
| `GET` | `/api/categories` | Kategorileri listeler |
| `GET` | `/api/questions/random?category=JAVA_CORE` | Kategoriden rastgele soru getirir |
| `POST` | `/api/questions/{id}/check` | Yerel AI değerlendirmesi, örnek cevap ve açıklamayı getirir |
| `GET` | `/api/reported-questions` | Kaynaklı gerçek mülakat deneyimi sorularını listeler |

Cevap kontrolü istek gövdesi:

```json
{
  "answer": "Kendi cevabınız"
}
```

H2 konsolu geliştirme amacıyla `/h2-console` adresindedir. JDBC URL: `jdbc:h2:mem:interviewcoach`, kullanıcı: `sa`, parola: boştur.

## Proje yapısı

```text
src/main/java/com/javainterviewcoach
├── config/       Başlangıç soru verileri
├── evaluation/   Ollama yerel yapay zekâ değerlendirmesi
├── exception/    Merkezi API hata yönetimi
└── question/     Entity, repository, service, controller ve DTO'lar
src/main/resources
├── static/       HTML, CSS ve vanilla JavaScript arayüzü
└── application.properties
src/test/java     Servis, web katmanı ve uygulama testleri
```

Not: Öz değerlendirme puanı tarayıcı sekmesinin `sessionStorage` alanında tutulur ve sekme oturumu sona erince sıfırlanabilir.
