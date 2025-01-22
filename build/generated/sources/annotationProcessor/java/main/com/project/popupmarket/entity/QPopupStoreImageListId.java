package com.project.popupmarket.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPopupStoreImageListId is a Querydsl query type for PopupStoreImageListId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QPopupStoreImageListId extends BeanPath<PopupStoreImageListId> {

    private static final long serialVersionUID = 466999580L;

    public static final QPopupStoreImageListId popupStoreImageListId = new QPopupStoreImageListId("popupStoreImageListId");

    public final StringPath image = createString("image");

    public final NumberPath<Long> popupStoreSeq = createNumber("popupStoreSeq", Long.class);

    public QPopupStoreImageListId(String variable) {
        super(PopupStoreImageListId.class, forVariable(variable));
    }

    public QPopupStoreImageListId(Path<? extends PopupStoreImageListId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPopupStoreImageListId(PathMetadata metadata) {
        super(PopupStoreImageListId.class, metadata);
    }

}

