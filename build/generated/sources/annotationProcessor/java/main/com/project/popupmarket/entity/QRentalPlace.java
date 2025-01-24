package com.project.popupmarket.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRentalPlace is a Querydsl query type for RentalPlace
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRentalPlace extends EntityPathBase<RentalPlace> {

    private static final long serialVersionUID = 2049795808L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRentalPlace rentalPlace = new QRentalPlace("rentalPlace");

    public final StringPath addrDetail = createString("addrDetail");

    public final StringPath address = createString("address");

    public final StringPath area = createString("area");

    public final StringPath capacity = createString("capacity");

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath infra = createString("infra");

    public final StringPath name = createString("name");

    public final StringPath nearbyAgeGroup = createString("nearbyAgeGroup");

    public final SetPath<PlaceRequest, QPlaceRequest> popupStores = this.<PlaceRequest, QPlaceRequest>createSet("popupStores", PlaceRequest.class, QPlaceRequest.class, PathInits.DIRECT2);

    public final NumberPath<java.math.BigDecimal> price = createNumber("price", java.math.BigDecimal.class);

    public final SetPath<Receipt, QReceipt> receipts = this.<Receipt, QReceipt>createSet("receipts", Receipt.class, QReceipt.class, PathInits.DIRECT2);

    public final DateTimePath<java.time.Instant> registeredAt = createDateTime("registeredAt", java.time.Instant.class);

    public final SetPath<RentalPlaceImageList, QRentalPlaceImageList> rentalPlaceImageLists = this.<RentalPlaceImageList, QRentalPlaceImageList>createSet("rentalPlaceImageLists", RentalPlaceImageList.class, QRentalPlaceImageList.class, PathInits.DIRECT2);

    public final QUser rentalUserSeq;

    public final StringPath status = createString("status");

    public final StringPath thumbnail = createString("thumbnail");

    public QRentalPlace(String variable) {
        this(RentalPlace.class, forVariable(variable), INITS);
    }

    public QRentalPlace(Path<? extends RentalPlace> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRentalPlace(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRentalPlace(PathMetadata metadata, PathInits inits) {
        this(RentalPlace.class, metadata, inits);
    }

    public QRentalPlace(Class<? extends RentalPlace> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.rentalUserSeq = inits.isInitialized("rentalUserSeq") ? new QUser(forProperty("rentalUserSeq")) : null;
    }

}

