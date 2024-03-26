package com.ll.sb20240322.domain.post.post.controller;

import com.ll.sb20240322.domain.post.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ap1/v1/posts")
@RequiredArgsConstructor
public class ApiV1PostController {
    private final PostService postService;
}
