-- Database: ProductShop

-- DROP DATABASE IF EXISTS "ProductShop";

/*
CREATE DATABASE "ProductShop"
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'English_United States.1251'
    LC_CTYPE = 'English_United States.1251'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;
    */

-- Table: public.Invoice

-- DROP TABLE IF EXISTS public."Invoice";

CREATE TABLE IF NOT EXISTS public."Invoice"
(
    id character varying NOT NULL,
    sum double precision,
    created date,
    CONSTRAINT primary_key_id_invoice PRIMARY KEY (id)
);

-- Table: public.Phone

-- DROP TABLE IF EXISTS public."Phone";

CREATE TABLE IF NOT EXISTS public."Phone"
(
    id character varying NOT NULL,
    model character varying,
    manufacturer character varying,
    count integer,
    price double precision,
    core_numbers integer,
    battery_power integer,
    invoice_id character varying,
    CONSTRAINT primary_key_id_phone PRIMARY KEY (id),
    CONSTRAINT combine_model_manufacturer_phone UNIQUE (model, manufacturer),
    CONSTRAINT id FOREIGN KEY (invoice_id)
        REFERENCES public."Invoice" (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE SET NULL
);

-- Table: public.Mouse

-- DROP TABLE IF EXISTS public."Mouse";

CREATE TABLE IF NOT EXISTS public."Mouse"
(
    id character varying NOT NULL,
    model character varying,
    manufacturer character varying,
    count integer,
    price double precision,
    connection_type character varying,
    dpi_amount integer,
    invoice_id character varying,
    CONSTRAINT primary_key_id_mouse PRIMARY KEY (id),
    CONSTRAINT combine_model_manufacturer_mouse UNIQUE (model, manufacturer),
    CONSTRAINT id FOREIGN KEY (invoice_id)
        REFERENCES public."Invoice" (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE SET NULL
);

-- Table: public.Washing_Machine

-- DROP TABLE IF EXISTS public."Washing_Machine";

CREATE TABLE IF NOT EXISTS public."Washing_Machine"
(
    id character varying NOT NULL,
    model character varying,
    manufacturer character varying,
    count integer,
    price double precision,
    turns_number integer,
    invoice_id character varying,
    CONSTRAINT primary_key_id_wm PRIMARY KEY (id),
    CONSTRAINT combine_model_manufacturer_wm UNIQUE (model, manufacturer),
    CONSTRAINT id FOREIGN KEY (invoice_id)
        REFERENCES public."Invoice" (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE SET NULL
);










