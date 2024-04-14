PGDMP  0    '                |            book    16.2    16.2     �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            �           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            �           1262    16478    book    DATABASE        CREATE DATABASE book WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'English_United States.1252';
    DROP DATABASE book;
                postgres    false                        2615    2200    public    SCHEMA        CREATE SCHEMA public;
    DROP SCHEMA public;
                pg_database_owner    false            �           0    0    SCHEMA public    COMMENT     6   COMMENT ON SCHEMA public IS 'standard public schema';
                   pg_database_owner    false    4            �            1259    16494    book    TABLE       CREATE TABLE public.book (
    id integer NOT NULL,
    image bytea,
    title character varying(255),
    author_name character varying(255),
    inventory_quantity integer,
    summary text,
    publish_date date,
    id_publisher integer,
    id_category integer
);
    DROP TABLE public.book;
       public         heap    postgres    false    4            �            1259    16493    book_id_seq    SEQUENCE     �   CREATE SEQUENCE public.book_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 "   DROP SEQUENCE public.book_id_seq;
       public          postgres    false    4    220            �           0    0    book_id_seq    SEQUENCE OWNED BY     ;   ALTER SEQUENCE public.book_id_seq OWNED BY public.book.id;
          public          postgres    false    219            �            1259    16487    category    TABLE     d   CREATE TABLE public.category (
    id integer NOT NULL,
    category_name character varying(255)
);
    DROP TABLE public.category;
       public         heap    postgres    false    4            �            1259    16486    category_id_seq    SEQUENCE     �   CREATE SEQUENCE public.category_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 &   DROP SEQUENCE public.category_id_seq;
       public          postgres    false    218    4            �           0    0    category_id_seq    SEQUENCE OWNED BY     C   ALTER SEQUENCE public.category_id_seq OWNED BY public.category.id;
          public          postgres    false    217            �            1259    16480 	   publisher    TABLE     f   CREATE TABLE public.publisher (
    id integer NOT NULL,
    publisher_name character varying(255)
);
    DROP TABLE public.publisher;
       public         heap    postgres    false    4            �            1259    16479    publisher_id_seq    SEQUENCE     �   CREATE SEQUENCE public.publisher_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 '   DROP SEQUENCE public.publisher_id_seq;
       public          postgres    false    4    216            �           0    0    publisher_id_seq    SEQUENCE OWNED BY     E   ALTER SEQUENCE public.publisher_id_seq OWNED BY public.publisher.id;
          public          postgres    false    215            &           2604    16497    book id    DEFAULT     b   ALTER TABLE ONLY public.book ALTER COLUMN id SET DEFAULT nextval('public.book_id_seq'::regclass);
 6   ALTER TABLE public.book ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    220    219    220            %           2604    16490    category id    DEFAULT     j   ALTER TABLE ONLY public.category ALTER COLUMN id SET DEFAULT nextval('public.category_id_seq'::regclass);
 :   ALTER TABLE public.category ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    218    217    218            $           2604    16483    publisher id    DEFAULT     l   ALTER TABLE ONLY public.publisher ALTER COLUMN id SET DEFAULT nextval('public.publisher_id_seq'::regclass);
 ;   ALTER TABLE public.publisher ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    216    215    216            �          0    16494    book 
   TABLE DATA           �   COPY public.book (id, image, title, author_name, inventory_quantity, summary, publish_date, id_publisher, id_category) FROM stdin;
    public          postgres    false    220   �       �          0    16487    category 
   TABLE DATA           5   COPY public.category (id, category_name) FROM stdin;
    public          postgres    false    218          �          0    16480 	   publisher 
   TABLE DATA           7   COPY public.publisher (id, publisher_name) FROM stdin;
    public          postgres    false    216          �           0    0    book_id_seq    SEQUENCE SET     :   SELECT pg_catalog.setval('public.book_id_seq', 1, false);
          public          postgres    false    219            �           0    0    category_id_seq    SEQUENCE SET     >   SELECT pg_catalog.setval('public.category_id_seq', 1, false);
          public          postgres    false    217            �           0    0    publisher_id_seq    SEQUENCE SET     ?   SELECT pg_catalog.setval('public.publisher_id_seq', 1, false);
          public          postgres    false    215            ,           2606    16501    book book_pkey 
   CONSTRAINT     L   ALTER TABLE ONLY public.book
    ADD CONSTRAINT book_pkey PRIMARY KEY (id);
 8   ALTER TABLE ONLY public.book DROP CONSTRAINT book_pkey;
       public            postgres    false    220            *           2606    16492    category category_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.category
    ADD CONSTRAINT category_pkey PRIMARY KEY (id);
 @   ALTER TABLE ONLY public.category DROP CONSTRAINT category_pkey;
       public            postgres    false    218            (           2606    16485    publisher publisher_pkey 
   CONSTRAINT     V   ALTER TABLE ONLY public.publisher
    ADD CONSTRAINT publisher_pkey PRIMARY KEY (id);
 B   ALTER TABLE ONLY public.publisher DROP CONSTRAINT publisher_pkey;
       public            postgres    false    216            -           2606    16507    book book_id_category_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.book
    ADD CONSTRAINT book_id_category_fkey FOREIGN KEY (id_category) REFERENCES public.category(id);
 D   ALTER TABLE ONLY public.book DROP CONSTRAINT book_id_category_fkey;
       public          postgres    false    4650    218    220            .           2606    16502    book book_id_publisher_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.book
    ADD CONSTRAINT book_id_publisher_fkey FOREIGN KEY (id_publisher) REFERENCES public.publisher(id);
 E   ALTER TABLE ONLY public.book DROP CONSTRAINT book_id_publisher_fkey;
       public          postgres    false    216    220    4648            �      x������ � �      �      x������ � �      �      x������ � �     