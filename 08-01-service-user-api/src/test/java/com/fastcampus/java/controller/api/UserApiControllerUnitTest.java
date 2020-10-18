package com.fastcampus.java.controller.api;

import com.fastcampus.java.config.ObjectMapperConfig;
import com.fastcampus.java.controller.GetController;
import com.fastcampus.java.model.SearchParam;
import com.fastcampus.java.model.network.Header;
import com.fastcampus.java.model.network.request.UserApiRequest;
import com.fastcampus.java.model.network.response.UserApiResponse;
import com.fastcampus.java.service.UserApiLogicService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserApiController.class)        // WebMvc Test Annotation ( UserApiController 를 테스트)
@AutoConfigureMockMvc                       // MockMvc 자동 설정 Annotation
@Import(ObjectMapperConfig.class)
public class UserApiControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;            // AutoConfigureMockMvc 으로 자동 주입된 mockMvc

    @MockBean
    private UserApiLogicService userApiLogicService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("사용자 생성 테스트")
    public void createTest() throws Exception {
        // given
        URI uri = UriComponentsBuilder.newInstance()
                .path("/api/user")
                .build()
                .toUri();

        var now = LocalDateTime.now();
        var mockReq = new Header<UserApiRequest>();
        var data = new UserApiRequest();
        data.setAccount("userAccount");
        data.setEmail("userAccount@gmail.com");
        data.setPassword("password");
        data.setPhoneNumber("010-1111-2222");
        data.setRegisteredAt(now);
        data.setStatus("REGISTERED");
        mockReq.setData(data);

        var mockRes = new Header<UserApiResponse>();
        var resData = new UserApiResponse();
        resData.setAccount("userAccount");
        resData.setEmail("userAccount@gmail.com");
        resData.setPassword("password");
        resData.setPhoneNumber("010-1111-2222");
        resData.setRegisteredAt(now);
        resData.setStatus("REGISTERED");
        mockRes.setData(resData);

        // userApiLogicService mocking
        given(userApiLogicService.create(mockReq)).willReturn(mockRes);


        // when
        mockMvc.perform(post(uri)  // post 로 테스트
                .content(objectMapper.writeValueAsString(mockReq))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))

                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.account").value("userAccount")) // $.을 통하여 json 에 접근 가능
                .andExpect(jsonPath("$.data.email").value("userAccount@gmail.com")) // $.을 통하여 json 에 접근 가능
                .andExpect(jsonPath("$.data.status").value("REGISTERED")) // $.을 통하여 json 에 접근 가능
                .andDo(print());
    }
}
