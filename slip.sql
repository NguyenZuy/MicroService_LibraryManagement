PGDMP      :                |            slipdb    16.2    16.2 	    �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            �           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            �           1262    16439    slipdb    DATABASE     �   CREATE DATABASE slipdb WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'English_United States.1252';
    DROP DATABASE slipdb;
                postgres    false                        2615    2200    public    SCHEMA        CREATE SCHEMA public;
    DROP SCHEMA public;
                pg_database_owner    false            �           0    0    SCHEMA public    COMMENT     6   COMMENT ON SCHEMA public IS 'standard public schema';
                   pg_database_owner    false    4            �            1259    16440    slip    TABLE     �  CREATE TABLE public.slip (
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
       public         heap    postgres    false    4            �          0    16440    slip 
   TABLE DATA           �   COPY public.slip (id, username, book_id, quantity, borrow_date, due_date, return_date, first_name, last_name, email, phone_number, address) FROM stdin;
    public          postgres    false    215   \	                  2606    16446    slip slip_pkey 
   CONSTRAINT     L   ALTER TABLE ONLY public.slip
    ADD CONSTRAINT slip_pkey PRIMARY KEY (id);
 8   ALTER TABLE ONLY public.slip DROP CONSTRAINT slip_pkey;
       public            postgres    false    215            �      x������ � �     