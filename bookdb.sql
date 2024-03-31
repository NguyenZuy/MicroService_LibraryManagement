PGDMP  /                    |            bookdb    11.22    16.0                0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false                       0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false                       0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false                       1262    40960    bookdb    DATABASE     ~   CREATE DATABASE bookdb WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Vietnamese_Vietnam.1258';
    DROP DATABASE bookdb;
                postgres    false            	           0    0    DATABASE bookdb    COMMENT     I   COMMENT ON DATABASE bookdb IS 'This is the DB of book for Book Service';
                   postgres    false    2824                        2615    2200    public    SCHEMA     2   -- *not* creating schema, since initdb creates it
 2   -- *not* dropping schema, since initdb creates it
                postgres    false            
           0    0    SCHEMA public    ACL     Q   REVOKE USAGE ON SCHEMA public FROM PUBLIC;
GRANT ALL ON SCHEMA public TO PUBLIC;
                   postgres    false    7                        3079    40969 	   uuid-ossp 	   EXTENSION     ?   CREATE EXTENSION IF NOT EXISTS "uuid-ossp" WITH SCHEMA public;
    DROP EXTENSION "uuid-ossp";
                   false    7                       0    0    EXTENSION "uuid-ossp"    COMMENT     W   COMMENT ON EXTENSION "uuid-ossp" IS 'generate universally unique identifiers (UUIDs)';
                        false    2            �            1259    40961    book    TABLE     W  CREATE TABLE public.book (
    id character varying NOT NULL,
    title character varying NOT NULL,
    author_name character varying NOT NULL,
    genre character varying NOT NULL,
    publisher character varying NOT NULL,
    publish_date character varying NOT NULL,
    quantity integer NOT NULL,
    available_quantity integer NOT NULL
);
    DROP TABLE public.book;
       public            postgres    false    7                      0    40961    book 
   TABLE DATA           t   COPY public.book (id, title, author_name, genre, publisher, publish_date, quantity, available_quantity) FROM stdin;
    public          postgres    false    197   t       �
           2606    40968    book books_pkey 
   CONSTRAINT     M   ALTER TABLE ONLY public.book
    ADD CONSTRAINT books_pkey PRIMARY KEY (id);
 9   ALTER TABLE ONLY public.book DROP CONSTRAINT books_pkey;
       public            postgres    false    197               �  x���;��@��:�/��3�ǰL�tA��i�]d���C�*$@m �����07��U�A(�� �00�iFt�r����x}�p����r�_���������~��o�_�ݟ#��.q�eHsp�E�T��	Aڌ�� &{&��1q�č�&��K���z�i�d�	C)K�ک�=�Lژ�c�Ƭ]2z-�r����5j`�e��B�#��G����hChE�բi��:��aiM&eLV���'������3MT,z�$=C�P��y�ƺg�����dcde�����t&) �Ua� %�Z=�^���'���,	�^B+Lc���B��:c���z�%�|���1�>{-���Vf_��C:3T�s��E-��b'��G��D�1K4�\��|�����	�0�Ϯ%>���{�k?�A������,�?�-     