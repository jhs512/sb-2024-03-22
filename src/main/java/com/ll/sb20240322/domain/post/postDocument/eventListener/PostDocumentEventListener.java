package com.ll.sb20240322.domain.post.postDocument.eventListener;

import com.ll.sb20240322.domain.post.post.dto.PostDto;
import com.ll.sb20240322.domain.post.postDocument.service.PostDocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostDocumentEventListener {
    private final PostDocumentService postDocumentService;

//    @EventListener
//    @Async
//    public void listen(AfterPostCreatedEvent event) {
//        PostDto postDto = event.getPostDto();
//
//        postDocumentService.add(postDto);
//    }
//
//    @EventListener
//    @Async
//    public void listen(AfterPostModifiedEvent event) {
//        PostDto postDto = event.getPostDto();
//
//        postDocumentService.modify(postDto);
//    }

    @KafkaListener(topics = "AfterPostCreatedEvent", groupId = "1")
    public void consumeAfterPostCreatedEvent(PostDto postDto) {
        postDocumentService.add(postDto);
    }

    @KafkaListener(topics = "AfterPostCreatedEvent.DLT", groupId = "1")
    public void consumeAfterPostCreatedEventDLT(byte[] in) {
        String message = new String(in);
        System.out.println("failed message: " + message);
    }

    @KafkaListener(topics = "AfterPostModifiedEvent", groupId = "1")
    public void consumeAfterPostModifiedEvent(PostDto postDto) {
        postDocumentService.modify(postDto);
    }

    @KafkaListener(topics = "AfterPostModifiedEvent.DLT", groupId = "1")
    public void consumeAfterPostModifiedEventDLT(byte[] in) {
        String message = new String(in);
        System.out.println("failed message: " + message);
    }
}