package com.ll.sb20240322.domain.post.post.entity;

import com.ll.sb20240322.global.jpa.entity.BaseTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
@Getter
@Setter
@ToString(callSuper = true)
public class Post extends BaseTime {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String subject;
    private String body;
}
