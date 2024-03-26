package com.ll.sb20240322.domain.post.postDocument.eventListener;

import com.ll.sb20240322.domain.post.post.dto.PostDto;
import com.ll.sb20240322.domain.post.post.event.AfterPostCreatedEvent;
import com.ll.sb20240322.domain.post.post.event.AfterPostModifiedEvent;
import com.ll.sb20240322.domain.post.postDocument.service.PostDocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostDocumentEventListener {
    private final PostDocumentService postDocumentService;

    @EventListener
    @Async
    public void listen(AfterPostCreatedEvent event) {
        PostDto postDto = event.getPostDto();

        postDocumentService.add(postDto);
    }

    @EventListener
    @Async
    public void listen(AfterPostModifiedEvent event) {
        PostDto postDto = event.getPostDto();

        postDocumentService.modify(postDto);
    }
}