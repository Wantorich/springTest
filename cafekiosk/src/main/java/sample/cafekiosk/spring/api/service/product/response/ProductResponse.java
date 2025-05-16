package sample.cafekiosk.spring.api.service.product.response;

import lombok.Builder;
import lombok.Getter;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductSellingStatus;
import sample.cafekiosk.spring.domain.product.ProductType;

@Getter
public class ProductResponse {

  private final Long id;
  private final String productNumber;
  private final ProductType productType;
  private final ProductSellingStatus sellingStatus;
  private final String name;
  private final int price;

  @Builder
  private ProductResponse(Long id, String productNumber, ProductType productType,
      ProductSellingStatus sellingStatus, String name, int price) {
    this.id = id;
    this.productNumber = productNumber;
    this.productType = productType;
    this.sellingStatus = sellingStatus;
    this.name = name;
    this.price = price;
  }

  public static ProductResponse of(Product product) {
    return ProductResponse.builder()
        .id(product.getId())
        .productNumber(product.getProductNumber())
        .productType(product.getType())
        .sellingStatus(product.getSellingStatus())
        .name(product.getName())
        .price(product.getPrice())
        .build();
  }
}
