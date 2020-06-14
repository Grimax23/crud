package com.example.crud.util;

public class DocumentsUtil {

/*
    public static final List<Document> DOCUMENTS = Arrays.asList(
            new Document("Недействительный документ", "123", LocalDate.of(2020, 2, 27)),
            new Document("Старый документ", "456", LocalDate.of(2019, 2, 28)),
            new Document("Действующий документ", "789", LocalDate.of(2021, 4, 1)),
            new Document("Действующий документ", "321", LocalDate.of(2020, 3, 5))
    );
*/

/*
    public static List<DocumentTo> getValidTodayTos(Collection<Document> documentList) {
        return getFiltered(documentList, document -> isValid(document, LocalDate.now()), LocalDate.now());
    }

    public static List<DocumentTo> getAllTodayTos(Collection<Document> documentList) {
        return getFiltered(documentList, document -> true, LocalDate.now());
    }

    public static List<DocumentTo> getValidOnDateTos(Collection<Document> documentList, LocalDate date) {
        return getFiltered(documentList, document -> isValid(document, date), date);
    }

    private static List<DocumentTo> getFiltered(Collection<Document> documentList, Predicate<Document> filter, LocalDate date) {
        return documentList.stream()
                .filter(filter)
                .map(document -> createTo(document, isValid(document, date)))
                .collect(Collectors.toList());
    }

    private static boolean isValid(Document document, LocalDate date) {
        return document.getExpiryDate().compareTo(date) >= 0;
    }

    private static DocumentTo createTo(Document document, boolean valid) {
        return new DocumentTo(document.getId(), document.getTitle(), document.getNumber(), document.getExpiryDate(), valid);
    }
*/

}
