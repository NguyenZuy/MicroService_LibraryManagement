PGDMP      )                |            supplier    16.2    16.2     �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            �           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            �           1262    16512    supplier    DATABASE     �   CREATE DATABASE supplier WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'English_United States.1252';
    DROP DATABASE supplier;
                postgres    false                        2615    2200    public    SCHEMA        CREATE SCHEMA public;
    DROP SCHEMA public;
                pg_database_owner    false            �           0    0    SCHEMA public    COMMENT     6   COMMENT ON SCHEMA public IS 'standard public schema';
                   pg_database_owner    false    4            �            1259    16514    supplier    TABLE     d   CREATE TABLE public.supplier (
    id integer NOT NULL,
    supplier_name character varying(255)
);
    DROP TABLE public.supplier;
       public         heap    postgres    false    4            �            1259    16513    supplier_id_seq    SEQUENCE     �   CREATE SEQUENCE public.supplier_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 &   DROP SEQUENCE public.supplier_id_seq;
       public          postgres    false    216    4            �           0    0    supplier_id_seq    SEQUENCE OWNED BY     C   ALTER SEQUENCE public.supplier_id_seq OWNED BY public.supplier.id;
          public          postgres    false    215                       2604    16517    supplier id    DEFAULT     j   ALTER TABLE ONLY public.supplier ALTER COLUMN id SET DEFAULT nextval('public.supplier_id_seq'::regclass);
 :   ALTER TABLE public.supplier ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    215    216    216            �          0    16514    supplier 
   TABLE DATA           5   COPY public.supplier (id, supplier_name) FROM stdin;
    public          postgres    false    216   �       �           0    0    supplier_id_seq    SEQUENCE SET     >   SELECT pg_catalog.setval('public.supplier_id_seq', 1, false);
          public          postgres    false    215                       2606    16519    supplier supplier_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.supplier
    ADD CONSTRAINT supplier_pkey PRIMARY KEY (id);
 @   ALTER TABLE ONLY public.supplier DROP CONSTRAINT supplier_pkey;
       public            postgres    false    216            �      x������ � �     