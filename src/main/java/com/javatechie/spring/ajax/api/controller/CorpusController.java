package com.javatechie.spring.ajax.api.controller;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javatechie.spring.ajax.api.dto.*;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.javatechie.spring.ajax.api.CorpusStatus;
import com.javatechie.spring.ajax.api.Service.Interface.CorpusService;
import com.javatechie.spring.ajax.api.dao.BatchInfoDao;
import com.javatechie.spring.ajax.api.dao.CorpusDao;
import de.vandermeer.svg2vector.applications.fh.Svg2Vector_FH;

@RestController
public class CorpusController {

	@Autowired
	CorpusService corpusService;
	@Autowired
	CorpusDao corpusDao;
	@Autowired
	BatchInfoDao batchInfoDao;

	/** Logger实例 */
	static final Logger logger = LoggerFactory.getLogger(CorpusController.class);

	/** Standard CLI options for tests. */
	static String[] FH_STD_OPTIONS = new String[] { "--create-directories", "--overwrite-existing", "--all-layers",
			"-v" };
	/** Standard CLI options for tests. */
	static String[] STD_OPTIONS = ArrayUtils.addAll(FH_STD_OPTIONS, "-t", "emf", "-q");
	/** Prefix for tests that create output. */
	static String FH_OUT_DIR_PREFIX = "target/output-tests/s2v-fh/";
	/** Prefix for tests that create output. */
	static String OUT_DIR_PREFIX = FH_OUT_DIR_PREFIX + "emf/";


	/**
	 * 判断当前批次用户是否标注完成
	 * @param batchId
	 * @param userId
	 * @return
	 */
	@GetMapping("/isDone")
	public ResponseEntity<Object> isDone(Integer batchId, Integer userId) {
		logger.info("In the method isDone of  CorpusController");
		logger.info("userId : " + userId);
		logger.info("batchId : " + batchId);


		String message = null;
		int initNum = corpusService.countByUserIdAndStatusIdAndBatch(userId, CorpusStatus.INIT, batchId);
		int passNum = corpusService.countByUserIdAndStatusIdAndBatch(userId, CorpusStatus.PASS, batchId);
		if (initNum == 0 && passNum == 0) {
			// 无未标注且无跳过，则标注完成
			message = "error";
		} else {
			message = "success";
		}
		ServiceResponse<Map<String, Object>> response = new ServiceResponse<>(message, null);
		logger.info("initNum: " + initNum + "   passNum:" + passNum);
		logger.info("Out the method isDone of  CorpusController");
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}

	/**
	 * 获取语料
	 * 
	 * @param corpusId(只有从搜索页面跳转才会有值)
	 * @param userId
	 * @return
	 */
	@GetMapping("/getCorpus")
	public ResponseEntity<Object> getCorpus(Integer batchId, Integer corpusId, Integer userId, String isDone
								 , @RequestParam (defaultValue = "false") boolean isOld) {
		logger.info("corpusId : " + corpusId);
		logger.info("userId : " + userId);
		logger.info("isDone : " + isDone);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		int selectedCorpusId;

		if (null != corpusId) {
			logger.info("从搜索页面跳转而来");
			// 从搜索页面跳转而来
			selectedCorpusId = corpusId;
			resultMap.putAll(corpusService.cutOriginalCorpus(corpusId));
			resultMap.putAll(corpusService.cutRelationCorpus(corpusId));
			resultMap.put("corpusId", selectedCorpusId);
			ServiceResponse<Map<String, Object>> response = new ServiceResponse<>("success", resultMap);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
		if (isDone.equals("1")) {
			// 标注完成，显示保存的第一句
			selectedCorpusId = corpusService.findMinIndexCoupusIdByUserIdAndBatchIdAndStatusId(userId,
					CorpusStatus.SAVE, batchId);
		} else {
			// 标注未完成，
			int initNum = corpusService.countByUserIdAndStatusIdAndBatch(userId, CorpusStatus.INIT, batchId);
			if (initNum != 0) {
				// 还有未标注语料
				selectedCorpusId = corpusService.findMinIndexCoupusIdByUserIdAndBatchIdAndStatusId(userId,
						CorpusStatus.INIT, batchId);
			} else {
				// 无未标注语料，返回跳过状态的语料
				selectedCorpusId = corpusService.findMinIndexCoupusIdByUserIdAndBatchIdAndStatusId(userId,
						CorpusStatus.PASS, batchId);
			}
		}

		resultMap.putAll(corpusService.cutOriginalCorpus(selectedCorpusId));
		resultMap.putAll(corpusService.cutRelationCorpus(selectedCorpusId));
		resultMap.put("corpusId", selectedCorpusId);
		// corpus_id 查 translate_id
		Corpus corpus = corpusService.getCorpusById(selectedCorpusId);
		int translationId = corpus.getTranslationId();
		if(translationId != -1) {
			// 去查
			CorpusTranslation corpusTranslation = corpusService.getTranslationInfo(translationId);
			resultMap.put("originalText", corpusTranslation.getOriginalParagraph());
			resultMap.put("translationText", corpusTranslation.getTranslationParagraph());
		} else {
			resultMap.put("originalText", "无");
			resultMap.put("translationText", "无");
		}
		logger.info("resultMap " + resultMap);

		ServiceResponse<Map<String, Object>> response = new ServiceResponse<>("success", resultMap);
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}

	/**
	 * 获取上一句语料
	 * 
	 * @param userId
	 * @param currentCorpusId
	 * @return 上一句语料的 id
	 */
	@RequestMapping("/getLastCorpus")
	public ResponseEntity<Object> getLastCorpus(Integer batchId, Integer userId, Integer currentCorpusId) {
		logger.info("In the method getLastCorpus of  CorpusController......");

		Map<String, Object> resultMap = new HashMap<String, Object>();
		ServiceResponse<Map<String, Object>> response = null;

		if (null == corpusService.getLastCorpus(batchId, userId, currentCorpusId)) {
			logger.info("There is no last Corpus !");
			response = new ServiceResponse<>("error", resultMap);
		} else {
			Integer corpusId = corpusService.getLastCorpus(batchId, userId, currentCorpusId).getId();
			resultMap.put("corpusId", corpusId);
			response = new ServiceResponse<>("success", resultMap);
		}
		logger.info("Out the method getLastCorpus of  CorpusController");
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}

	/**
	 * 保存修改语料
	 * 
	 * @param corpusId
	 * @param words
	 * @param tags
	 * @param starts
	 * @param ends
	 * @param relations
	 * @return
	 */
	@RequestMapping("/save")
	public ResponseEntity<Object> save(@RequestParam(value = "corpusId") Integer corpusId,
			@RequestParam(value = "words") List<String> words, @RequestParam(value = "tags") List<String> tags,
			@RequestParam(value = "starts") List<Integer> starts, @RequestParam(value = "ends") List<Integer> ends,
			@RequestParam(value = "relations") List<String> relations) {

		// 拼接
		String corpus = corpusService.jointOriginalCorpus(words, tags);
		String relation = corpusService.jointRelation(words, starts, ends, relations);
		try {
			// 更新
			corpusService.updateOriginalCorpusAndRelationAndStatus(corpus, relation, CorpusStatus.SAVE, corpusId);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Object>(HttpStatus.OK);
	}

	/**
	 * 更新语料状态（点击跳过）
	 * 
	 * @param corpusId
	 * @param corpusStatus
	 * @param reason
	 * @return
	 */
	@RequestMapping("/updateStatus")
	public ResponseEntity<Object> updateStatus(@RequestParam(value = "corpusId") Integer corpusId,
			@RequestParam(value = "corpusStatus") Integer corpusStatus) {
		try {
			corpusService.updateCorpusStatusById(corpusId, corpusStatus);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Object>(HttpStatus.OK);
	}


	/**
	 * 模糊检索语料
	 * @param userId
	 * @param keyWord
	 * @param batchId
	 * @param corpusStatus
	 * @param pageSize
	 * @param pageIndex
	 * @return
	 */
	@RequestMapping("/search")
	public ResponseEntity<Object> searchCorpus(@RequestParam(value = "userId") Integer userId,
			@RequestParam(value = "keyWord") String keyWord, @RequestParam(value = "batch") Integer batchId,
			@RequestParam(value = "corpusStatus") Integer corpusStatus,
			@RequestParam(value = "pageSize") Integer pageSize, @RequestParam(value = "pageIndex") Integer pageIndex) {

		List<Map<String, Object>> showCorpus = new ArrayList<Map<String, Object>>();

		Page<Corpus> pagedCorpus = corpusService.findByKeyWordAndUserIdAndBatch(keyWord, userId, batchId, corpusStatus,
				pageSize, pageIndex);
		List<Corpus> corpusList = pagedCorpus.getContent();

		for (Corpus corpus : corpusList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", corpus.getId());
			map.put("corpus", corpus.getOriginalCorpus());
			map.put("batch", batchInfoDao.getOne(corpus.getBatchId()).getName());
			Integer status = corpus.getStatusId();
			switch (status) {
			case 6:
				map.put("status", "已保存");
				break;
			case 5:
				map.put("status", "其他");
				break;
			case 4:
				map.put("status", "暂跳");
				break;
			case 3:
				map.put("status", "无意义");
				break;
			case 2:
				map.put("status", "过长");
				break;
			default:
				map.put("status", "未标注");
				break;
			}
			showCorpus.add(map);
		}
		ServiceResponse<List<Map<String, Object>>> response = new ServiceResponse<List<Map<String, Object>>>("success",
				0, pagedCorpus.getTotalElements(), showCorpus);
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}

	/**
	 * 保存为SWF文件
	 * 
	 * @param words
	 * @param starts
	 * @param ends
	 * @param relations
	 * @return
	 * @throws FileNotFoundException
	 */
	@RequestMapping("/saveAsEMF")
	public ResponseEntity<Object> saveAsEMF(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "svgStr") String svgStr) throws Exception {
		logger.info("In the method saveAsEMF of  CorpusController......");
		String basePath = getResourceBasePath();
		String fileName = UUID.randomUUID().toString();

		StringBuffer sBuffer = new StringBuffer(
				"<svg width=\"800\" height=\"400\" xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\">");
		sBuffer.append(svgStr);
		sBuffer.append("</svg>");

		String suffixPath = "target/output-tests/svg/" + fileName + ".svg";
		String svgResourcePath = new File(basePath, suffixPath).getAbsolutePath();
		// 保证目录一定存在
		ensureDirectory(svgResourcePath);
		BufferedWriter writer;
		writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(svgResourcePath)));
		writer.write(sBuffer.toString());
		writer.flush();
		writer.close();

		Svg2Vector_FH app = new Svg2Vector_FH();
		String[] arg = ArrayUtils.addAll(STD_OPTIONS, "-f", svgResourcePath, "-o", OUT_DIR_PREFIX + fileName);

		File emfFile = new File(OUT_DIR_PREFIX, fileName + ".emf");
		if (emfFile.exists()) {
			// 配置文件下载
			response.setHeader("content-type", "application/octet-stream");
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition",
					"attachment;fileName=" + URLEncoder.encode(fileName + ".emf", "UTF-8")); // 设置文件名
			// 实现文件下载
			byte[] buffer = new byte[1024];
			FileInputStream fis = null;
			BufferedInputStream bis = null;
			try {
				fis = new FileInputStream(emfFile);
				bis = new BufferedInputStream(fis);
				OutputStream os = response.getOutputStream();
				int i = bis.read(buffer);
				while (i != -1) {
					os.write(buffer, 0, i);
					i = bis.read(buffer);
				}
				logger.info("Download the file successfully!");
			} catch (Exception e) {
				logger.info("Download the file failed!");
			} finally {
				if (bis != null) {
					try {
						bis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (fis != null) {
					try {
						fis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return null;
	}

	private static String getResourceBasePath() {
		// 获取根目录
		File path = null;
		try {
			path = new File(ResourceUtils.getURL("classpath:").getPath());
		} catch (FileNotFoundException e) {
			// nothing to do
		}
		if (path == null || !path.exists()) {
			path = new File("");
		}

		String pathStr = path.getAbsolutePath();
		// 如果是在eclipse中运行，则和target同级目录,如果是jar部署到服务器，则默认和jar包同级
		pathStr = pathStr.replace("\\target\\classes", "");

		return pathStr;
	}

	/**
	 * 将符号“\\”和“\”替换成“/”,有时候便于统一的处理路径的分隔符,避免同一个路径出现两个或三种不同的分隔符
	 * 
	 * @param str
	 */
	public static String replaceSeparator(String str) {
		return str.replace("\\", "/").replace("\\\\", "/");
	}

	/**
	 * 
	 * @param filePath
	 */
	public static void ensureDirectory(String filePath) {
		if (StringUtils.isBlank(filePath)) {
			return;
		}
		filePath = replaceSeparator(filePath);
		if (filePath.indexOf("/") != -1) {
			filePath = filePath.substring(0, filePath.lastIndexOf("/"));
			File file = new File(filePath);
			if (!file.exists()) {
				file.mkdirs();
			}
		}
	}

	/**
	 * 
	 * @return
	 */

	@RequestMapping(value = "/down", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String down(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "svgStr") String svgStr) throws Exception {
		String basePath = getResourceBasePath();
		String fileName = UUID.randomUUID().toString();
		System.out.println("svgStr::" + svgStr);
		StringBuffer sBuffer = new StringBuffer(
				"<svg width=\"800\" height=\"400\" xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\">");
		sBuffer.append(svgStr);
		sBuffer.append("</svg>");

		String suffixPath = "target/output-tests/svg/" + fileName + ".svg";
		String svgResourcePath = new File(basePath, suffixPath).getAbsolutePath();
		// 保证目录一定存在
		ensureDirectory(svgResourcePath);
		BufferedWriter writer;
		writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(svgResourcePath)));
		writer.write(sBuffer.toString());
		writer.flush();
		writer.close();

		Svg2Vector_FH app = new Svg2Vector_FH();
		String[] arg = ArrayUtils.addAll(STD_OPTIONS, "-f", svgResourcePath, "-o", OUT_DIR_PREFIX + fileName);
		System.out.println(app.executeApplication(arg));

		String filename = fileName + ".emf";
		String filepath = OUT_DIR_PREFIX + filename;
		System.out.println(filepath);
		// 如果文件名不为空，则进行下载
		if (filename != null) {
			File file = new File(filepath);
			// 如果文件存在，则进行下载
			if (file.exists()) {
				// 配置文件下载
				response.setHeader("content-type", "application/octet-stream");
				response.setContentType("application/octet-stream");
				// 下载文件能正常显示中文
				response.setHeader("Content-Disposition",
						"attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
				// 实现文件下载
				byte[] buffer = new byte[1024];
				FileInputStream fis = null;
				BufferedInputStream bis = null;
				try {
					fis = new FileInputStream(file);
					bis = new BufferedInputStream(fis);
					OutputStream os = response.getOutputStream();
					int i = bis.read(buffer);
					while (i != -1) {
						os.write(buffer, 0, i);
						i = bis.read(buffer);
					}
					System.out.println("Download  successfully!");
					return "successfully";

				} catch (Exception e) {
					System.out.println("Download  failed!");
					return "failed";

				} finally {
					if (bis != null) {
						try {
							bis.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					if (fis != null) {
						try {
							fis.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		return "";
	}

	/**
	 * 獲取用戶對應的語料批次信息
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping("/getBatch")
	public Object getBatch(Integer userId) {
		logger.info("In the method getBatch of  CorpusController");
		logger.info("userId : " + userId);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Status> status = corpusService.getStatus();
		List<BatchInfo> batch = corpusService.getBatch(userId);

		resultMap.put("batch", batch);
		resultMap.put("corpusStatus", status);

		ServiceResponse<Object> response = new ServiceResponse<>("success", resultMap);
		logger.info("Out the method getBatch of  CorpusController");
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}

	/**
	 * 获取所有语料状态
	 * 
	 * @return
	 */
	@RequestMapping("/getStatus")
	public Object getStatus() {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		List<Status> status = corpusService.getStatus();
		for (int i = 0; i < status.size(); i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", status.get(i).getId());
			map.put("info", status.get(i).getInfo());
			result.add(map);
		}
		ServiceResponse<Object> response = new ServiceResponse<>("success", result);
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}

	/**
	 * 
	 * @param batchId
	 * @param userId
	 * @return
	 */
	@RequestMapping("/getStatistic")
	public Object getStatistic(Integer batchId, Integer userId) {
		logger.info("In the method getStatistic of  CorpusController.......");
		Map<String, Integer> result = new HashMap<String, Integer>();
		Integer save = corpusService.countByUserIdAndStatusIdAndBatch(userId, CorpusStatus.SAVE, batchId);
		Integer init = corpusService.countByUserIdAndStatusIdAndBatch(userId, CorpusStatus.INIT, batchId);
		Integer pass = corpusService.countByUserIdAndStatusIdAndBatch(userId, CorpusStatus.PASS, batchId);
		result.put("save", save);
		result.put("wait", init + pass);
		ServiceResponse<Object> response = new ServiceResponse<>("success", result);
		logger.info("Out the method getStatistic of  CorpusController.......");
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}

	@RequestMapping("/test")
	public Object test() {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("corpus", corpusService.testCorpus());

		ServiceResponse<Object> response = new ServiceResponse<>("success", result);

		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}
}
