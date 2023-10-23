package com.chunancy.post.dao.impl;

import com.chunancy.post.dao.PostDao;
import com.chunancy.post.model.Post;
import com.chunancy.post.rowmapper.PostRowMapper;
import com.chunancy.post.dto.PostQueryParams;
import com.chunancy.post.dto.PostRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PostDaoImpl implements PostDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Integer countPost(PostQueryParams postQueryParams) {
        String sql = "SELECT count(*) FROM post WHERE 1=1";

        Map<String, Object> map = new HashMap<>();

        // 查詢條件
        sql = addFilteringSql(sql, map, postQueryParams);

        Integer total = namedParameterJdbcTemplate.queryForObject(sql, map, Integer.class);

        return total;
    }

    @Override
    public List<Post> getPosts(PostQueryParams postQueryParams) {
        String sql = "SELECT post_id, title, image_url, description, " +
                "created_date, last_modified_date " +
                "FROM post WHERE 1=1";

        Map<String, Object> map = new HashMap<>();

        // Filtering
        sql = addFilteringSql(sql, map, postQueryParams);

        // Sorting
        sql = sql + " ORDER BY " + postQueryParams.getOrderBy() + " " + postQueryParams.getSort();

        // 分頁
        sql = sql + " LIMIT :limit OFFSET :offset";
        map.put("limit", postQueryParams.getLimit());
        map.put("offset", postQueryParams.getOffset());

        List<Post> postList = namedParameterJdbcTemplate.query(sql, map, new PostRowMapper());

        return postList;
    }

    @Override
    public Post getPostById(Integer postId) {
        String sql = "SELECT post_id, title, image_url, description, " +
                "created_date, last_modified_date " +
                "FROM post WHERE post_id = :postId";

        Map<String, Object> map = new HashMap<>();
        map.put("postId", postId);

        List<Post> postList = namedParameterJdbcTemplate.query(sql, map, new PostRowMapper());

        if (postList.size() > 0) {
            return postList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public Integer createPost(PostRequest postRequest) {
        String sql = "INSERT INTO post(title, image_url, description, created_date, last_modified_date) " +
                "VALUES (:title, :imageUrl, :description, :createdDate, :lastModifiedDate)";

        Map<String, Object> map = new HashMap<>();
        map.put("title", postRequest.getTitle());
        map.put("imageUrl", postRequest.getImageUrl());
        map.put("description", postRequest.getDescription());

        Date now = new Date();
        map.put("createdDate", now);
        map.put("lastModifiedDate", now);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        int postId = keyHolder.getKey().intValue();

        return postId;
    }

    @Override
    public void updatePost(Integer postId, PostRequest postRequest) {
        String sql = "UPDATE post SET title = :title, image_url = :imageUrl, " +
                "description = :description, last_modified_date = :lastModifiedDate" +
                " WHERE post_id = :postId ";

        Map<String, Object> map = new HashMap<>();
        map.put("postId", postId);

        map.put("title", postRequest.getTitle());
        map.put("imageUrl", postRequest.getImageUrl());
        map.put("description", postRequest.getDescription());

        map.put("lastModifiedDate", new Date());

        namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public void deletePostById(Integer postId) {
        String sql = "DELETE FROM post WHERE post_id = :postId ";

        Map<String, Object> map = new HashMap<>();
        map.put("postId", postId);

        namedParameterJdbcTemplate.update(sql, map);
    }

    private String addFilteringSql(String sql, Map<String, Object> map, PostQueryParams postQueryParams) {

        if (postQueryParams.getSearch() != null) {
            sql = sql + " AND title LIKE :search";
            map.put("search", "%" + postQueryParams.getSearch() + "%");
        }

        return sql;
    }
}
