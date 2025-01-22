package com.project.popupmarket.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPlaceRequest is a Querydsl query type for PlaceRequest
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPlaceRequest extends EntityPathBase<PlaceRequest> {

    private static final long serialVersionUID = -228656501L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPlaceRequest placeRequest = new QPlaceRequest("placeRequest");

    public final QPlaceRequestId id;

    public final QPopupStore popupStoreSeq;

    public final QRentalPlace rentalPlaceSeq;

    public QPlaceRequest(String variable) {
        this(PlaceRequest.class, forVariable(variable), INITS);
    }

    public QPlaceRequest(Path<? extends PlaceRequest> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPlaceRequest(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPlaceRequest(PathMetadata metadata, PathInits inits) {
        this(PlaceRequest.class, metadata, inits);
    }

    public QPlaceRequest(Class<? extends PlaceRequest> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QPlaceRequestId(forProperty("id")) : null;
        this.popupStoreSeq = inits.isInitialized("popupStoreSeq") ? new QPopupStore(forProperty("popupStoreSeq"), inits.get("popupStoreSeq")) : null;
        this.rentalPlaceSeq = inits.isInitialized("rentalPlaceSeq") ? new QRentalPlace(forProperty("rentalPlaceSeq"), inits.get("rentalPlaceSeq")) : null;
    }

}

