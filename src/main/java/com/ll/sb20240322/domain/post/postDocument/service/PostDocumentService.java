package com.ll.sb20240322.domain.post.postDocument.service;

import com.ll.sb20240322.domain.post.post.dto.PostDto;
import com.ll.sb20240322.domain.post.postDocument.document.PostDocument;
import com.ll.sb20240322.domain.post.postDocument.repository.PostDocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    public Optional<PostDocument> findById(long id) {
        return postDocumentRepository.findById(id);
    }

    public Page<PostDocument> findByKw(String kw, Pageable pageable) {
        return findByKw(kw, null, null, pageable);
    }

    public Page<PostDocument> findByKw(String kw, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        return postDocumentRepository.findByKw(kw, startDate, endDate, pageable);
    }
}
