package com.ll.sb20240322.domain.post.postDocument.repository;

import com.ll.sb20240322.domain.post.postDocument.document.PostDocument;
import com.ll.sb20240322.global.app.AppConfig;
import com.ll.sb20240322.global.meilisearch.MeilisearchConfig;
import com.ll.sb20240322.standard.util.Ut;
import com.meilisearch.sdk.Index;
import com.meilisearch.sdk.SearchRequest;
import com.meilisearch.sdk.exceptions.MeilisearchException;
import com.meilisearch.sdk.model.Searchable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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
    }

    public List<PostDocument> findByOrderByIdDesc() {
        // 검색 파라미터 설정
        SearchRequest searchRequest =
                new SearchRequest("")
                        .setSort(new String[]{"id:desc"});

        // 문서 검색
        Searchable search = getIndex().search(searchRequest);

        return
                search
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
}
