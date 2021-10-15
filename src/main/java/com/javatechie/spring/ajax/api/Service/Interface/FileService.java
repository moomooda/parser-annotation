package com.javatechie.spring.ajax.api.Service.Interface;

import java.util.List;

public interface FileService {

	String createSVGString(List<String> words, List<Integer> starts, List<Integer> ends, List<String> relations);
}
