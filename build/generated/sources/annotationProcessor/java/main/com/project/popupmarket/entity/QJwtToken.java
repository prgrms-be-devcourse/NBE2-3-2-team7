package com.project.popupmarket.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QJwtToken is a Querydsl query type for JwtToken
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QJwtToken extends EntityPathBase<JwtToken> {

    private static final long serialVersionUID = -1213711883L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QJwtToken jwtToken = new QJwtToken("jwtToken");

    public final DateTimePath<java.time.Instant> createdAt = createDateTime("createdAt", java.time.Instant.class);

    public final DateTimePath<java.time.Instant> expiresAt = createDateTime("expiresAt", java.time.Instant.class);

    public final QJwtTokenId id;

    public final QUser userSeq;

    public QJwtToken(String variable) {
        this(JwtToken.class, forVariable(variable), INITS);
    }

    public QJwtToken(Path<? extends JwtToken> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QJwtToken(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QJwtToken(PathMetadata metadata, PathInits inits) {
        this(JwtToken.class, metadata, inits);
    }

    public QJwtToken(Class<? extends JwtToken> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QJwtTokenId(forProperty("id")) : null;
        this.userSeq = inits.isInitialized("userSeq") ? new QUser(forProperty("userSeq")) : null;
    }

}

