package com.danilkha.service;

import org.danilkha.dao.*;
import org.danilkha.dto.CommentDto;
import org.danilkha.dto.PostDto;
import org.danilkha.dto.TopicDto;
import org.danilkha.dto.UserDto;
import org.danilkha.entities.CommentEntity;
import org.danilkha.entities.LikeStatusEntity;
import org.danilkha.entities.PostEntity;
import org.danilkha.entities.TopicEntity;
import org.danilkha.services.PostsService;
import org.danilkha.utils.FileProvider;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class PostsServiceImpl implements PostsService {

    private final UserDao userDao;
    private final PostDao postDao;
    private final LikesDao likesDao;
    private final CommentsDao commentsDao;
    private final TopicDao topicDao;
    private final SubscriptionsDao subscriptionsDao;

    private final int pageSize;

    private final String baseTopicPicturesPath;
    private final String basePostsPicturesPath;
    private final String baseAvatarPicturesPath;

    private final FileProvider fileProvider;

    public PostsServiceImpl(
            UserDao userDao, PostDao postDao,
            LikesDao likesDao,
            CommentsDao commentsDao,
            TopicDao topicDao,
            SubscriptionsDao subscriptionsDao,
            int pageSize,
            String baseTopicPicturesPath, String basePostsPicturesPath, String baseAvatarPicturesPath, FileProvider fileProvider) {
        this.userDao = userDao;
        this.postDao = postDao;
        this.likesDao = likesDao;
        this.commentsDao = commentsDao;
        this.topicDao = topicDao;
        this.subscriptionsDao = subscriptionsDao;
        this.pageSize = pageSize;
        this.baseTopicPicturesPath = baseTopicPicturesPath;
        this.basePostsPicturesPath = basePostsPicturesPath;
        this.baseAvatarPicturesPath = baseAvatarPicturesPath;
        this.fileProvider = fileProvider;
    }
    @Override
    public List<PostDto> getUserFeed(UUID userId, int page) {
        try {
            List<UUID> userSubs = subscriptionsDao.getSubscriptionsIds(userId);
            userSubs.add(userId);
            List<PostEntity> postEntities = postDao.getPostByUserList(userSubs, page * pageSize, pageSize);
            return mapPosts(postEntities);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<PostDto> getAllFeed(int page) {
        try {
            List<PostEntity> postEntities = postDao.getPostList(page * pageSize, pageSize);
            return mapPosts(postEntities);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<PostDto> getPostsByTopic(String name) {
        try {
            TopicEntity topicEntity = topicDao.getTopicByName(name).get();
            List<PostEntity> postEntities = postDao.getPostByTopic(topicEntity.id());
            return mapPosts(postEntities);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public PostDto getPostById(UUID postId) {
        try {
            Optional<PostEntity> postEntityOptional = postDao.getPostById(postId);
            if(postEntityOptional.isEmpty()) return null;
            TopicEntity topicEntity = topicDao.getTopicById(postEntityOptional.get().topicId()).get();
            TopicDto topicDto = new TopicDto(
                    topicEntity.id(),
                    topicEntity.name(),
                    baseTopicPicturesPath+"/"+topicEntity.picture()
            );
            UserDto userDto = userDao.getById(postEntityOptional.get().authorId()).toDto(baseAvatarPicturesPath);
            return new PostDto(
                    postEntityOptional.get().id(),
                    postEntityOptional.get().datetime(),
                    userDto,
                    topicDto,
                    buildPostPath(basePostsPicturesPath, postEntityOptional.get().id().toString(), postEntityOptional.get().pictureUrl()),
                    postEntityOptional.get().content()
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<PostDto> getUserPosts(UUID id) {
        try {
            List<PostEntity> postEntities = postDao.getPostByUser(id);
            return mapPosts(postEntities);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UUID writeNewPost(InputStream picture, String fileName, UUID authorId, String text, UUID topicId) {
        PostEntity postEntity = new PostEntity(
                null,
                new Date(),
                authorId,
                topicId,
                fileName,
                text
        );
        try {
            UUID postId = postDao.createNewPost(postEntity);
            if(picture != null){
                String postPath = basePostsPicturesPath+"/"+postId.toString();
                fileProvider.makeDir(postPath);
                fileProvider.savePlainFile(picture, postPath+"/"+fileName);
            }
            return postId;
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void changeLikeState(UUID postId, UUID userId, boolean isLiked) {
        try {
            likesDao.changeLikeStatus(new LikeStatusEntity(
                    postId, userId, isLiked));
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<UUID> getPostLikes(UUID postId) {
        try {
            return likesDao.getPostLikes(postId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<CommentDto> getPostComments(UUID postId) {
        Map<UUID, UserDto> userDtoMap = new HashMap<>();
        try {
            return commentsDao.getPostComments(postId).stream().map(commentEntity -> {
                UserDto userDto = userDtoMap.get(commentEntity.userId());
                if(userDto == null){
                    try {
                        userDto = userDao.getById(commentEntity.userId()).toDto(baseAvatarPicturesPath);
                        userDtoMap.put(commentEntity.userId(), userDto);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
                return new CommentDto(
                        commentEntity.id(),
                        userDto,
                        commentEntity.postId(),
                        commentEntity.replyingId(),
                        commentEntity.date(),
                        commentEntity.text()
                );
            }).collect(Collectors.toList());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addComment(UUID postId, UUID userId, String text, UUID replyingId) {
        try {
            commentsDao.writeComment(new CommentEntity(
                    null,
                    userId,
                    postId,
                    replyingId,
                    new Date(),
                    text
            ));
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private List<PostDto> mapPosts(List<PostEntity> postEntities){
        Map<UUID, TopicDto> topicDtoMap = new HashMap<>();
        Map<UUID, UserDto> userDtoMap = new HashMap<>();
        return postEntities.stream().map(postEntity -> {
            UUID topicID = postEntity.topicId();
            TopicDto topicDto = topicDtoMap.get(topicID);
            if(topicDto == null){
                try {
                    TopicEntity topicEntity = topicDao.getTopicById(topicID).get();
                    topicDto = new TopicDto(
                            topicEntity.id(),
                            topicEntity.name(),
                            baseTopicPicturesPath+"/"+topicEntity.picture()
                    );
                    topicDtoMap.put(topicID, topicDto);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

            UserDto userDto = userDtoMap.get(postEntity.authorId());
            if(userDto == null){
                try {
                    userDto = userDao.getById(postEntity.authorId()).toDto(baseAvatarPicturesPath);
                    userDtoMap.put(postEntity.authorId(), userDto);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            return new PostDto(
                    postEntity.id(),
                    postEntity.datetime(),
                    userDto,
                    topicDto,
                    buildPostPath(basePostsPicturesPath, postEntity.id().toString(), postEntity.pictureUrl()),
                    postEntity.content()
            );
        }).collect(Collectors.toList());
    }

    private String buildPostPath(String basePath, String postId, String filename){
        return basePath+"/"+postId+"/"+filename;
    }
}
