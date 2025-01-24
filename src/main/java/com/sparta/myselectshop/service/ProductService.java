package com.sparta.myselectshop.service;

import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.myselectshop.dto.ProductMypriceRequestDto;
import com.sparta.myselectshop.dto.ProductRequestDto;
import com.sparta.myselectshop.dto.ProductResponseDto;
import com.sparta.myselectshop.entity.Folder;
import com.sparta.myselectshop.entity.Product;
import com.sparta.myselectshop.entity.ProductFolder;
import com.sparta.myselectshop.entity.User;
import com.sparta.myselectshop.entity.UserRoleEnum;
import com.sparta.myselectshop.exception.ProductNotFoundException;
import com.sparta.myselectshop.naver.dto.ItemDto;
import com.sparta.myselectshop.repository.FolderRepository;
import com.sparta.myselectshop.repository.ProductFolderRepository;
import com.sparta.myselectshop.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
	private final ProductRepository productRepository;
	private final FolderRepository folderRepository;
	private final ProductFolderRepository productFolderRepository;
	private final MessageSource messageSource;

	public ProductResponseDto createProduct(ProductRequestDto requestDto, User user) {
		Product product = productRepository.save(new Product(requestDto, user));
		return new ProductResponseDto(product);
	}

	@Transactional
	public ProductResponseDto updateProduct(Long id, ProductMypriceRequestDto requestDto) {
		Product product = productRepository.findById(id)
			.orElseThrow(() -> new ProductNotFoundException(
				messageSource.getMessage("not.found.product",
						null,
					"Not Found Product",
					Locale.getDefault()
					)
			));

		product.update(requestDto);
		return new ProductResponseDto(product);
	}

	@Transactional(readOnly = true)
	public Page<ProductResponseDto> getProducts(User user, int page, int size, String sortBy, boolean isAsc) {
		Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
		Sort sort = Sort.by(direction, sortBy);
		Pageable pageable = PageRequest.of(page, size, sort);

		UserRoleEnum userRole = user.getRole();

		Page<Product> products;

		if (userRole == UserRoleEnum.USER) {
			products = productRepository.findAllByUser(user, pageable);
		} else {
			products = productRepository.findAll(pageable);
		}

		return products.map(ProductResponseDto::new);
	}

	@Transactional
	public void updateBySearch(Long id, ItemDto itemDto) {
		Product product = productRepository.findById(id)
			.orElseThrow(() -> new NoSuchElementException("해당 상품은 존재하지 않습니다."));

		product.updateByItemDto(itemDto);
	}

	public void addFolder(long productId, Long folderId, User user) {
		Product product = productRepository.findById(productId)
			.orElseThrow(() -> new NoSuchElementException("해당 상품이 존재하지 않습니다."));

		Folder folder = folderRepository.findById(folderId)
			.orElseThrow(() -> new NoSuchElementException("해당 폴더가 존재하지 않습니다."));

		if (!product.getUser().getId().equals(user.getId())
			|| !folder.getUser().getId().equals(user.getId())) {
			throw new IllegalArgumentException("회원님의 관심상품이 아니거나, 회원님의 폴더가 아닙니다.");
		}

		Optional<ProductFolder> overlapFolder = productFolderRepository.findByProductAndFolder(product, folder);

		if (overlapFolder.isPresent()) {
			throw new IllegalArgumentException("중복된 폴더입니다.");
		}

		productFolderRepository.save(new ProductFolder(product, folder));
	}

	public Page<ProductResponseDto> getProductsInFolder(Long folderId, int page, int size, String sortBy, boolean isAsc,
		User user) {
		Pageable pageable = PageRequest.of(page, size,
			Sort.by(isAsc ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy));

		return productRepository.findAllByUserAndProductFolders_FolderId(
			user, folderId, pageable).map(ProductResponseDto::new);
	}
}
