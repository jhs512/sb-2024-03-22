package com.ll.sb20240322.domain.post.postDocument.repository;

import com.ll.sb20240322.domain.post.postDocument.document.PostDocument;
import com.ll.sb20240322.global.app.AppConfig;
import com.ll.sb20240322.global.meilisearch.MeilisearchConfig;
import com.ll.sb20240322.standard.meilisearch.repository.DocumentRepository;
import com.ll.sb20240322.standard.util.Ut;
import com.meilisearch.sdk.Index;
import com.meilisearch.sdk.SearchRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostDocumentRepository extends DocumentRepository<PostDocument> {
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
        String str = Ut.json.toString(postDocument);
        System.out.println("str = " + str);
        getIndex().addDocuments(
                str
        );
    }

    public void clear() {
        getIndex().deleteAllDocuments();
        getIndex().updateSortableAttributesSettings(new String[]{"id"});
    }

    public List<PostDocument> findByOrderByIdDesc() {
        SearchRequest searchRequest = SearchRequest
                .builder()
                .sort(new String[] {"id:asc"})
                .build();
        getIndex().search(searchRequest);

        return null;
    }
}
