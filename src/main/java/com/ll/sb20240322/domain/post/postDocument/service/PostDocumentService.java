package com.ll.sb20240322.domain.post.postDocument.service;

import com.ll.sb20240322.domain.post.post.dto.PostDto;
import com.ll.sb20240322.domain.post.postDocument.document.PostDocument;
import com.ll.sb20240322.domain.post.postDocument.repository.PostDocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostDocumentService {
    private final PostDocumentRepository postDocumentRepository;

    public void add(PostDto postDto) {
        PostDocument postDocument = new PostDocument(postDto);

        postDocumentRepository.save(postDocument);
    }

    public void clear() {
        postDocumentRepository.clear();
    }

    public List<PostDocument> findAll() {
        return postDocumentRepository.findByOrderByIdDesc();
    }
}
