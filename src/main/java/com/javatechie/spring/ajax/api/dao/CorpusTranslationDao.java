package com.javatechie.spring.ajax.api.dao;

import com.javatechie.spring.ajax.api.dto.CorpusTranslation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author: maodazhan
 * @Date: 2021/11/4 18:38
 */
@Repository
public interface CorpusTranslationDao extends JpaRepository<CorpusTranslation, Integer> {

    CorpusTranslation findCorpusTranslationById(int id);
}
