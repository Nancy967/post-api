package com.chunancy.post.service;

import com.chunancy.post.dto.PostQueryParams;
import com.chunancy.post.dto.PostRequest;
import com.chunancy.post.model.Post;

import java.util.List;

public interface PostService {

    Integer countPost(PostQueryParams postQueryParams);

    List<Post> getPosts(PostQueryParams postQueryParams);

    Post getPostById(Integer postId);

    Integer createPost(PostRequest postRequest);

    void updatePost(Integer postId, PostRequest postRequest);

    void deletePostById(Integer postId);
}
