package com.sparta.myselectshop.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sparta.myselectshop.dto.FolderResponseDto;
import com.sparta.myselectshop.entity.Folder;
import com.sparta.myselectshop.entity.User;
import com.sparta.myselectshop.repository.FolderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FolderService {
	private final FolderRepository folderRepository;

	public void addFolders(List<String> folderNames, User user) {
		List<Folder> existFolderList = folderRepository.finAllByUserAndNameIn(user, folderNames);

		List<Folder> folderList = new ArrayList<>();

		folderNames.forEach(folderName -> {
			if (!isExistFolderName(folderName, existFolderList)) {
				Folder folder = new Folder(folderName, user);
				folderList.add(folder);
			} else {
				throw new IllegalArgumentException("폴더명이 중복되었습니다.");
			}
		});

		folderRepository.saveAll(folderList);
	}

	public List<FolderResponseDto> getFolders(User user) {
		return folderRepository.findAllByUser(user)
			.stream()
			.map(FolderResponseDto::new)
			.toList();
	}

	private static boolean isExistFolderName(String folderName, List<Folder> existFolderList) {
		return existFolderList.stream()
			.anyMatch(folder -> folder.getName().equals(folderName));
	}
}
