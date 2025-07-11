package sample.cafekiosk.spring.api.controller.order;

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
import sample.cafekiosk.spring.api.controller.order.request.OrderCreateServiceRequest;
import sample.cafekiosk.spring.api.service.order.OrderService;

@WebMvcTest(controllers = OrderController.class)
class OrderControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockitoBean
  private OrderService orderService;

  @DisplayName("신규 주문을 등록한다.")
  @Test
  void createOrder() throws Exception {
    // given
    OrderCreateServiceRequest request = OrderCreateServiceRequest.builder()
        .productNumbers(List.of("001"))
        .build();
    // when
    // then
    mockMvc.perform(post("/api/v1/orders/new")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request))
        )
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code").value("200"))
        .andExpect(jsonPath("$.message").value("OK"))
        .andExpect(jsonPath("$.status").value("OK"))
        ;
  }

  @DisplayName("신규 주문을 등록할때 상품 번호는 1개 이상이어야한다.")
  @Test
  void createOrderWithEmptyProductNumbers() throws Exception {
    // given
    OrderCreateServiceRequest request = OrderCreateServiceRequest.builder()
        .productNumbers(List.of())
        .build();
    // when
    // then
    mockMvc.perform(post("/api/v1/orders/new")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request))
        )
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.code").value("400"))
        .andExpect(jsonPath("$.message").value("상품 번호 리스트는 필수입니다."))
        .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
    ;
  }

}