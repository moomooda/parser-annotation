package com.javatechie.spring.ajax.api;

public class CorpusStatus {
	/**已保存**/
	public static final Integer  SAVE = 6;  //已保存
	
	/**跳过**/
	public static final Integer OTHER = 5;  //其他
	public static final Integer PASS = 4;  // 暂跳
	public static final Integer MEANINGLESS = 3;  // 无意义
	public static final Integer LONG = 2;  //过长
	
	/**未标注**/
	public static final Integer INIT = 1;	  //未标注

}
