package com.project.popupmarket.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPopupStoreImageList is a Querydsl query type for PopupStoreImageList
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPopupStoreImageList extends EntityPathBase<PopupStoreImageList> {

    private static final long serialVersionUID = -115715039L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPopupStoreImageList popupStoreImageList = new QPopupStoreImageList("popupStoreImageList");

    public final QPopupStoreImageListId id;

    public final QPopupStore popupStoreSeq;

    public QPopupStoreImageList(String variable) {
        this(PopupStoreImageList.class, forVariable(variable), INITS);
    }

    public QPopupStoreImageList(Path<? extends PopupStoreImageList> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPopupStoreImageList(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPopupStoreImageList(PathMetadata metadata, PathInits inits) {
        this(PopupStoreImageList.class, metadata, inits);
    }

    public QPopupStoreImageList(Class<? extends PopupStoreImageList> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QPopupStoreImageListId(forProperty("id")) : null;
        this.popupStoreSeq = inits.isInitialized("popupStoreSeq") ? new QPopupStore(forProperty("popupStoreSeq"), inits.get("popupStoreSeq")) : null;
    }

}

