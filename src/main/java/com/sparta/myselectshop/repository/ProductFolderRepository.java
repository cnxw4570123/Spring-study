package com.sparta.myselectshop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sparta.myselectshop.entity.Folder;
import com.sparta.myselectshop.entity.Product;
import com.sparta.myselectshop.entity.ProductFolder;

public interface ProductFolderRepository extends JpaRepository<ProductFolder, Long> {
	@Query("select pf from ProductFolder pf where pf.folder = :folder and pf.product = :product")
	Optional<ProductFolder> findByProductAndFolder(Product product, Folder folder);
}
