package com.sparta.myselectshop.service;

import java.util.List;
import java.util.NoSuchElementException;

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
import com.sparta.myselectshop.entity.Product;
import com.sparta.myselectshop.entity.User;
import com.sparta.myselectshop.entity.UserRoleEnum;
import com.sparta.myselectshop.naver.dto.ItemDto;
import com.sparta.myselectshop.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
	private final ProductRepository productRepository;

	public ProductResponseDto createProduct(ProductRequestDto requestDto, User user) {
		Product product = productRepository.save(new Product(requestDto, user));
		return new ProductResponseDto(product);
	}

	@Transactional
	public ProductResponseDto updateProduct(Long id, ProductMypriceRequestDto requestDto) {
		Product product = productRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("해당 상품을 찾을 수 없습니다."));

		product.update(requestDto);
		return new ProductResponseDto(product);
	}

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

}
