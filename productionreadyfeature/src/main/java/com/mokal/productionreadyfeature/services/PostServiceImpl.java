package com.mokal.productionreadyfeature.services;

import com.mokal.productionreadyfeature.dto.PostDto;
import com.mokal.productionreadyfeature.entities.PostEntity;
import com.mokal.productionreadyfeature.exceptions.ResourceNotFoundException;
import com.mokal.productionreadyfeature.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<PostDto> getAllPosts() {
        return postRepository.findAll().stream().map(postEntity -> modelMapper.map(postEntity, PostDto.class)).collect(Collectors.toList());
    }

    @Override
    public PostDto createNewPost(PostDto input) {
        PostEntity postEntity=modelMapper.map(input,PostEntity.class);
        return modelMapper.map(postRepository.save(postEntity), PostDto.class);
    }

    @Override
    public PostDto getPostById(Long postId) {
        PostEntity postEntity=postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Resourse Not Found"));
        return modelMapper.map(postEntity, PostDto.class);
    }
}
