PGDMP  9    "                |            bookdb    16.2    16.2     �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            �           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            �           1262    16399    bookdb    DATABASE     �   CREATE DATABASE bookdb WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'English_United States.1252';
    DROP DATABASE bookdb;
                postgres    false                        2615    2200    public    SCHEMA        CREATE SCHEMA public;
    DROP SCHEMA public;
                postgres    false            �           0    0    SCHEMA public    COMMENT     6   COMMENT ON SCHEMA public IS 'standard public schema';
                   postgres    false    6            �           0    0    SCHEMA public    ACL     Q   REVOKE USAGE ON SCHEMA public FROM PUBLIC;
GRANT ALL ON SCHEMA public TO PUBLIC;
                   postgres    false    6            �            1259    16432    account    TABLE     �   CREATE TABLE public.account (
    username character varying NOT NULL,
    password character varying NOT NULL,
    email character varying NOT NULL
);
    DROP TABLE public.account;
       public         heap    postgres    false    6            �            1259    16411    book    TABLE     �  CREATE TABLE public.book (
    id character varying NOT NULL,
    title character varying NOT NULL,
    author_name character varying NOT NULL,
    category character varying NOT NULL,
    publisher character varying NOT NULL,
    publish_date character varying NOT NULL,
    quantity integer NOT NULL,
    available_quantity integer NOT NULL,
    "Image" bytea,
    "Description" character varying
);
    DROP TABLE public.book;
       public         heap    postgres    false    6            �            1259    16423    category    TABLE     i   CREATE TABLE public.category (
    id character varying NOT NULL,
    name character varying NOT NULL
);
    DROP TABLE public.category;
       public         heap    postgres    false    6            �            1259    16418    slip    TABLE     �  CREATE TABLE public.slip (
    id character varying NOT NULL,
    username character varying NOT NULL,
    book_id character varying NOT NULL,
    quantity integer NOT NULL,
    borrow_date date NOT NULL,
    due_date date NOT NULL,
    return_date date NOT NULL,
    first_name character varying NOT NULL,
    last_name character varying NOT NULL,
    email character varying NOT NULL,
    phone_number character varying NOT NULL,
    address character varying NOT NULL
);
    DROP TABLE public.slip;
       public         heap    postgres    false    6            �          0    16432    account 
   TABLE DATA           <   COPY public.account (username, password, email) FROM stdin;
    public          postgres    false    219   �       �          0    16411    book 
   TABLE DATA           �   COPY public.book (id, title, author_name, category, publisher, publish_date, quantity, available_quantity, "Image", "Description") FROM stdin;
    public          postgres    false    216   �       �          0    16423    category 
   TABLE DATA           ,   COPY public.category (id, name) FROM stdin;
    public          postgres    false    218   �       �          0    16418    slip 
   TABLE DATA           �   COPY public.slip (id, username, book_id, quantity, borrow_date, due_date, return_date, first_name, last_name, email, phone_number, address) FROM stdin;
    public          postgres    false    217   �       7           2606    16438    account account_pkey 
   CONSTRAINT     X   ALTER TABLE ONLY public.account
    ADD CONSTRAINT account_pkey PRIMARY KEY (username);
 >   ALTER TABLE ONLY public.account DROP CONSTRAINT account_pkey;
       public            postgres    false    219            1           2606    16417    book books_pkey 
   CONSTRAINT     M   ALTER TABLE ONLY public.book
    ADD CONSTRAINT books_pkey PRIMARY KEY (id);
 9   ALTER TABLE ONLY public.book DROP CONSTRAINT books_pkey;
       public            postgres    false    216            5           2606    16431    category category_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.category
    ADD CONSTRAINT category_pkey PRIMARY KEY (id);
 @   ALTER TABLE ONLY public.category DROP CONSTRAINT category_pkey;
       public            postgres    false    218            3           2606    16427    slip slip_pkey 
   CONSTRAINT     L   ALTER TABLE ONLY public.slip
    ADD CONSTRAINT slip_pkey PRIMARY KEY (id);
 8   ALTER TABLE ONLY public.slip DROP CONSTRAINT slip_pkey;
       public            postgres    false    217            �      x������ � �      �   �  x��ӹj1 �����/0a$�Y&M:�"���ĉ���?ڣ؅�3�s��j鵀tj@�D��C�4�;����ۯ��/��!ܾ~~<��?<�������}���,�/���<G�	0̸�5�gܻ�>:g��H��4RGuq9b���,�X\��knX�����ʜ�dR�4jሥ�v,��cKq7�%6J��1���+P�
nՠipͺ�r��D�Ei�h���(լi��N��'�R�2L�����-�ⵅ���安2�ǹ$���)�#�9��z���Ee�d���(hXg�.) �ٽ��@r(��鵒���(�h�u%\s�B�L�(Ǭ�
x���Tr9�e'�.�h;f�j�{]6׼0��{�aPf�0ם��@�ɏ��(�h���KlTd�#G���F��fimUcSɧ-�c>�e�z̸i���kF��F���~��T�<n      �      x������ � �      �      x������ � �     