package com.chunancy.post.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.chunancy.post.dto.PostRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    // Get post
    @Test
    public void getPost_success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/posts/{postId}", 1);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", equalTo("New Seasonal Delights")))
                .andExpect(jsonPath("$.imageUrl", notNullValue()))
                .andExpect(jsonPath("$.description", notNullValue()))
                .andExpect(jsonPath("$.createdDate", notNullValue()))
                .andExpect(jsonPath("$.lastModifiedDate", notNullValue()));
    }

    @Test
    public void getPost_notFound() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/posts/{postId}", 20000);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(404));
    }

    // Create post
    @Transactional
    @Test
    public void createPost_success() throws Exception {
        PostRequest postRequest = new PostRequest();
        postRequest.setTitle("test food post");
        postRequest.setDescription("hello test");

        String json = objectMapper.writeValueAsString(postRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(201))
                .andExpect(jsonPath("$.title", equalTo("test food post")))
                .andExpect(jsonPath("$.imageUrl", nullValue()))
                .andExpect(jsonPath("$.description", equalTo("hello test")))
                .andExpect(jsonPath("$.createdDate", notNullValue()))
                .andExpect(jsonPath("$.lastModifiedDate", notNullValue()));
    }

    @Transactional
    @Test
    public void createPost_illegalArgument() throws Exception {
        PostRequest postRequest = new PostRequest();
        postRequest.setTitle("test food post");

        String json = objectMapper.writeValueAsString(postRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(400));
    }

    // Update post
    @Transactional
    @Test
    public void updatePost_success() throws Exception {
        PostRequest postRequest = new PostRequest();
        postRequest.setTitle("test food post");
        postRequest.setDescription("hello test");

        String json = objectMapper.writeValueAsString(postRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/posts/{postId}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.title", equalTo("test food post")))
                .andExpect(jsonPath("$.imageUrl", nullValue()))
                .andExpect(jsonPath("$.description", equalTo("hello test")))
                .andExpect(jsonPath("$.createdDate", notNullValue()))
                .andExpect(jsonPath("$.lastModifiedDate", notNullValue()));
    }

    @Transactional
    @Test
    public void updatePost_illegalArgument() throws Exception {
        PostRequest postRequest = new PostRequest();
        postRequest.setTitle("test food post");

        String json = objectMapper.writeValueAsString(postRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/posts/{postId}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(400));

    }

    @Transactional
    @Test
    public void updatePost_postNotFound() throws Exception {
        PostRequest postRequest = new PostRequest();
        postRequest.setTitle("test food post");
        postRequest.setImageUrl("http://test.com");
        postRequest.setDescription("hello test");

        String json = objectMapper.writeValueAsString(postRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/posts/{postId}", 20000)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(404));
    }

    // Delete post
    @Transactional
    @Test
    public void deletePost_success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/posts/{postId}", 2);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(204));
    }

    @Transactional
    @Test
    public void deletePost_deleteNonExistingPost() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/posts/{postId}", 20000);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(204));
    }

    // Get posts
    @Test
    public void getPosts() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/posts");

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.limit", notNullValue()))
                .andExpect(jsonPath("$.offset", notNullValue()))
                .andExpect(jsonPath("$.total", notNullValue()))
                .andExpect(jsonPath("$.results", hasSize(9)));
    }

    @Test
    public void getPosts_filtering() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/posts")
                .param("search", "Wednesdays");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.limit", notNullValue()))
                .andExpect(jsonPath("$.offset", notNullValue()))
                .andExpect(jsonPath("$.total", notNullValue()))
                .andExpect(jsonPath("$.results", hasSize(1)));
    }

    @Test
    public void getPosts_sorting() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/posts")
                .param("orderBy", "created_date")
                .param("sort", "desc");

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.limit", notNullValue()))
                .andExpect(jsonPath("$.offset", notNullValue()))
                .andExpect(jsonPath("$.total", notNullValue()))
                .andExpect(jsonPath("$.results", hasSize(9)))
                .andExpect(jsonPath("$.results[0].postId", equalTo(20)))
                .andExpect(jsonPath("$.results[1].postId", equalTo(19)))
                .andExpect(jsonPath("$.results[2].postId", equalTo(18)))
                .andExpect(jsonPath("$.results[3].postId", equalTo(17)))
                .andExpect(jsonPath("$.results[4].postId", equalTo(16)))
                .andExpect(jsonPath("$.results[5].postId", equalTo(15)))
                .andExpect(jsonPath("$.results[6].postId", equalTo(14)))
                .andExpect(jsonPath("$.results[7].postId", equalTo(13)))
                .andExpect(jsonPath("$.results[8].postId", equalTo(12)));
    }

    @Test
    public void getPosts_pagination() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/posts")
                .param("limit", "2")
                .param("offset", "2");

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.limit", notNullValue()))
                .andExpect(jsonPath("$.offset", notNullValue()))
                .andExpect(jsonPath("$.total", notNullValue()))
                .andExpect(jsonPath("$.results", hasSize(2)))
                .andExpect(jsonPath("$.results[0].postId", equalTo(18)))
                .andExpect(jsonPath("$.results[1].postId", equalTo(17)));
    }
}