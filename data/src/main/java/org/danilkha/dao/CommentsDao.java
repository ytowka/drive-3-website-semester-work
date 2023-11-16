package org.danilkha.dao;

import org.danilkha.ConnectionProvider;
import org.danilkha.entities.CommentEntity;
import org.example.orm.Insertion;
import org.example.orm.ORM;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public interface CommentsDao {

    void writeComment(CommentEntity commentEntity) throws SQLException;
    List<CommentEntity> getPostComments(UUID postId) throws SQLException;

    class Impl implements CommentsDao{

        private final ConnectionProvider connectionProvider;
        public Impl(ConnectionProvider connectionProvider){
            this.connectionProvider = connectionProvider;
        }

        //language=SQL
        private static final String GET_BY_POST_ID = "SELECT * FROM comments WHERE post_id = ?";

        @Override
        public void writeComment(CommentEntity commentEntity) throws SQLException {
            PreparedStatement statement = Insertion.prepareInsertStatement(connectionProvider.provide(), commentEntity);
            statement.execute();
        }

        @Override
        public List<CommentEntity> getPostComments(UUID postId) throws SQLException {
            PreparedStatement statement = connectionProvider.provide().prepareStatement(GET_BY_POST_ID);
            ResultSet resultSet = statement.executeQuery();
            return ORM.parseResultSetList(resultSet, CommentEntity.class);
        }
    }
}
