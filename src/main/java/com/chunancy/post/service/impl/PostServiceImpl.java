package com.chunancy.post.service.impl;

import com.chunancy.post.model.Post;
import com.chunancy.post.service.PostService;
import com.chunancy.post.dao.PostDao;
import com.chunancy.post.dto.PostQueryParams;
import com.chunancy.post.dto.PostRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PostServiceImpl implements PostService {

    @Autowired
    private PostDao postDao;

    @Override
    public Integer countPost(PostQueryParams postQueryParams) {
        return postDao.countPost(postQueryParams);
    }

    @Override
    public List<Post> getPosts(PostQueryParams postQueryParams) {
        return postDao.getPosts(postQueryParams);
    }

    @Override
    public Post getPostById(Integer postId) {
        return postDao.getPostById(postId);
    }

    @Override
    public Integer createPost(PostRequest postRequest) {
        return postDao.createPost(postRequest);
    }

    @Override
    public void updatePost(Integer postId, PostRequest postRequest) {
        postDao.updatePost(postId, postRequest);
    }

    @Override
    public void deletePostById(Integer postId) {
        postDao.deletePostById(postId);
    }
}
