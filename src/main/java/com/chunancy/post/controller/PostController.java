package com.chunancy.post.controller;

import com.chunancy.post.dto.PostRequest;
import com.chunancy.post.model.Post;
import com.chunancy.post.service.PostService;
import com.chunancy.post.dto.PostQueryParams;
import com.chunancy.post.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@Validated
@RestController
@CrossOrigin(origins = "*")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/posts")
    public ResponseEntity<Page<Post>> getPosts(
            // Filtering
            @RequestParam(required = false) String search,

            // Sorting
            @RequestParam(defaultValue = "last_modified_date") String orderBy,
            @RequestParam(defaultValue = "desc") String sort,

            // Pagination
            @RequestParam(defaultValue = "9") @Max(1000) @Min(0) Integer limit,
            @RequestParam(defaultValue = "0") @Min(0) Integer offset
    ) {
        PostQueryParams postQueryParams = new PostQueryParams();
        postQueryParams.setSearch(search);
        postQueryParams.setOrderBy(orderBy);
        postQueryParams.setSort(sort);
        postQueryParams.setLimit(limit);
        postQueryParams.setOffset(offset);

        // Get post list
        List<Post> postList = postService.getPosts(postQueryParams);

        // Get post total
        Integer total = postService.countPost(postQueryParams);

        // Pagination
        Page<Post> page = new Page<>();
        page.setLimit(limit);
        page.setOffset(offset);
        page.setTotal(total);
        page.setResults(postList);

        return ResponseEntity.status(HttpStatus.OK).body(page);
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<Post> getPost(@PathVariable Integer postId) {
        Post post = postService.getPostById(postId);

        if (post != null) {
            return ResponseEntity.status(HttpStatus.OK).body(post);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/posts")
    public ResponseEntity<Post> createPost(@RequestBody @Valid PostRequest postRequest) {
        Integer postId = postService.createPost(postRequest);

        Post post = postService.getPostById(postId);

        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }

    @PutMapping("/posts/{postId}")
    public ResponseEntity<Post> updatePost(@PathVariable Integer postId,
                                           @RequestBody @Valid PostRequest postRequest) {
        // Check if the post exists
        Post post = postService.getPostById(postId);

        if (post == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // Update post data
        postService.updatePost(postId, postRequest);

        Post updatedPost = postService.getPostById(postId);

        return ResponseEntity.status(HttpStatus.OK).body(updatedPost);
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Integer postId) {
        postService.deletePostById(postId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
