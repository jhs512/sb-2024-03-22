package com.ll.sb20240322.domain.post.postDocument.service;

import com.ll.sb20240322.domain.post.postDocument.document.PostDocument;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class PostDocumentServiceTest {
    @Autowired
    private PostDocumentService postDocumentService;

    @Test
    @DisplayName("findAll")
    void t1() {
        // 모든 포스트를 찾은 후 결과 검증
        List<PostDocument> postDocuments = postDocumentService.findAll();
        assertThat(postDocuments).hasSize(3);

        // 순서대로 각 포스트 검증
        assertPostDocument(postDocuments.get(0), 3L, "subject3", "body3");
        assertPostDocument(postDocuments.get(1), 2L, "subject2", "body2");
        assertPostDocument(postDocuments.get(2), 1L, "subject1", "body1");
    }

    private void assertPostDocument(PostDocument post, Long expectedId, String expectedSubject, String expectedBody) {
        assertThat(post.getId()).isEqualTo(expectedId);
        assertThat(post.getSubject()).isEqualTo(expectedSubject);
        assertThat(post.getBody()).isEqualTo(expectedBody);
    }
}
