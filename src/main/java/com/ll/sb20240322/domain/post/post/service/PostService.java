package com.ll.sb20240322.domain.post.post.service;

import com.ll.sb20240322.domain.post.post.dto.PostDto;
import com.ll.sb20240322.domain.post.post.entity.Post;
import com.ll.sb20240322.domain.post.post.event.AfterPostCreatedEvent;
import com.ll.sb20240322.domain.post.post.event.AfterPostModifiedEvent;
import com.ll.sb20240322.domain.post.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {
    private final PostRepository postRepository;
    private final ApplicationEventPublisher publisher;

    @Transactional
    public Post write(String subject, String body) {
        Post post = postRepository.save(
                Post.builder()
                        .subject(subject)
                        .body(body)
                        .build());

        publisher.publishEvent(new AfterPostCreatedEvent(this, new PostDto(post)));

        return post;
    }

    public long count() {
        return postRepository.count();
    }

    public List<Post> findAll() {
        return postRepository.findByOrderByIdDesc();
    }

    public Optional<Post> findById(long id) {
        return postRepository.findById(id);
    }

    public void modified(Post post) {
        publisher.publishEvent(new AfterPostModifiedEvent(this, new PostDto(post)));
    }
}
