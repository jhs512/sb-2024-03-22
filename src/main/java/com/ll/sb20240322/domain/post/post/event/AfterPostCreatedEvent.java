package com.ll.sb20240322.domain.post.post.event;

import com.ll.sb20240322.domain.post.post.entity.Post;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public class AfterPostCreatedEvent extends ApplicationEvent {
    @Getter
    private final Post post;

    public AfterPostCreatedEvent(Object source, Post post) {
        super(source);
        this.post = post;
    }
}
