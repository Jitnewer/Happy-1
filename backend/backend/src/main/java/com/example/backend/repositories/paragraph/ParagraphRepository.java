package com.example.backend.repositories.paragraph;

import com.example.backend.models.Challenge;
import com.example.backend.models.Paragraph;

import java.util.List;

public interface ParagraphRepository {
    List<Paragraph> getParagraphs();
    Paragraph getParagraph(long id);
    void addParagraph(Paragraph paragraph);
    void updateParagraph(Paragraph paragraph);
    void deleteParagraph(long id);
}
