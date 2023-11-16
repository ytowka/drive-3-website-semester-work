package com.danilkha.dao;

import org.danilkha.ConnectionProvider;
import org.danilkha.dao.PostDao;
import org.danilkha.dao.TopicDao;
import org.danilkha.entities.PostEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

public class TestPostDao {

    ConnectionProvider connectionProvider;
    PostDao postDao;
    @BeforeEach
    void setup(){
        connectionProvider = new TestConnectionProvider();
        postDao = new PostDao.Impl(connectionProvider);
    }


    @Test
    void InsertAndGetTest() throws SQLException {
        PostEntity postEntity = new PostEntity(
                null,
                new Date(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                "picture",
                "content"
        );

        UUID postId = postDao.createNewPost(postEntity);

        Optional<PostEntity> gettingPostEntityOptional = postDao.getPostById(postId);

        Assertions.assertTrue(gettingPostEntityOptional.isPresent());

        PostEntity gettingPostEntity = gettingPostEntityOptional.get();

        Assertions.assertEquals(postEntity.authorId(), gettingPostEntity.authorId());
        Assertions.assertEquals(postEntity.content(), gettingPostEntity.content());
        Assertions.assertEquals(postEntity.datetime(), gettingPostEntity.datetime());
        Assertions.assertEquals(postEntity.topicId(), gettingPostEntity.topicId());
        Assertions.assertEquals(postEntity.pictureUrl(), gettingPostEntity.pictureUrl());

    }
}
