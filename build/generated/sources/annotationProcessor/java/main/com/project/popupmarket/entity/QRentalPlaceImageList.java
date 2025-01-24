package com.project.popupmarket.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRentalPlaceImageList is a Querydsl query type for RentalPlaceImageList
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRentalPlaceImageList extends EntityPathBase<RentalPlaceImageList> {

    private static final long serialVersionUID = -287367783L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRentalPlaceImageList rentalPlaceImageList = new QRentalPlaceImageList("rentalPlaceImageList");

    public final QRentalPlaceImageListId id;

    public final QRentalPlace rentalPlaceSeq;

    public QRentalPlaceImageList(String variable) {
        this(RentalPlaceImageList.class, forVariable(variable), INITS);
    }

    public QRentalPlaceImageList(Path<? extends RentalPlaceImageList> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRentalPlaceImageList(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRentalPlaceImageList(PathMetadata metadata, PathInits inits) {
        this(RentalPlaceImageList.class, metadata, inits);
    }

    public QRentalPlaceImageList(Class<? extends RentalPlaceImageList> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QRentalPlaceImageListId(forProperty("id")) : null;
        this.rentalPlaceSeq = inits.isInitialized("rentalPlaceSeq") ? new QRentalPlace(forProperty("rentalPlaceSeq"), inits.get("rentalPlaceSeq")) : null;
    }

}

