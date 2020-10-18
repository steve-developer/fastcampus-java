package com.fastcampus.java.controller;

import com.fastcampus.java.model.SearchParam;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PostController.class)        // WebMvc Test Annotation ( PostController 를 테스트 안맞는 경우 동작 안합니다. )
@AutoConfigureMockMvc                   // MockMvc 자동 설정 Annotation
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;            // AutoConfigureMockMvc 으로 자동 주입된 mockMvc

    @Test                               // JUnit Test 설정 Annotation
    @DisplayName("postMethod 테스트")    // 테스트명 미적용시 메소드 이름
    public void postMethodTest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        // given
        URI uri = UriComponentsBuilder.newInstance()
                .path("/api/postMethod")
                .build()
                .toUri();

        var searchParam = new SearchParam();
        searchParam.setAccount("account");
        searchParam.setEmail("email");
        searchParam.setPage(10);

        // when
        mockMvc.perform(post(uri)  // post 로 테스트
                .content(objectMapper.writeValueAsString(searchParam))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))

            // then
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.account").value("account")) // $.을 통하여 json 에 접근 가능
            .andExpect(jsonPath("$.email").value("email")) // $.을 통하여 json 에 접근 가능
            .andExpect(jsonPath("$.page").value("10")) // $.을 통하여 json 에 접근 가능
            .andDo(print());
    }
}
