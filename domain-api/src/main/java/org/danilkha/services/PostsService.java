package org.danilkha.services;

import org.danilkha.dto.PostDto;

import java.util.List;

public interface PostsService {

    List<PostDto> getUserFeed();
}
