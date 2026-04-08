package com.mokal.productionreadyfeature.controller;


import com.mokal.productionreadyfeature.dto.PostDto;
import com.mokal.productionreadyfeature.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController

{
    private final PostService postService;

    @GetMapping
    public List<PostDto> getAllPosts(){
        return postService.getAllPosts();
    }

    @GetMapping("/{postId}")
    public PostDto getById(@PathVariable Long postId){
        return postService.getPostById(postId);
    }


    @PostMapping
    public PostDto createNewPost(@RequestBody PostDto input){
        return postService.createNewPost(input);
    }

}
