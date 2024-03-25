package com.ll.sb20240322.domain.post.postDocument.repository;

import com.ll.sb20240322.domain.post.postDocument.document.PostDocument;
import com.ll.sb20240322.global.app.AppConfig;
import com.ll.sb20240322.global.meilisearch.MeilisearchConfig;
import com.ll.sb20240322.standard.util.Ut;
import com.meilisearch.sdk.Index;
import com.meilisearch.sdk.model.Results;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

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
    }

    public List<PostDocument> findByOrderByIdDesc() {
        Results<PostDocument> documents = getIndex()
                .getDocuments(PostDocument.class);

        return Arrays.stream(documents.getResults())
                .toList();
    }
}
