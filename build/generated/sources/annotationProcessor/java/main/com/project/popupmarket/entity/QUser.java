package com.project.popupmarket.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 753782222L;

    public static final QUser user = new QUser("user");

    public final StringPath brand = createString("brand");

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final SetPath<JwtToken, QJwtToken> jwtTokens = this.<JwtToken, QJwtToken>createSet("jwtTokens", JwtToken.class, QJwtToken.class, PathInits.DIRECT2);

    public final StringPath name = createString("name");

    public final StringPath password = createString("password");

    public final SetPath<PopupStore, QPopupStore> popupStores = this.<PopupStore, QPopupStore>createSet("popupStores", PopupStore.class, QPopupStore.class, PathInits.DIRECT2);

    public final StringPath profileImage = createString("profileImage");

    public final SetPath<Receipt, QReceipt> receipts = this.<Receipt, QReceipt>createSet("receipts", Receipt.class, QReceipt.class, PathInits.DIRECT2);

    public final DateTimePath<java.time.Instant> registeredAt = createDateTime("registeredAt", java.time.Instant.class);

    public final SetPath<RentalPlace, QRentalPlace> rentalPlaces = this.<RentalPlace, QRentalPlace>createSet("rentalPlaces", RentalPlace.class, QRentalPlace.class, PathInits.DIRECT2);

    public final StringPath social = createString("social");

    public final StringPath tel = createString("tel");

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

