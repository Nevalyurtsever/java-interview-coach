package com.javainterviewcoach.config;

import com.javainterviewcoach.question.Category;
import com.javainterviewcoach.question.Question;
import com.javainterviewcoach.question.QuestionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@Configuration
public class QuestionDataLoader {
    @Bean
    CommandLineRunner loadQuestions(QuestionRepository repository) {
        return args -> {
            if (repository.count() > 0) return;
            repository.saveAll(List.of(
                q(Category.JAVA_CORE, "JDK, JRE ve JVM arasındaki farklar nelerdir?", "JVM bytecode'u çalıştırır. JRE, JVM ve çalışma zamanı kütüphanelerini içerir. JDK ise JRE'ye ek olarak javac gibi geliştirme araçlarını içerir.", "Kod JDK ile derlenir, JVM üzerinde çalışır. Modern dağıtımlarda ayrı bir JRE kurulumu her zaman sunulmasa da kavramsal ayrım önemlidir."),
                q(Category.JAVA_CORE, "Java'da == ile equals() arasındaki fark nedir?", "== primitive değerleri veya nesne referanslarını karşılaştırır; equals() ise sınıf uygun biçimde uyguladıysa nesnelerin mantıksal eşitliğini karşılaştırır.", "String gibi değer nesnelerinde içerik karşılaştırmak için equals kullanılmalıdır."),
                q(Category.JAVA_CORE, "String neden immutable'dır?", "String oluşturulduktan sonra içeriği değişmez; yapılan işlemler yeni String üretir.", "Immutable olması güvenlik, thread safety, string pool ve hash değerinin güvenle önbelleklenmesini sağlar."),
                q(Category.JAVA_CORE, "Checked ve unchecked exception farkı nedir?", "Checked exception'lar derleyici tarafından kontrol edilir ve yakalanmalı ya da bildirilmeli; RuntimeException alt sınıfları olan unchecked exception'larda bu zorunluluk yoktur.", "Checked hatalar genellikle dış kaynaklı kurtarılabilir durumları, unchecked hatalar programlama kusurlarını temsil eder."),
                q(Category.JAVA_CORE, "final, finally ve finalize kavramlarını açıklayın.", "final değiştirilemezlik/kalıtım kısıtı getirir; finally try-catch sonrasında çalışan bloktur; finalize eski bir GC kancasıdır ve kullanımdan kaldırılmıştır.", "Benzer isimli olsalar da ilgisiz kavramlardır. Kaynak kapatmak için try-with-resources tercih edilir."),
                q(Category.JAVA_CORE, "Java'da pass-by-value ne demektir?", "Java her zaman değeri kopyalayarak geçirir. Nesnelerde kopyalanan değer nesne referansıdır.", "Metot nesnenin durumunu değiştirebilir fakat çağıranın referans değişkenini başka nesneye yönlendiremez."),
                q(Category.OOP, "Encapsulation nedir ve Java'da nasıl uygulanır?", "Nesnenin verisini gizleyip kontrollü metotlarla erişim sağlamaktır; private alanlar ve davranış odaklı public metotlarla uygulanır.", "Amaç yalnız getter/setter yazmak değil, nesnenin kurallarını kendi sınırları içinde korumaktır."),
                q(Category.OOP, "Inheritance ile composition arasındaki fark nedir?", "Inheritance bir 'is-a', composition bir 'has-a' ilişkisidir. Composition davranışları nesneler üzerinden birleştirir.", "Composition daha düşük bağlılık ve daha kolay değişim sağladığı için çoğu durumda tercih edilir."),
                q(Category.OOP, "Polymorphism nedir?", "Aynı üst tür referansı üzerinden farklı alt tür davranışlarının çalışabilmesidir.", "Metot overriding ve dinamik dispatch çalışma zamanındaki polymorphism'in temelidir."),
                q(Category.OOP, "Abstract class ile interface arasındaki farklar nelerdir?", "Abstract class durum ve constructor taşıyabilir; bir sınıf yalnız bir sınıfı extend eder. Interface sözleşme tanımlar ve bir sınıf birden çok interface uygulayabilir.", "Interface'ler default ve static metot içerebilir; seçim ortak durum ihtiyacına ve tasarımdaki role bağlıdır."),
                q(Category.OOP, "Overloading ve overriding arasındaki fark nedir?", "Overloading aynı isimli farklı parametreli metotlardır ve derleme zamanında seçilir. Overriding alt sınıfın üst sınıf metodunu yeniden uygulamasıdır ve çalışma zamanında seçilir.", "Dönüş tipi tek başına overloading için yeterli değildir."),
                q(Category.OOP, "SOLID prensiplerinden Single Responsibility neyi anlatır?", "Bir sınıfın değişmek için tek bir nedeni, yani tek bir temel sorumluluğu olması gerektiğini söyler.", "Bu ilke küçük sınıf yazmaktan çok farklı değişim nedenlerini ayırmayı hedefler."),
                q(Category.COLLECTIONS, "List, Set ve Map arasındaki temel farklar nelerdir?", "List sıralı ve tekrarlı elemanlara izin verir; Set benzersiz elemanlar tutar; Map anahtar-değer eşleşmeleri tutar.", "Seçim erişim biçimine, sıralama ve benzersizlik gereksinimine göre yapılır."),
                q(Category.COLLECTIONS, "ArrayList ile LinkedList arasındaki fark nedir?", "ArrayList dizi tabanlıdır ve indeks erişimi hızlıdır. LinkedList düğüm tabanlıdır; indeks erişimi yavaştır, iterator ile bilinen konumdaki ekleme/silme ucuz olabilir.", "Pratikte önbellek dostu yapısı nedeniyle çoğu genel kullanımda ArrayList tercih edilir."),
                q(Category.COLLECTIONS, "HashMap nasıl çalışır?", "Anahtarın hashCode değeriyle bir bucket bulunur, ardından equals ile doğru anahtar eşleştirilir.", "İyi hash dağılımı performans sağlar; equals ve hashCode sözleşmesine birlikte uyulmalıdır."),
                q(Category.COLLECTIONS, "HashSet elemanların benzersizliğini nasıl sağlar?", "İçeride HashMap benzeri bir yapı kullanır; hashCode ile aday bölgeyi, equals ile eşitliği belirler.", "Eşit nesneler aynı hashCode değerini üretmelidir."),
                q(Category.COLLECTIONS, "Comparable ve Comparator farkı nedir?", "Comparable sınıfın doğal sırasını compareTo ile tanımlar; Comparator dışarıdan bir veya daha fazla alternatif sıralama tanımlar.", "Comparator, sınıfı değiştirmeden farklı alanlara göre sıralama sağlar."),
                q(Category.COLLECTIONS, "ConcurrentModificationException neden oluşur?", "Bir koleksiyon dolaşılırken iterator dışından yapısal olarak değiştirilirse fail-fast iterator bu hatayı verebilir.", "Silme için Iterator.remove veya uygun concurrent koleksiyon kullanılabilir."),
                q(Category.SPRING_BOOT, "Spring Boot'un temel amacı nedir?", "Spring uygulamalarını otomatik yapılandırma, starter bağımlılıklar ve gömülü sunucuyla hızlı ve tutarlı biçimde kurmayı sağlar.", "Convention over configuration yaklaşımı tekrarlı yapılandırmayı azaltır."),
                q(Category.SPRING_BOOT, "Dependency Injection nedir?", "Bir sınıfın bağımlılıklarını kendisinin üretmesi yerine dışarıdan almasıdır.", "Constructor injection bağımlılıkları görünür, zorunlu ve test edilebilir yaptığı için önerilir."),
                q(Category.SPRING_BOOT, "@RestController ne işe yarar?", "Sınıfı HTTP isteklerini karşılayan controller yapar ve metot dönüşlerini varsayılan olarak response body'ye serileştirir.", "@Controller ve @ResponseBody birleşimi gibi düşünülebilir."),
                q(Category.SPRING_BOOT, "@Service ve @Repository anotasyonlarının farkı nedir?", "İkisi de Spring bean'i tanımlar; @Service iş mantığını, @Repository veri erişimini belirtir ve repository istisna dönüşümünü etkinleştirir.", "Katman niyetini açıkça ifade etmek bakım ve test kolaylığı sağlar."),
                q(Category.SPRING_BOOT, "Spring Data JPA repository ne sağlar?", "Entity veri erişimi için hazır CRUD metotları ve metot adından sorgu türetme imkânı sağlar.", "JpaRepository save, findById, findAll ve delete gibi işlemleri hazır sunar."),
                q(Category.SPRING_BOOT, "@Valid anotasyonu neden kullanılır?", "Gelen nesnedeki Jakarta Validation kurallarının controller girişinde çalıştırılmasını sağlar.", "Hatalar merkezi exception handler ile tutarlı bir API yanıtına dönüştürülebilir."),
                q(Category.SQL, "INNER JOIN ile LEFT JOIN arasındaki fark nedir?", "INNER JOIN yalnız iki tarafta eşleşen satırları; LEFT JOIN soldaki tüm satırları ve varsa sağdaki eşleşmeyi döndürür.", "LEFT JOIN'de eşleşmeyen sağ taraf sütunları NULL olur."),
                q(Category.SQL, "Primary key ve foreign key nedir?", "Primary key satırı benzersiz tanımlar. Foreign key başka tablodaki anahtar değerine referans vererek ilişkisel bütünlük sağlar.", "Foreign key geçersiz ilişkilerin veritabanına yazılmasını engeller."),
                q(Category.SQL, "WHERE ile HAVING arasındaki fark nedir?", "WHERE gruplamadan önce satırları, HAVING GROUP BY sonrasında grupları filtreler.", "Toplam gibi aggregate sonuçlarına göre filtre genellikle HAVING ile yapılır."),
                q(Category.SQL, "Veritabanı index'i ne işe yarar?", "Sorgularda aranan satırlara daha hızlı ulaşmayı sağlayan ek veri yapısıdır.", "Okumayı hızlandırırken disk kullanımı ve insert/update maliyetini artırır; her sütuna eklenmemelidir."),
                q(Category.SQL, "Normalization nedir?", "Veri tekrarını ve güncelleme anomalilerini azaltmak için veriyi ilişkili tablolara kurallı biçimde ayırmadır.", "İlk üç normal form çoğu işlem sistemi için temel bir tasarım rehberidir."),
                q(Category.SQL, "ACID özellikleri nelerdir?", "Atomicity işlemin bütünüyle olması, Consistency kuralların korunması, Isolation eşzamanlı işlemlerin ayrılması, Durability commit edilen verinin kalıcı olmasıdır.", "Bu özellikler transaction güvenilirliğini tarif eder.")
            ));
        };
    }

    private static Question q(Category category, String text, String answer, String explanation) {
        return new Question(category, text, answer, explanation);
    }
}
