-- Table: public.tb_cart

-- DROP TABLE IF EXISTS public.tb_cart;

CREATE TABLE IF NOT EXISTS public.tb_cart
(
    id bigint NOT NULL,
    fk_user bigint,
    cart_status character varying(255) COLLATE pg_catalog."default",
    created_at timestamp(6) without time zone,
    update_at timestamp(6) without time zone,
    CONSTRAINT tb_cart_pkey PRIMARY KEY (id),
    CONSTRAINT tb_cart_status_type_check
    CHECK (cart_status IN ('ACTIVE', 'ABANDONED', 'CHECKED_OUT'))
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.tb_cart
    OWNER to lab_group;

-- Table: public.tb_cart_item

-- DROP TABLE IF EXISTS public.tb_cart_item;

CREATE TABLE IF NOT EXISTS public.tb_cart_item
(
    id bigint NOT NULL,
    fk_product bigint,
    quantity integer,
    unit_price numeric(38,2),,
    fk_cart bigint,
    CONSTRAINT tb_cart_item_pkey PRIMARY KEY (id),
    CONSTRAINT "fk_to_tb_cart" FOREIGN KEY (fk_cart)
    REFERENCES public.tb_cart (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.tb_cart_item
    OWNER to lab_group;



create sequence if not exists cart_seq start 1;
create sequence if not exists cart_item_seq start 1;