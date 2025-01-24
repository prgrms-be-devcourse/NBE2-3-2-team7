package com.project.popupmarket.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPlaceRequestId is a Querydsl query type for PlaceRequestId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QPlaceRequestId extends BeanPath<PlaceRequestId> {

    private static final long serialVersionUID = -695563002L;

    public static final QPlaceRequestId placeRequestId = new QPlaceRequestId("placeRequestId");

    public final NumberPath<Long> popupStoreSeq = createNumber("popupStoreSeq", Long.class);

    public final NumberPath<Long> rentalPlaceSeq = createNumber("rentalPlaceSeq", Long.class);

    public QPlaceRequestId(String variable) {
        super(PlaceRequestId.class, forVariable(variable));
    }

    public QPlaceRequestId(Path<? extends PlaceRequestId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPlaceRequestId(PathMetadata metadata) {
        super(PlaceRequestId.class, metadata);
    }

}

