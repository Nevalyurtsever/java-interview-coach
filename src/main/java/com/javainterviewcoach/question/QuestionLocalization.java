package com.javainterviewcoach.question;

import java.util.List;

final class QuestionLocalization {
    private QuestionLocalization() {}
    record Content(String question, String answer, String explanation) {}

    private static final List<Content> EN = List.of(
        c("What are the differences between JDK, JRE, and JVM?", "The JVM executes bytecode. The JRE contains the JVM and runtime libraries. The JDK also contains development tools such as javac.", "Code is compiled with the JDK and runs on the JVM."),
        c("What is the difference between == and equals() in Java?", "== compares primitive values or object references; equals() compares logical equality when implemented correctly.", "Use equals() to compare the content of value objects such as String."),
        c("Why is String immutable?", "A String cannot change after creation; operations create a new String.", "Immutability supports security, thread safety, the string pool, and cached hash values."),
        c("What is the difference between checked and unchecked exceptions?", "Checked exceptions must be caught or declared; RuntimeException subclasses are unchecked.", "Checked exceptions often represent recoverable external conditions; unchecked exceptions often signal programming errors."),
        c("Explain final, finally, and finalize.", "final restricts reassignment or inheritance; finally is a cleanup block; finalize was a deprecated GC hook.", "Prefer try-with-resources for cleanup; finalize should not be used."),
        c("What does pass-by-value mean in Java?", "Java always passes a copied value. For objects, that value is the reference.", "A method can mutate an object but cannot replace the caller's reference variable."),
        c("What is encapsulation and how is it implemented in Java?", "Encapsulation hides state and exposes controlled behavior through methods.", "It protects object rules, not merely fields."),
        c("What is the difference between inheritance and composition?", "Inheritance is an is-a relationship; composition is a has-a relationship.", "Composition often reduces coupling and improves flexibility."),
        c("What is polymorphism?", "Different subtype implementations can run through the same parent-type reference.", "Overriding and dynamic dispatch provide runtime polymorphism."),
        c("What are the differences between an abstract class and an interface?", "An abstract class can hold state and constructors; a class may implement multiple interfaces.", "Choose based on shared state versus a behavioral contract."),
        c("What is the difference between overloading and overriding?", "Overloading changes parameters and is resolved at compile time; overriding replaces inherited behavior at runtime.", "A return type alone cannot overload a method."),
        c("What does the Single Responsibility Principle mean?", "A class should have one primary responsibility and one reason to change.", "It separates different causes of change."),
        c("What are the main differences among List, Set, and Map?", "List is ordered and allows duplicates; Set stores unique elements; Map stores key-value pairs.", "Choose according to ordering, uniqueness, and access needs."),
        c("What is the difference between ArrayList and LinkedList?", "ArrayList has fast indexed access; LinkedList is node-based and has slow indexed access.", "ArrayList is usually preferred for general use."),
        c("How does HashMap work?", "HashMap uses hashCode to select a bucket and equals to locate the matching key.", "A correct equals/hashCode contract is essential."),
        c("How does HashSet ensure uniqueness?", "It uses hashCode to locate candidates and equals to test equality.", "Equal objects must have the same hash code."),
        c("What is the difference between Comparable and Comparator?", "Comparable defines natural order; Comparator defines external alternative orders.", "Comparator provides multiple sort strategies without changing the class."),
        c("Why does ConcurrentModificationException occur?", "A fail-fast iterator may throw it when the collection changes outside the iterator during iteration.", "Use Iterator.remove or a concurrent collection."),
        c("What is the main purpose of Spring Boot?", "It provides auto-configuration, starters, and embedded servers for fast setup.", "Convention over configuration reduces repetitive work."),
        c("What is Dependency Injection?", "Dependencies are supplied from outside rather than created by the class.", "Constructor injection makes dependencies explicit and testable."),
        c("What does @RestController do?", "It handles HTTP requests and serializes return values into the response body.", "It combines @Controller and @ResponseBody."),
        c("What is the difference between @Service and @Repository?", "@Service expresses business logic; @Repository expresses data access and enables exception translation.", "The annotations communicate layer intent."),
        c("What does a Spring Data JPA repository provide?", "It provides CRUD methods and query derivation from method names.", "JpaRepository includes save, findById, findAll, and delete."),
        c("Why is @Valid used?", "It triggers Jakarta Validation constraints on incoming objects.", "A global handler can return consistent validation errors."),
        c("What is the difference between INNER JOIN and LEFT JOIN?", "INNER JOIN returns matches; LEFT JOIN returns all left rows plus matching right rows.", "Unmatched right columns are NULL."),
        c("What are primary and foreign keys?", "A primary key identifies a row; a foreign key references another table and preserves integrity.", "Foreign keys prevent invalid relationships."),
        c("What is the difference between WHERE and HAVING?", "WHERE filters rows before grouping; HAVING filters groups after GROUP BY.", "Aggregate filters generally use HAVING."),
        c("What does a database index do?", "An index speeds up locating rows.", "It improves reads but costs storage and write performance."),
        c("What is normalization?", "Normalization separates data into related tables to reduce duplication and anomalies.", "The first three normal forms are a common baseline."),
        c("What are the ACID properties?", "Atomicity is all-or-nothing; Consistency preserves rules; Isolation separates concurrent work; Durability preserves commits.", "ACID describes transaction reliability.")
    );

    static Content get(Question q, String language) {
        if (!"en".equalsIgnoreCase(language)) return new Content(q.getText(), q.getSampleAnswer(), q.getExplanation());
        int index = Math.toIntExact(q.getId() - 1);
        return index >= 0 && index < EN.size() ? EN.get(index) : new Content(q.getText(), q.getSampleAnswer(), q.getExplanation());
    }
    private static Content c(String q, String a, String e) { return new Content(q, a, e); }
}
