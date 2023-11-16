package com.danilkha.service;

import org.danilkha.dao.TopicDao;
import org.danilkha.dto.TopicDto;
import org.danilkha.services.TopicService;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class TopicServiceImpl implements TopicService {

    private final TopicDao topicDao;
    private final String baseTopicPicturesPath;

    public TopicServiceImpl(TopicDao topicDao, String baseTopicPicturesPath) {
        this.topicDao = topicDao;
        this.baseTopicPicturesPath = baseTopicPicturesPath;
    }

    @Override
    public List<TopicDto> getAllTopics() {
        try {
            return topicDao.getAllTopics().stream().map(topicEntity ->
                    new TopicDto(
                            topicEntity.id(),
                            topicEntity.name(),
                            baseTopicPicturesPath+"/"+topicEntity.picture()
                    )).collect(Collectors.toList());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
