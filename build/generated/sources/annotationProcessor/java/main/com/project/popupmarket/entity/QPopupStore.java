package com.project.popupmarket.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPopupStore is a Querydsl query type for PopupStore
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPopupStore extends EntityPathBase<PopupStore> {

    private static final long serialVersionUID = -1196869800L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPopupStore popupStore = new QPopupStore("popupStore");

    public final StringPath description = createString("description");

    public final DatePath<java.time.LocalDate> endDate = createDate("endDate", java.time.LocalDate.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final SetPath<PopupStoreImageList, QPopupStoreImageList> popupStoreImageLists = this.<PopupStoreImageList, QPopupStoreImageList>createSet("popupStoreImageLists", PopupStoreImageList.class, QPopupStoreImageList.class, PathInits.DIRECT2);

    public final QUser popupUserSeq;

    public final SetPath<PlaceRequest, QPlaceRequest> rentalPlaces = this.<PlaceRequest, QPlaceRequest>createSet("rentalPlaces", PlaceRequest.class, QPlaceRequest.class, PathInits.DIRECT2);

    public final DatePath<java.time.LocalDate> startDate = createDate("startDate", java.time.LocalDate.class);

    public final StringPath targetAgeGroup = createString("targetAgeGroup");

    public final StringPath targetLocation = createString("targetLocation");

    public final StringPath thumbnail = createString("thumbnail");

    public final StringPath title = createString("title");

    public final StringPath type = createString("type");

    public final NumberPath<Integer> wishArea = createNumber("wishArea", Integer.class);

    public QPopupStore(String variable) {
        this(PopupStore.class, forVariable(variable), INITS);
    }

    public QPopupStore(Path<? extends PopupStore> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPopupStore(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPopupStore(PathMetadata metadata, PathInits inits) {
        this(PopupStore.class, metadata, inits);
    }

    public QPopupStore(Class<? extends PopupStore> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.popupUserSeq = inits.isInitialized("popupUserSeq") ? new QUser(forProperty("popupUserSeq")) : null;
    }

}

