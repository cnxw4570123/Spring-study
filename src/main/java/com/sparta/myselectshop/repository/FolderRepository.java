package com.sparta.myselectshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sparta.myselectshop.entity.Folder;
import com.sparta.myselectshop.entity.User;

public interface FolderRepository extends JpaRepository<Folder, Long> {
	@Query("select f from Folder f where f.user = :user and f.name in :folderNames")
	List<Folder> finAllByUserAndNameIn(User user, List<String> folderNames);

	List<Folder> findAllByUser(User user);
}
