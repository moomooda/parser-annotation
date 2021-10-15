package com.javatechie.spring.ajax.api.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.javatechie.spring.ajax.api.dto.Corpus;

@Repository
public interface CorpusDao extends JpaRepository<Corpus, Integer> {

	int countByUserIdAndStatusIdAndBatchId(Integer userId, Integer statusId, Integer batch);

	List<Corpus> findByUserIdAndStatusId(Integer userId, Integer statusId);

	List<Corpus> findAllByUserIdAndStatusIdAndBatchId(Integer userId, Integer statusId, Integer batchId);

	Page<Corpus> findAll(@Nullable Specification<Corpus> var1, Pageable pageable);

	List<Corpus> findAll(@Nullable Specification<Corpus> var1, Sort sort);

	@Transactional
	@Modifying
	@Query("UPDATE Corpus c SET c.originalCorpus=?1, c.relation=?2, c.updateDate=?3, c.statusId = ?4 WHERE c.id=?5")
	int updateOriginalCorpusAndRelationAndStatusById(String corpus, String relation, Date date, Integer statusId, Integer corpusId);

	@Transactional
	@Modifying
	@Query("UPDATE Corpus c SET c.statusId=?2, c.updateDate=?3 WHERE c.id=?1")
	int updateCorpusStatusById(Integer corpusId, Integer corpusStatus, Date date);
	
	@	Query(nativeQuery = true,
			value = "SELECT id FROM corpus c WHERE c.user_id=?1 AND c.batch_id=?2 AND c.status_id=?3 order by c.index LIMIT 1")
    int findMinIndexCoupusIdByUserIdAndBatchIdAndStatusId(Integer userId, Integer batchId, Integer statusId);
}
