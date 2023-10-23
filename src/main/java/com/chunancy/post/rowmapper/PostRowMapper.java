package com.chunancy.post.rowmapper;

import com.chunancy.post.model.Post;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PostRowMapper implements RowMapper<Post> {

    @Override
    public Post mapRow(ResultSet resultSet, int i) throws SQLException {
        Post post = new Post();

        post.setPostId(resultSet.getInt("post_id"));
        post.setTitle(resultSet.getString("title"));
        post.setImageUrl(resultSet.getString("image_url"));
        post.setDescription(resultSet.getString("description"));
        post.setCreatedDate(resultSet.getTimestamp("created_date"));
        post.setLastModifiedDate(resultSet.getTimestamp("last_modified_date"));

        return post;
    }
}
