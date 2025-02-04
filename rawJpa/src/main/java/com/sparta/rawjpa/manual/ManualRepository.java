package com.sparta.rawjpa.manual;

import java.util.HashMap;

import org.springframework.stereotype.Component;

import lombok.Setter;

@Setter
public class ManualRepository {
	// DB 테이블
	private HashMap<Long, String> dataTable;

	public String find(Long id) {
		return dataTable.getOrDefault(id, "");
	}

	public Long save(String data) {
		var newId = Long.valueOf(dataTable.size());
		dataTable.put(newId, data);
		return newId;
	}
}
