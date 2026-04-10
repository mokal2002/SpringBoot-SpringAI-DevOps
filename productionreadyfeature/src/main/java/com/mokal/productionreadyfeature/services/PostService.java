package com.mokal.productionreadyfeature.services;

import com.mokal.productionreadyfeature.dto.PostDto;

import java.util.List;

public interface PostService {

    List<PostDto> getAllPosts();


    PostDto createNewPost(PostDto input);


    PostDto getPostById(Long postId);

    PostDto updatePost(PostDto postDto, Long postId);
}
