package com.sparta.myselectshop.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sparta.myselectshop.dto.ProductMypriceRequestDto;
import com.sparta.myselectshop.dto.ProductRequestDto;
import com.sparta.myselectshop.dto.ProductResponseDto;
import com.sparta.myselectshop.entity.Product;
import com.sparta.myselectshop.entity.User;
import com.sparta.myselectshop.repository.FolderRepository;
import com.sparta.myselectshop.repository.ProductFolderRepository;
import com.sparta.myselectshop.repository.ProductRepository;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@ExtendWith(MockitoExtension.class) // @Mock 사용을 위해 설정합니다.
class ProductServiceTest {
	private static ValidatorFactory validatorFactory;
	private static Validator validator;
	@BeforeAll
	public static void init() {
		validatorFactory = Validation.buildDefaultValidatorFactory();
		validator = validatorFactory.getValidator();
	}

	@AfterAll
	static void close() {
		validatorFactory.close();
	}

	@Mock
	ProductRepository productRepository;

	@Mock
	FolderRepository folderRepository;

	@Mock
	ProductFolderRepository productFolderRepository;

	@Test
	@DisplayName("관심 상품 희망가 - 최저가 이상으로 변경")
	void test1() {
		// given
		Long productId = 100L;
		int myprice = ProductMypriceRequestDto.MIN_MY_PRICE + 3_000_000;

		ProductMypriceRequestDto requestMyPriceDto = new ProductMypriceRequestDto();
		requestMyPriceDto.setMyprice(myprice);

		User user = new User();
		ProductRequestDto requestProductDto = new ProductRequestDto(
			"Apple <b>맥북</b> <b>프로</b> 16형 2021년 <b>M1</b> Max 10코어 실버 (MK1H3KH/A) ",
			"https://shopping-phinf.pstatic.net/main_2941337/29413376619.20220705152340.jpg",
			"https://search.shopping.naver.com/gate.nhn?id=29413376619",
			3515000
		);

		Product product = new Product(requestProductDto, user);

		ProductService productService = new ProductService(productRepository, folderRepository,
			productFolderRepository);

		given(productRepository.findById(productId)).willReturn(Optional.of(product));

		// when
		ProductResponseDto result = productService.updateProduct(productId, requestMyPriceDto);

		// then
		assertEquals(myprice, result.getMyprice());
	}

	@Test
	@DisplayName("관심 상품 희망가 - 최저가 미만으로 변경")
	void test2() {
		// given
		Long productId = 200L;
		int myprice = ProductMypriceRequestDto.MIN_MY_PRICE - 50;

		ProductMypriceRequestDto requestMyPriceDto = new ProductMypriceRequestDto();
		requestMyPriceDto.setMyprice(myprice);

		Set<ConstraintViolation<ProductMypriceRequestDto>> violations = validator.validate(requestMyPriceDto);
		assertNotEquals(0, violations.size());
		String message = violations.iterator().next().getMessage();
		assertEquals("유효하지 않은 관심 가격입니다. 최소 " + ProductMypriceRequestDto.MIN_MY_PRICE + "원 이상으로 설정해 주세요.", message);
	}
}