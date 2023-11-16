package org.danilkha.dao;

import org.danilkha.ConnectionProvider;
import org.danilkha.entities.PostEntity;
import org.example.orm.Insertion;
import org.example.orm.ORM;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PostDao {

    UUID createNewPost(PostEntity postEntity) throws SQLException;

    List<PostEntity> getPostByUserList(List<UUID> userIds, int from, int limit) throws SQLException;

    List<PostEntity> getPostByTopic(UUID topicId) throws SQLException;

    Optional<PostEntity> getPostById(UUID postId) throws SQLException;

    List<PostEntity> getPostByUser(UUID userId) throws SQLException;

    class Impl implements PostDao{

        private final ConnectionProvider connectionProvider;

        //language=SQL
        String GET_BY_USERS = "SELECT id, datetime, author_id, topic_id, picture_url, content FROM posts " +
                "WHERE author_id IN (%s) " +
                "ORDER BY datetime DESC OFFSET ? LIMIT ?";

        //language=SQL
        String GET_BY_TOPIC = "SELECT * FROM posts WHERE topic_id = ?";

        //language=SQL
        String GET_BY_ID = "SELECT * FROM posts WHERE id = ?";

        //language=SQL
        String GET_BY_AUTHOR = "SELECT * FROM posts WHERE author_id = ?";

        public Impl(ConnectionProvider connectionProvider) {
            this.connectionProvider = connectionProvider;
        }


        @Override
        public UUID createNewPost(PostEntity postEntity) throws SQLException {
            PreparedStatement statement = Insertion.prepareInsertStatement(connectionProvider.provide(), postEntity);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return (UUID) resultSet.getObject("id");
        }

        @Override
        public List<PostEntity> getPostByUserList(List<UUID> userIds, int from, int limit) throws SQLException {
            String userIdsString = String.join(",", Collections.nCopies(userIds.size(), "?"));
            String sql = GET_BY_USERS.formatted(userIdsString);
            PreparedStatement statement = connectionProvider.provide().prepareStatement(sql);
            for (int i = 0; i < userIds.size(); i++) {
                statement.setObject(i + 1, userIds.get(i));
            }
            statement.setInt(userIds.size() + 1, from);
            statement.setInt(userIds.size() + 2, limit);
            ResultSet resultSet = statement.executeQuery();
            return ORM.parseResultSetList(resultSet, PostEntity.class);
        }

        @Override
        public List<PostEntity> getPostByTopic(UUID topicId) throws SQLException {
            PreparedStatement statement = connectionProvider.provide().prepareStatement(GET_BY_TOPIC);
            statement.setObject(1, topicId);
            ResultSet resultSet = statement.executeQuery();
            return ORM.parseResultSetList(resultSet, PostEntity.class);
        }

        @Override
        public Optional<PostEntity> getPostById(UUID postId) throws SQLException {
            PreparedStatement statement = connectionProvider.provide().prepareStatement(GET_BY_ID);
            statement.setObject(1, postId);
            ResultSet resultSet = statement.executeQuery();
            return ORM.parseOptionalResultSet(resultSet, PostEntity.class);
        }

        @Override
        public List<PostEntity> getPostByUser(UUID userId) throws SQLException {
            PreparedStatement statement = connectionProvider.provide().prepareStatement(GET_BY_AUTHOR);
            statement.setObject(1, userId);
            ResultSet resultSet = statement.executeQuery();
            return ORM.parseResultSetList(resultSet, PostEntity.class);
        }
    }

}
