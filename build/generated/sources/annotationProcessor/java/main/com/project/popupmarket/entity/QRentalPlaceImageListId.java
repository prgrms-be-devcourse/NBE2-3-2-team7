package com.project.popupmarket.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRentalPlaceImageListId is a Querydsl query type for RentalPlaceImageListId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QRentalPlaceImageListId extends BeanPath<RentalPlaceImageListId> {

    private static final long serialVersionUID = -1282530156L;

    public static final QRentalPlaceImageListId rentalPlaceImageListId = new QRentalPlaceImageListId("rentalPlaceImageListId");

    public final StringPath image = createString("image");

    public final NumberPath<Long> rentalPlaceSeq = createNumber("rentalPlaceSeq", Long.class);

    public QRentalPlaceImageListId(String variable) {
        super(RentalPlaceImageListId.class, forVariable(variable));
    }

    public QRentalPlaceImageListId(Path<? extends RentalPlaceImageListId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRentalPlaceImageListId(PathMetadata metadata) {
        super(RentalPlaceImageListId.class, metadata);
    }

}

