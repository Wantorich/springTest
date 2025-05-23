package sample.cafekiosk.spring.api.service.product;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sample.cafekiosk.spring.api.controller.product.dto.request.ProductCreateRequest;
import sample.cafekiosk.spring.api.service.product.response.ProductResponse;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductRepository;
import sample.cafekiosk.spring.domain.product.ProductSellingStatus;

/**
 * readOnly = true : 읽기 전용
 * CRUD에서 CUD 동작 X / only READ
 * JPA : CUD 스냅샷 저장, 변경감지 X (성능 향상)
 *
 * CQRS - Command / Query -> Read 가 압도적으로 많이 불림
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository productRepository;

  public List<ProductResponse> getSellingProducts() {
    List<Product> products = productRepository.findAllBySellingStatusIn(
        ProductSellingStatus.forDisplay());
    return products.stream()
        .map(ProductResponse::of)
        .toList();
  }

  // 동시성 이슈
  @Transactional
  public ProductResponse createProduct(ProductCreateRequest request) {
    String nextProductNumber = createNextProductNumber();

    Product product = request.toEntity(nextProductNumber);
    Product savedProduct = productRepository.save(product);

    return ProductResponse.of(savedProduct);
  }

  private String createNextProductNumber() {
    String latestProductNumber = productRepository.findLatestProductNumber();
    if (latestProductNumber == null) {
      return "001";
    }
    int nextProductNumber = Integer.parseInt(latestProductNumber) + 1;
    return String.format("%03d", nextProductNumber);
  }
}
