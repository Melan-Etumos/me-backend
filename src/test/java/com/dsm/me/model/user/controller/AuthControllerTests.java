package com.dsm.me.model.user.controller;

import com.dsm.me.model.user.dto.UserCreateRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AuthController.class)
public class AuthControllerTests {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Join Success Test")
    public void joinTest() throws Exception {
        String content = objectMapper.writeValueAsString(new UserCreateRequestDto("email@naver.com","password1!", "nickname"));

        mvc.perform(post("/auth")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("@Email Error Test: 이메일 형식을 갖추고 있어야 한다")
    public void emailExceptionJoinTest() throws Exception {
        String content = objectMapper.writeValueAsString(new UserCreateRequestDto("email@naver","password1!", "nickname"));

        mvc.perform(post("/auth")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Password Error Test: 숫자와 특수문자가 하나 이상 들어가야 한다")
    public void textPasswordJoinTest() throws Exception {
        String content = objectMapper.writeValueAsString(new UserCreateRequestDto("email@naver.com","password", "nickname"));

        mvc.perform(post("/auth")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Nickname Error Test: 글자수가 1자 이상 25자 이하여야 한다")
    public void nicknameLengthJoinTest() throws Exception {
        String content = objectMapper.writeValueAsString(new UserCreateRequestDto("email@naver.com","password1!", "가나다라마바사아자차카타파하갸냐댜랴먀뱌샤야쟈챠캬탸퍄햐"));

        mvc.perform(post("/auth")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
