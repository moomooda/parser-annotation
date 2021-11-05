package com.javatechie.spring.ajax.api.Service.Impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.javatechie.spring.ajax.api.dao.*;
import com.javatechie.spring.ajax.api.dto.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.javatechie.spring.ajax.api.CorpusStatus;
import com.javatechie.spring.ajax.api.LoginInterceptor;
import com.javatechie.spring.ajax.api.Service.Interface.CorpusService;
import com.javatechie.spring.ajax.api.util.StringUtil;

@Service
public class CorpusServiceImpl implements CorpusService {
	Logger log = Logger.getLogger(LoginInterceptor.class);

	@Autowired
	CorpusDao corpusDao;
	@Autowired
	CorpusStatusDao corpusStatusDao;
	@Autowired
	BatchUserDao batchUserDao;
	@Autowired
	BatchInfoDao batchInfoDao;
	@Autowired
	CorpusTranslationDao corpusTranslationDao;

	@Override
	public Map<String, List<Object>> cutOriginalCorpus(Integer corpusId) {
		Map<String, List<Object>> resultMap = new HashMap<String, List<Object>>();
		List<Object> words = new ArrayList<Object>();
		List<Object> tags = new ArrayList<Object>();
		List<Object> wordsSize = new ArrayList<Object>();

		String originalCorpus = corpusDao.getOne(corpusId).getOriginalCorpus();

		String wordArr[] = originalCorpus.split(" \\["); // 避免 word 中出现空格的情况

		for (int i = 0; i < wordArr.length; i++) {
			String slide[] = wordArr[i].split("]")[1].split("/");
			String tag = slide[slide.length - 1];
			String word = "";
			if (StringUtil.isBlank(slide[0])) {
				continue;
			} else {
				word = slide[0];
			}
			for (int j = 1; j < slide.length - 1; j++) { // 避免 word 当中可能有“/”的情况
				if (slide[j] == "") {
					word += "/";
				} else {
					word += "/" + slide[j];
				}
			}

			words.add(word);
			tags.add(tag);
			wordsSize.add(word.length());
		}
		resultMap.put("words", words);
		resultMap.put("tags", tags);
		resultMap.put("wordsSize", wordsSize);
		return resultMap;
	}

	@Override
	public Map<String, List<Object>> cutRelationCorpus(Integer corpusId) {
		Map<String, List<Object>> resultMap = new HashMap<String, List<Object>>();
		List<Object> starts = new ArrayList<Object>();
		List<Object> ends = new ArrayList<Object>();
		List<Object> relations = new ArrayList<Object>();

		String relationCorpus = corpusDao.getOne(corpusId).getRelation();

		String words2[] = relationCorpus.split("\\t");

		for (int i = 0; i < words2.length; i++) {
			if (StringUtil.isBlank(words2[i])) {
				continue;
			}
			String temp1 = words2[i];
			String tmp2[] = temp1.split("\\[");
			String start = tmp2[1].split("\\]")[0];
			String end = tmp2[2].split("\\]")[0];
			String relation = temp1.substring(temp1.lastIndexOf("(") + 1, temp1.lastIndexOf(")"));

			starts.add(start);
			ends.add(end);
			relations.add(relation);
		}
		resultMap.put("start", starts);
		resultMap.put("end", ends);
		resultMap.put("relation", relations);
		return resultMap;
	}

	@Override
	public String jointOriginalCorpus(List<String> words, List<String> tags) {
		int len = words.size();
		StringBuffer sBuffer = new StringBuffer();
		for (int i = 0; i < len; i++) {
			sBuffer.append("[").append(i).append("]").append(words.get(i)).append("/").append(tags.get(i));
			if (i != len - 1) {
				sBuffer.append(" ");
			}
		}
		return sBuffer.toString();
	}

	@Override
	public String jointRelation(List<String> words, List<Integer> starts, List<Integer> ends, List<String> relations) {
		StringBuffer sBuffer = new StringBuffer();
		int len = relations.size();
		for (int i = 0; i < len; i++) {
			int start = starts.get(i);
			int end = ends.get(i);
			sBuffer.append("[").append(start).append("]").append(words.get(start)).append("_[").append(end).append("]")
					.append(words.get(end)).append("(").append(relations.get(i)).append(")");
			if (i != len - 1) {
				sBuffer.append("\t");
			}
		}
		return sBuffer.toString();
	}

	@Override
	public void updateOriginalCorpusAndRelationAndStatus(String corpus, String relation, Integer statusId, Integer corpusId)
			throws ParseException {
		corpusDao.updateOriginalCorpusAndRelationAndStatusById(corpus, relation, new Date(), statusId, corpusId);
	}

	@Override
	public void updateCorpusStatusById(Integer corpusId, Integer corpusStatus) throws ParseException {
		corpusDao.updateCorpusStatusById(corpusId, corpusStatus, new Date());
	}

	@Override
	public Corpus getCorpusById(Integer corpusId) {
		return corpusDao.getOne(corpusId);
	}

	@Override
	public int countByUserIdAndStatusIdAndBatch(Integer userId, Integer statusId, Integer batch) {
		return corpusDao.countByUserIdAndStatusIdAndBatchId(userId, statusId, batch);
	}

	@Override
	public int findMinIndexCoupusIdByUserIdAndBatchIdAndStatusId(Integer userId, Integer statusId, Integer batchId) {
		return corpusDao.findMinIndexCoupusIdByUserIdAndBatchIdAndStatusId(userId, batchId, statusId);
	}

	@Override
	public Page<Corpus> findByKeyWordAndUserIdAndBatch(String keyWord, Integer userId, Integer batch, Integer statusId,
			int pageSize, int pageIndex) {
		// 规格定义
		Specification<Corpus> specification = new Specification<Corpus>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Corpus> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate like = null;

				if (!StringUtil.isBlank(keyWord)) { // 添加断言
					String searchKey = "%";
					String[] keys = keyWord.split("\\s+");
					for (int i = 0; i < keys.length; i++) {
						searchKey = searchKey + keys[i] + "%";
					}
					log.info("searchKey : " + searchKey);
					Predicate likeKeyWordOriginal = cb.like(root.get("originalCorpus").as(String.class), searchKey);
					Predicate likeKeyWordResult = cb.like(root.get("relation").as(String.class), searchKey);
					like = cb.or(likeKeyWordOriginal, likeKeyWordResult);
				}
				Predicate equalUserId = cb.equal(root.get("userId").as(String.class), userId);
				Predicate equalBatch = cb.equal(root.get("batchId").as(Integer.class), batch);
				Predicate equalStatus = cb.equal(root.get("statusId").as(Integer.class), statusId);

				if (null != like) {
					return cb.and(equalBatch, equalStatus, equalUserId, like);
				} else {
					return cb.and(equalBatch, equalStatus, equalUserId);
				}
			}
		};
		Sort.Order order = null;
		if (statusId == CorpusStatus.INIT) {
			order = Sort.Order.desc("importDate");
		} else {
			order = Sort.Order.desc("updateDate");
		}
		Sort sort = Sort.by(order);
		// 分页信息
		Pageable pageable = PageRequest.of(pageIndex - 1, pageSize, sort);// 页码：前端从1开始，jpa从0开始，做个转换
		// 查询
		return corpusDao.findAll(specification, pageable);
	}

	@Override
	public Corpus getLastCorpus(Integer batchId, Integer userId, Integer currentCorpusId) {
		log.info("In the method getLastCorpus of CorpusServiceImpl......");
		Corpus corpus = corpusDao.getOne(currentCorpusId);
		Integer index = corpus.getIndex();
		// 规格定义
		Specification<Corpus> specification = new Specification<Corpus>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Corpus> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate equalUserId = cb.equal(root.get("userId").as(String.class), userId);
				Predicate equalBatch = cb.equal(root.get("batchId").as(Integer.class), batchId);
				Predicate equalIndex = cb.equal(root.get("index").as(Integer.class), index - 1);

				return cb.and(equalIndex, equalUserId, equalBatch);

			}
		};
		Sort.Order order = Sort.Order.desc("index");
		Sort sort = Sort.by(order);
		List<Corpus> resultCorpus = corpusDao.findAll(specification, sort);
		// 查询
		log.info("the amout of corpus satisfied the condition : " + resultCorpus.size());
		return resultCorpus.size() == 0 ? null : resultCorpus.get(0);
	}

	@Override
	public List<BatchInfo> getBatch(Integer userId) {
		List<BatchInfo> batchInfos = new ArrayList<BatchInfo>();
		List<BatchUser> batchUsers = batchUserDao.findAllByUserId(userId);
		for (BatchUser batchUser : batchUsers) {
			System.out.println(batchUser.toString());
			System.out.println(batchInfoDao.getOne(batchUser.getBatchId()));
			BatchInfo batchInfo = batchInfoDao.findOneByIdAndIsShow(batchUser.getBatchId(), true);
			if (null != batchInfo){
				batchInfos.add(batchInfo);
			}
		}
		return batchInfos;
	}

	@Override
	public List<Status> getStatus() {
		Sort.Order order = Sort.Order.desc("id");
		Sort sort = Sort.by(order);
		return corpusStatusDao.findAll(sort);
	}

	@Override
	public Corpus testCorpus() {
		return corpusDao.findById(corpusDao.findMinIndexCoupusIdByUserIdAndBatchIdAndStatusId(3, 19, 1)).get();
	}

	@Override
	public CorpusTranslation getTranslationInfo(int translationId) {
		return corpusTranslationDao.findCorpusTranslationById(translationId);
	}
}
