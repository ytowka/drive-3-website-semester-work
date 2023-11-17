package org.danilkha.dao;

import org.danilkha.ConnectionProvider;
import org.danilkha.entities.LikeStatusEntity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public interface LikesDao {
    void changeLikeStatus(LikeStatusEntity likeStatus) throws SQLException;
    boolean getPostLikeStatus(UUID postId, UUID userId) throws SQLException;
    List<UUID> getPostLikes(UUID postId) throws SQLException;

    class Impl implements LikesDao{


        private final ConnectionProvider connectionProvider;
        public Impl(ConnectionProvider connectionProvider){
            this.connectionProvider = connectionProvider;
        }

        //language=SQL
        private static final String UPDATE = "INSERT INTO post_likes (post_id, user_id, is_liked) VALUES (?, ?, ?) ON CONFLICT (post_id, user_id) DO UPDATE SET is_liked = ?";

        //language=SQL
        private static final String IS_LIKED = "SELECT is_liked FROM post_likes WHERE post_id = ? AND user_id = ?";

        //language=SQL
        private static final String LIKE_COUNT = "SELECT user_id FROM post_likes WHERE post_id = ? AND is_liked = true";

        @Override
        public void changeLikeStatus(LikeStatusEntity likeStatus) throws SQLException {
            try {
                PreparedStatement statement = connectionProvider.provide().prepareStatement(UPDATE);
                statement.setObject(1, likeStatus.postId());
                statement.setObject(2, likeStatus.userId());
                statement.setBoolean(3, likeStatus.isLiked());
                statement.setBoolean(4, likeStatus.isLiked());
                statement.executeUpdate();
            }catch (Exception e){
                e.printStackTrace();
            }

        }

        @Override
        public boolean getPostLikeStatus(UUID postId, UUID userId) throws SQLException {
            PreparedStatement statement = connectionProvider.provide().prepareStatement(IS_LIKED);
            statement.setObject(1, postId);
            statement.setObject(2, userId);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getBoolean("is_liked");
            }
            return false;
        }

        @Override
        public List<UUID> getPostLikes(UUID postId) throws SQLException {

            try {
                PreparedStatement statement = connectionProvider.provide().prepareStatement(LIKE_COUNT);
                statement.setObject(1, postId);
                ResultSet resultSet = statement.executeQuery();
                List<UUID> likes = new ArrayList<>();
                while (resultSet.next()){
                    likes.add((UUID) resultSet.getObject("user_id"));
                }
                return likes;
            }catch (Exception e){
                e.printStackTrace();
                throw e;
            }

        }
    }
}
