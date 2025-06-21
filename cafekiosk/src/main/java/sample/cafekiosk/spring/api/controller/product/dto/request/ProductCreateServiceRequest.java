package sample.cafekiosk.spring.api.controller.product.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sample.cafekiosk.spring.domain.product.ProductSellingStatus;
import sample.cafekiosk.spring.domain.product.ProductType;

@Getter
@NoArgsConstructor
public class ProductCreateServiceRequest {

  @NotNull(message = "상품 타입은 필수입니다.")
  private ProductType type;

  @NotNull(message = "상품 판매상태는 필수입니다.")
  private ProductSellingStatus sellingStatus;

  @NotBlank(message = "상품 이름은 필수입니다.")
//  @NotNull // "" " "
//  @NotEmpty // "  "
//  @Max(20) -> 이런 도메인 특화 로직은 서비스나 도메인에서 검증하는게 책임 분리 측면에서 나을 수도 있음
  private String name;

  @Positive(message = "상품 가격은 양수여야 합니다.")
  private int price;

  @Builder
  public ProductCreateServiceRequest(ProductType type, ProductSellingStatus sellingStatus, String name,
                                     int price) {
    this.type = type;
    this.sellingStatus = sellingStatus;
    this.name = name;
    this.price = price;
  }

  public sample.cafekiosk.spring.api.service.product.request.ProductCreateServiceRequest toServiceRequest() {
    return sample.cafekiosk.spring.api.service.product.request.ProductCreateServiceRequest.builder()
        .type(type)
        .sellingStatus(sellingStatus)
        .name(name)
        .price(price)
        .build();
  }
}
