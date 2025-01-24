package com.project.popupmarket.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QJwtTokenId is a Querydsl query type for JwtTokenId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QJwtTokenId extends BeanPath<JwtTokenId> {

    private static final long serialVersionUID = 1853987312L;

    public static final QJwtTokenId jwtTokenId = new QJwtTokenId("jwtTokenId");

    public final StringPath token = createString("token");

    public final NumberPath<Long> userSeq = createNumber("userSeq", Long.class);

    public QJwtTokenId(String variable) {
        super(JwtTokenId.class, forVariable(variable));
    }

    public QJwtTokenId(Path<? extends JwtTokenId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QJwtTokenId(PathMetadata metadata) {
        super(JwtTokenId.class, metadata);
    }

}

