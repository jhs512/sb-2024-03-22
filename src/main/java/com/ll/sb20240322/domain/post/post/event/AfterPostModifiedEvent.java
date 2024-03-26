package com.ll.sb20240322.domain.post.post.event;

import com.ll.sb20240322.domain.post.post.dto.PostDto;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public class AfterPostModifiedEvent extends ApplicationEvent {
    @Getter
    private final PostDto postDto;

    public AfterPostModifiedEvent(Object source, PostDto postDto) {
        super(source);
        this.postDto = postDto;
    }
}
