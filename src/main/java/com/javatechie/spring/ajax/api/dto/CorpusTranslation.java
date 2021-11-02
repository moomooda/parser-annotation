package com.javatechie.spring.ajax.api.dto;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * @Author: maodazhan
 * @Date: 2021/10/30 20:36
 */
@Entity
@Table(name = "corpus_translation")
@EntityListeners(AuditingEntityListener.class)
public class CorpusTranslation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 1000)
    private String originalParagraph;

    @Column(length = 1000)
    private String translationParagraph;

    public Integer getId() {
        return id;
    }

    public String getOriginalParagraph() {
        return originalParagraph;
    }

    public String getTranslationParagraph() {
        return translationParagraph;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setOriginalParagraph(String originalParagraph) {
        this.originalParagraph = originalParagraph;
    }

    public void setTranslationParagraph(String translationParagraph) {
        this.translationParagraph = translationParagraph;
    }

    @Override
    public String toString() {
        return "CorpusTranslation{" +
                "id=" + id +
                ", originalParagraph='" + originalParagraph + '\'' +
                ", translationParagraph='" + translationParagraph + '\'' +
                '}';
    }
}
