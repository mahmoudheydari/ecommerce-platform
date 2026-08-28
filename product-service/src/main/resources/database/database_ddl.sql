-- Table: public.tb_category

-- DROP TABLE IF EXISTS public.tb_category;

CREATE TABLE IF NOT EXISTS public.tb_category
(
    id bigint NOT NULL,
    active boolean,
    description character varying(255) COLLATE pg_catalog."default",
    name character varying(255) COLLATE pg_catalog."default",
    sort_order integer,
    fk_parent bigint,
    CONSTRAINT tb_category_pkey PRIMARY KEY (id),
    CONSTRAINT "fk_parent_category" FOREIGN KEY (fk_parent)
    REFERENCES public.tb_category (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.tb_category
    OWNER to lab_group;
-- Table: public.tb_currency

-- DROP TABLE IF EXISTS public.tb_currency;

CREATE TABLE IF NOT EXISTS public.tb_currency
(
    id bigint NOT NULL,
    code character varying(255) COLLATE pg_catalog."default",
    fraction_digits integer,
    name character varying(255) COLLATE pg_catalog."default",
    symbol character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT tb_currency_pkey PRIMARY KEY (id)
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.tb_currency
    OWNER to lab_group;

-- Table: public.tb_inventory

-- DROP TABLE IF EXISTS public.tb_inventory;

CREATE TABLE IF NOT EXISTS public.tb_inventory
(
    id bigint NOT NULL,
    quantity integer,
    reserved_quantity integer,
    CONSTRAINT tb_inventory_pkey PRIMARY KEY (id)
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.tb_inventory
    OWNER to lab_group;

-- Table: public.tb_price

-- DROP TABLE IF EXISTS public.tb_price;

CREATE TABLE IF NOT EXISTS public.tb_price
(
    id bigint NOT NULL,
    amount numeric(38,2),
    discount integer,
    fk_currency bigint,
    CONSTRAINT tb_price_pkey PRIMARY KEY (id),
    CONSTRAINT "fk_currency" FOREIGN KEY (fk_currency)
    REFERENCES public.tb_currency (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.tb_price
    OWNER to lab_group;

-- Table: public.tb_product

-- DROP TABLE IF EXISTS public.tb_product;

CREATE TABLE IF NOT EXISTS public.tb_product
(
    id bigint NOT NULL,
    description character varying(255) COLLATE pg_catalog."default",
    name character varying(255) COLLATE pg_catalog."default",
    slug character varying(255) COLLATE pg_catalog."default",
    status_type character varying(255) COLLATE pg_catalog."default",
    fk_category bigint,
    fk_inventory bigint,
    fk_price bigint,
    CONSTRAINT tb_product_pkey PRIMARY KEY (id),
    CONSTRAINT "uk_fk_inventory" UNIQUE (fk_inventory),
    CONSTRAINT "uk_fk_price" UNIQUE (fk_price),
    CONSTRAINT "fk_category" FOREIGN KEY (fk_category)
    REFERENCES public.tb_category (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION,
    CONSTRAINT "fk_price" FOREIGN KEY (fk_price)
    REFERENCES public.tb_price (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION,
    CONSTRAINT "fk_inventory" FOREIGN KEY (fk_inventory)
    REFERENCES public.tb_inventory (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION,
    CONSTRAINT tb_product_status_type_check
    CHECK (status_type IN ('DRAFT', 'PUBLISHED', 'HIDDEN', 'DELETED', 'ACTIVE', 'OUT_OF_STOCK', 'ARCHIVED', 'DISCONTINUED'))
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.tb_product
    OWNER to lab_group;

-- ====================== tb_category ======================
CREATE INDEX IF NOT EXISTS idx_tb_category_fk_parent
    ON public.tb_category (fk_parent);

CREATE INDEX IF NOT EXISTS idx_tb_category_active_sort
    ON public.tb_category (active, sort_order);


-- ====================== tb_currency ======================
CREATE UNIQUE INDEX IF NOT EXISTS idx_tb_currency_code
    ON public.tb_currency (code);


-- ====================== tb_price ======================
CREATE INDEX IF NOT EXISTS idx_tb_price_fk_currency
    ON public.tb_price (fk_currency);


-- ====================== tb_product ======================
CREATE INDEX IF NOT EXISTS idx_tb_product_fk_category
    ON public.tb_product (fk_category);

CREATE INDEX IF NOT EXISTS idx_tb_product_status_type
    ON public.tb_product (status_type);

CREATE UNIQUE INDEX IF NOT EXISTS idx_tb_product_slug
    ON public.tb_product (slug);

CREATE INDEX IF NOT EXISTS idx_tb_product_name
    ON public.tb_product (name);