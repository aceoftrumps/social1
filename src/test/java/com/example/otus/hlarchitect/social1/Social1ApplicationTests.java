package com.example.otus.hlarchitect.social1;

import com.example.otus.hlarchitect.social1.controllers.MainController;
import com.example.otus.hlarchitect.social1.repository.PostRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.BDDMockito.given;


@WebMvcTest(MainController.class)
class Social1ApplicationTests {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private PostRepository postRepository;

    @Test
    public void testHelloWorld() throws Exception{

        given(postRepository.getPosts()).willReturn(10);

        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.equalTo("Hello Brave New World 10")));
    }
}
