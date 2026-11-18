package me.scpark.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.scpark.domain.Article;
import me.scpark.dto.AddArticleRequest;
import me.scpark.repository.BlogRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BlogApiControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper; //Jave Object <-> JSON String

    @Autowired
    private BlogRepository blogRepository;

    @DisplayName("블로그 글 추가에 성공한다")
    @Test
    public void addArticle() throws Exception{
        //준비 given : 데이터 준비, 요청 URL
        String title ="11월 4일 마지막 수업시간";
        String content="JUnit으로 블로그 글 생성요청 처리가 성공하는지 실습했다";
        AddArticleRequest req = new AddArticleRequest(title,content);
        String reqBody = objectMapper.writeValueAsString(req);
        String url = "/api/articles";

        //실행 when : 게시글 등록 요청
        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(reqBody));

        //검증 then : DB에 레코드 1개가 등록되어 있고 그 레코드의 내용이 위의 것과 동일한지.
        result.andExpect(status().isCreated());
        List<Article> articles=blogRepository.findAll();
        assertThat(articles.size()).isEqualTo(1);
        assertThat(articles.get(0).getTitle()).isEqualTo(title);
        assertThat(articles.get(0).getContent()).isEqualTo(content);
    }
}