create table public.categories
(
    id          varchar(255) not null primary key,
    created_at  timestamp(6) not null,
    created_by  varchar(255) not null,
    deleted     boolean      not null,
    updated_at  timestamp(6),
    updated_by  varchar(255),
    description text,
    name        varchar(255) not null
        constraint category_name_unique_constraint unique
);

create table public.products
(
    id              varchar(255)   not null primary key,
    created_at      timestamp(6)   not null,
    created_by      varchar(255)   not null,
    deleted         boolean        not null,
    updated_at      timestamp(6),
    updated_by      varchar(255),
    alert_threshold integer        not null,
    description     text,
    name            varchar(255)   not null,
    price           numeric(38, 2) not null,
    reference       varchar(255)   not null constraint products_reference_unique_constraint unique,
    category_id     varchar(255) constraint fk_category_id references public.categories
);


create table public.stock_mvts
(
    id         varchar(255) not null primary key,
    created_at timestamp(6) not null,
    created_by varchar(255) not null,
    deleted    boolean      not null,
    updated_at timestamp(6),
    updated_by varchar(255),
    comment    text,
    date_mvt   date         not null,
    quantity   integer      not null,
    type_mvt   varchar(255) not null constraint stock_mvts_type_mvt_check
        check ((type_mvt)::text = ANY ((ARRAY ['IN'::character varying, 'OUT'::character varying])::text[])),
    product_id varchar(255) constraint fk_product_id references public.products
);

