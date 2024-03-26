package com.ll.sb20240322.domain.post.postDocument.repository;

import com.ll.sb20240322.domain.post.postDocument.document.PostDocument;
import com.ll.sb20240322.global.app.AppConfig;
import com.ll.sb20240322.global.meilisearch.MeilisearchConfig;
import com.ll.sb20240322.standard.util.Ut;
import com.meilisearch.sdk.Index;
import com.meilisearch.sdk.SearchRequest;
import com.meilisearch.sdk.exceptions.MeilisearchException;
import com.meilisearch.sdk.model.SearchResult;
import com.meilisearch.sdk.model.Searchable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PostDocumentRepository {
    private final MeilisearchConfig meilisearchConfig;
    private Index postIndex;

    private String getIndexName() {
        String indexName = "post";

        if (AppConfig.isTest()) indexName += "Test";

        return indexName;
    }

    private Index getIndex() {
        if (postIndex == null) postIndex = meilisearchConfig.meilisearchClient().index(getIndexName());

        return postIndex;
    }

    public void save(PostDocument postDocument) {
        getIndex().addDocuments(
                Ut.json.toString(postDocument)
        );
    }

    public void clear() {
        getIndex().deleteAllDocuments();
        getIndex().resetSortableAttributesSettings();
        getIndex().updateSortableAttributesSettings(new String[]{"id"});
        getIndex().updateFilterableAttributesSettings(new String[]{"createTimeStamp"});
    }

    public List<PostDocument> findByOrderByIdDesc() {
        // 검색 파라미터 설정
        SearchRequest searchRequest =
                new SearchRequest("")
                        .setSort(new String[]{"id:desc"});

        // 문서 검색
        Searchable searchable = getIndex().search(searchRequest);

        return
                searchable
                        .getHits()
                        .stream()
                        .map(
                                hit -> Ut.json.toObject(hit, PostDocument.class)
                        )
                        .toList();

    }

    public Optional<PostDocument> findById(long id) {
        try {
            PostDocument document = getIndex().getDocument(String.valueOf(id), PostDocument.class);
            return Optional.ofNullable(document);
        } catch (MeilisearchException ignored) {
        }

        return Optional.empty();
    }

    public Page<PostDocument> findByKw(String kw, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        SearchRequest searchRequest =
                new SearchRequest(kw)
                        .setAttributesToSearchOn(new String[]{"subject", "body"})
                        .setLimit(pageable.getPageSize())
                        .setOffset((int) pageable.getOffset());

        if (startDate != null && endDate != null) {
            searchRequest
                    .setFilter(new String[]{
                            "createTimeStamp >= %d AND createTimeStamp <= %d"
                                    .formatted(
                                    Ut.time.toTimeStamp(startDate),
                                    Ut.time.toTimeStamp(endDate)
                            )
                    });
        }

        SearchResult searchResult = (SearchResult) getIndex().search(searchRequest);

        List<PostDocument> postDocuments = searchResult
                .getHits()
                .stream()
                .map(
                        hit -> Ut.json.toObject(hit, PostDocument.class)
                )
                .toList();

        return new PageImpl<>(postDocuments, pageable, searchResult.getEstimatedTotalHits());
    }
}
