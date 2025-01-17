package com.sparta.springresttemplateserver.dto;

import com.sparta.springresttemplateserver.entity.Item;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ItemResponseDto {
	private final List<Item> items = new ArrayList<>();

	public void addItem(Item item) {
		items.add(item);
	}
}