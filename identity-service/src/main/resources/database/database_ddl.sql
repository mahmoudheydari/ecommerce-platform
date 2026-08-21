
CREATE TABLE IF NOT EXISTS public.tb_location_info
(
    id bigint NOT NULL,
    city character varying(255) COLLATE pg_catalog."default" ,
    full_address character varying(255) COLLATE pg_catalog."default" ,
    house_no character varying(255) COLLATE pg_catalog."default" ,
    CONSTRAINT tb_location_info_pkey PRIMARY KEY (id)
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.tb_location_info
    OWNER to lab_group;


-- Table: public.tb_contact_info

-- DROP TABLE IF EXISTS public.tb_contact_info;

CREATE TABLE IF NOT EXISTS public.tb_contact_info
(
    id bigint NOT NULL,
    cell_number character varying(255) COLLATE pg_catalog."default",
    email character varying(255) COLLATE pg_catalog."default",
    phone_number character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT tb_contact_info_pkey PRIMARY KEY (id)
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.tb_contact_info
    OWNER to lab_group;


-- Table: public.tb_permission

-- DROP TABLE IF EXISTS public.tb_permission;

CREATE TABLE IF NOT EXISTS public.tb_permission
(
    id bigint NOT NULL,
    create_date timestamp(6) without time zone,
    created_by character varying(255) COLLATE pg_catalog."default",
    last_modified_by character varying(255) COLLATE pg_catalog."default",
    last_modified_date timestamp(6) without time zone,
    operation character varying(255) COLLATE pg_catalog."default" NOT NULL,
    target_type character varying(255) COLLATE pg_catalog."default" NOT NULL,
    target_scope character varying(255) COLLATE pg_catalog."default" NOT NULL,
    title character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT tb_permission_pkey PRIMARY KEY (id),
    CONSTRAINT uk_operation_target_type_target_scope UNIQUE (operation,target_type,target_scope)

    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.tb_permission
    OWNER to lab_group;



-- Table: public.tb_role

-- DROP TABLE IF EXISTS public.tb_role;

CREATE TABLE IF NOT EXISTS public.tb_role
(
    id bigint NOT NULL,
    title character varying(255) COLLATE pg_catalog."default" NOT NULL,
    description character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT tb_role_pkey PRIMARY KEY (id),
    CONSTRAINT uk_role_title UNIQUE (title)
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.tb_role
    OWNER to lab_group;



-- Table: public.tb_role_permission

-- DROP TABLE IF EXISTS public.tb_role_permission;

CREATE TABLE IF NOT EXISTS public.tb_role_permission
(
    id bigint NOT NULL,
    fk_role bigint NOT NULL,
    fk_permission bigint NOT NULL,
    CONSTRAINT tb_role_permission_pkey PRIMARY KEY (id),
    CONSTRAINT uk_fk_role_fk_permission UNIQUE (fk_role, fk_permission),
    CONSTRAINT "fk_permission" FOREIGN KEY (fk_permission)
    REFERENCES public.tb_permission (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION,
    CONSTRAINT "fk_role" FOREIGN KEY (fk_role)
    REFERENCES public.tb_role (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.tb_role_permission
    OWNER to lab_group;




-- Table: public.tb_user

-- DROP TABLE IF EXISTS public.tb_user;

CREATE TABLE IF NOT EXISTS public.tb_user
(
    id bigint NOT NULL,
    create_date timestamp(6) without time zone,
    created_by character varying(255) COLLATE pg_catalog."default",
    last_modified_by character varying(255) COLLATE pg_catalog."default",
    last_modified_date timestamp(6) without time zone,
    account_non_expired boolean,
    account_non_locked boolean,
    credentials_non_expired boolean,
    enabled boolean,
    password character varying(255) COLLATE pg_catalog."default" NOT NULL,
    username character varying(255) COLLATE pg_catalog."default" NOT NULL,
    fk_location_info bigint NOT NULL,
    fk_contact_info bigint NOT NULL,
    CONSTRAINT tb_user_pkey PRIMARY KEY (id),
    CONSTRAINT uk_user_name UNIQUE (username),
    CONSTRAINT "fk_location_info" FOREIGN KEY (fk_location_info)
    REFERENCES public.tb_location_info (id) MATCH SIMPLE
                             ON UPDATE NO ACTION
                             ON DELETE NO ACTION,
    CONSTRAINT "fk_contact_info" FOREIGN KEY (fk_contact_info)
    REFERENCES public.tb_contact_info (id) MATCH SIMPLE
                             ON UPDATE NO ACTION
                             ON DELETE NO ACTION
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.tb_user
    OWNER to lab_group;





-- Table: public.tb_user_role

-- DROP TABLE IF EXISTS public.tb_user_role;

CREATE TABLE IF NOT EXISTS public.tb_user_role
(
    id bigint NOT NULL,
    create_date timestamp(6) without time zone,
    created_by character varying(255) COLLATE pg_catalog."default",
    last_modified_by character varying(255) COLLATE pg_catalog."default",
    last_modified_date timestamp(6) without time zone,
    fk_role bigint NOT NULL,
    fk_user bigint NOT NULL,
    CONSTRAINT tb_user_role_pkey PRIMARY KEY (id),
    CONSTRAINT uk_fk_user_fk_role UNIQUE (fk_user,fk_role),
    CONSTRAINT "fk_user" FOREIGN KEY (fk_user)
    REFERENCES public.tb_user (id) MATCH SIMPLE
                             ON UPDATE NO ACTION
                             ON DELETE NO ACTION,
    CONSTRAINT "fk_role" FOREIGN KEY (fk_role)
    REFERENCES public.tb_role (id) MATCH SIMPLE
                             ON UPDATE NO ACTION
                             ON DELETE NO ACTION
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.tb_user_role
    OWNER to lab_group;

create sequence if not exists role_seq start 1;
create sequence if not exists cont_seq start 1;
create sequence if not exists loc_seq start 1;
create sequence if not exists prm_seq start 1;
create sequence if not exists role_per_seq start 1;
create sequence if not exists user_seq start 1;
create sequence if not exists user_role_seq start 1;