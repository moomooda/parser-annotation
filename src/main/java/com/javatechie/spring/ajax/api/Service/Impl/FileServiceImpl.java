package com.javatechie.spring.ajax.api.Service.Impl;

import java.util.List;

import com.javatechie.spring.ajax.api.Service.Interface.FileService;

public class FileServiceImpl implements FileService {
	
	public static final String SVG_TITLE = "<svg xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\">";
	@Override
	public String createSVGString(List<String> words, List<Integer> starts, List<Integer> ends,
			List<String> relations) {
		return null;
	}

}
