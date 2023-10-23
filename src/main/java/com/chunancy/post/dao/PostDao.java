package com.chunancy.post.dao;

import com.chunancy.post.model.Post;
import com.chunancy.post.dto.PostQueryParams;
import com.chunancy.post.dto.PostRequest;

import java.util.List;

public interface PostDao {

    Integer countPost(PostQueryParams postQueryParams);

    List<Post> getPosts(PostQueryParams postQueryParams);

    Post getPostById(Integer postId);

    Integer createPost(PostRequest postRequest);

    void updatePost(Integer postId, PostRequest postRequest);

    void deletePostById(Integer postId);
}
