package sample.cafekiosk.spring.api.controller.product;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import sample.cafekiosk.spring.api.controller.product.dto.request.ProductCreateServiceRequest;
import sample.cafekiosk.spring.api.service.product.ProductService;
import sample.cafekiosk.spring.api.service.product.response.ProductResponse;
import sample.cafekiosk.spring.domain.product.ProductSellingStatus;
import sample.cafekiosk.spring.domain.product.ProductType;

@WebMvcTest(controllers = {ProductController.class})
class ProductControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockitoBean
  private ProductService productService;

  @DisplayName("신규 상품을 등록한다.")
  @Test
  void createProduct() throws Exception {
    // given
    ProductCreateServiceRequest request = ProductCreateServiceRequest.builder()
        .type(ProductType.HANDMADE)
        .sellingStatus(ProductSellingStatus.SELLING)
        .name("아메리카노")
        .price(4000)
        .build();
    // when
    // then
    mockMvc.perform(post("/api/v1/products/new")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request))
        )
        .andDo(print())
        .andExpect(status().isOk());
  }

  @DisplayName("신규 상품을 등록할때 상품 타입은 필수값이다.")
  @Test
  void createProductWithoutType() throws Exception {
    // given
    ProductCreateServiceRequest request = ProductCreateServiceRequest.builder()
        .sellingStatus(ProductSellingStatus.SELLING)
        .name("아메리카노")
        .price(4000)
        .build();
    // when
    // then
    mockMvc.perform(post("/api/v1/products/new")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request))
        )
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.code").value("400"))
        .andExpect(jsonPath("$.message").value("상품 타입은 필수입니다."))
        .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
        .andExpect(jsonPath("$.data").isEmpty())
    ;
  }

  @DisplayName("신규 상품을 등록할때 상품 판매 상태는 필수값이다.")
  @Test
  void createProductWithoutSellingStatus() throws Exception {
    // given
    ProductCreateServiceRequest request = ProductCreateServiceRequest.builder()
        .type(ProductType.HANDMADE)
        .name("아메리카노")
        .price(4000)
        .build();
    // when
    // then
    mockMvc.perform(post("/api/v1/products/new")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request))
        )
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.code").value("400"))
        .andExpect(jsonPath("$.message").value("상품 판매상태는 필수입니다."))
        .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
        .andExpect(jsonPath("$.data").isEmpty())
    ;
  }

  @DisplayName("신규 상품을 등록할때 상품 이름은 필수값이다.")
  @Test
  void createProductWithoutName() throws Exception {
    // given
    ProductCreateServiceRequest request = ProductCreateServiceRequest.builder()
        .type(ProductType.HANDMADE)
        .sellingStatus(ProductSellingStatus.SELLING)
        .price(4000)
        .build();
    // when
    // then
    mockMvc.perform(post("/api/v1/products/new")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request))
        )
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.code").value("400"))
        .andExpect(jsonPath("$.message").value("상품 이름은 필수입니다."))
        .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
        .andExpect(jsonPath("$.data").isEmpty())
    ;
  }

  @DisplayName("신규 상품을 등록할때 상품 가격은 필수값이다.")
  @Test
  void createProductWithoutPrice() throws Exception {
    // given
    ProductCreateServiceRequest request = ProductCreateServiceRequest.builder()
        .type(ProductType.HANDMADE)
        .name("아메리카노")
        .price(0)
        .sellingStatus(ProductSellingStatus.SELLING)
        .build();
    // when
    // then
    mockMvc.perform(post("/api/v1/products/new")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request))
        )
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.code").value("400"))
        .andExpect(jsonPath("$.message").value("상품 가격은 양수여야 합니다."))
        .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
        .andExpect(jsonPath("$.data").isEmpty())
    ;
  }

  @DisplayName("판매 상품을 조회한다.")
  @Test
  void getSellingProducts() throws Exception {
    // given

    List<ProductResponse> result = List.of();
    when(productService.getSellingProducts())
        .thenReturn(result);

    // when
    // then
    mockMvc.perform(get("/api/v1/products/selling")
        )
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code").value("200"))
        .andExpect(jsonPath("$.message").value("OK"))
        .andExpect(jsonPath("$.status").value("OK"))
        .andExpect(jsonPath("$.data").isArray())
    ;
  }
}