PGDMP  !    9                |            bookdb    16.2    16.2 
    �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
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
                   postgres    false    6            �            1259    16411    book    TABLE     �  CREATE TABLE public.book (
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
       public         heap    postgres    false    6            �          0    16411    book 
   TABLE DATA           �   COPY public.book (id, title, author_name, category, publisher, publish_date, quantity, available_quantity, "Image", "Description") FROM stdin;
    public          postgres    false    216   �	       %           2606    16417    book books_pkey 
   CONSTRAINT     M   ALTER TABLE ONLY public.book
    ADD CONSTRAINT books_pkey PRIMARY KEY (id);
 9   ALTER TABLE ONLY public.book DROP CONSTRAINT books_pkey;
       public            postgres    false    216            �   �  x��ӹj1 �����/0a$�Y&M:�"���ĉ���?ڣ؅�3�s��j鵀tj@�D��C�4�;����ۯ��/��!ܾ~~<��?<�������}���,�/���<G�	0̸�5�gܻ�>:g��H��4RGuq9b���,�X\��knX�����ʜ�dR�4jሥ�v,��cKq7�%6J��1���+P�
nՠipͺ�r��D�Ei�h���(լi��N��'�R�2L�����-�ⵅ���安2�ǹ$���)�#�9��z���Ee�d���(hXg�.) �ٽ��@r(��鵒���(�h�u%\s�B�L�(Ǭ�
x���Tr9�e'�.�h;f�j�{]6׼0��{�aPf�0ם��@�ɏ��(�h���KlTd�#G���F��fimUcSɧ-�c>�e�z̸i���kF��F���~��T�<n     