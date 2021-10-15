package com.javatechie.spring.ajax.api.Service.Interface;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.javatechie.spring.ajax.api.dto.BatchInfo;
import com.javatechie.spring.ajax.api.dto.Corpus;
import com.javatechie.spring.ajax.api.dto.Status;

public interface CorpusService {
	Map<String, List<Object>> cutOriginalCorpus(Integer corpusId);

	Map<String, List<Object>> cutRelationCorpus(Integer corpusId);

	int countByUserIdAndStatusIdAndBatch(Integer userId, Integer statusId, Integer batch);

	int findMinIndexCoupusIdByUserIdAndBatchIdAndStatusId(Integer userId, Integer status, Integer batch);

	String jointOriginalCorpus(List<String> words, List<String> tags);

	String jointRelation(List<String> words, List<Integer> starts, List<Integer> ends, List<String> relations);

	void updateOriginalCorpusAndRelationAndStatus(String corpus, String relation, Integer statusId, Integer corpusId) throws ParseException;

	void updateCorpusStatusById(Integer corpusId, Integer corpusStatus) throws ParseException;

	Corpus getCorpusById(Integer corpusId);

	/**
	 * 检索
	 * @param keyWord
	 * @param userId
	 * @param batch
	 * @param statusId
	 * @param pageSize
	 * @param pageIndex
	 * @return
	 */
	Page<Corpus> findByKeyWordAndUserIdAndBatch(String keyWord, Integer userId, Integer batch, Integer statusId, int pageSize,
			int pageIndex);
		
	/**
	 * 获取最近更新的一条语料
	 * @param userId
	 * @return
	 */
	Corpus getLastCorpus(Integer batchId, Integer userId, Integer currentCorpusId);
	
	/**
	 * 获取用户所属的所有批次信息
	 * @return
	 */
	List<BatchInfo> getBatch(Integer userId);
	
	/**
	 * 
	 * @return
	 */
	List<Status> getStatus();
	
	Corpus testCorpus ();
}
