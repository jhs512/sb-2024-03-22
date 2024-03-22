package com.ll.sb20240322.domain.post.postDocument.repository;

import com.ll.sb20240322.domain.post.postDocument.document.PostDocument;
import com.ll.sb20240322.global.meilisearch.MeilisearchConfig;
import com.ll.sb20240322.standard.util.Ut;
import com.meilisearch.sdk.Index;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PostDocumentRepository {
    private final MeilisearchConfig meilisearchConfig;
    private Index postIndex;

    private Index getIndex() {
        if (postIndex == null) postIndex = meilisearchConfig.meilisearchClient().index("post");

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
}
