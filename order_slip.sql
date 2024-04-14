PGDMP      *                |         
   order_slip    16.2    16.2     �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            �           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            �           1262    16578 
   order_slip    DATABASE     �   CREATE DATABASE order_slip WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'English_United States.1252';
    DROP DATABASE order_slip;
                postgres    false                        2615    2200    public    SCHEMA        CREATE SCHEMA public;
    DROP SCHEMA public;
                pg_database_owner    false            �           0    0    SCHEMA public    COMMENT     6   COMMENT ON SCHEMA public IS 'standard public schema';
                   pg_database_owner    false    4            �            1259    16586    detail_order_slip    TABLE     �   CREATE TABLE public.detail_order_slip (
    id_order_slip integer NOT NULL,
    id_book integer NOT NULL,
    quantity integer
);
 %   DROP TABLE public.detail_order_slip;
       public         heap    postgres    false    4            �            1259    16580 
   order_slip    TABLE     �   CREATE TABLE public.order_slip (
    id integer NOT NULL,
    order_date date,
    staff_account character varying(50),
    id_supplier integer
);
    DROP TABLE public.order_slip;
       public         heap    postgres    false    4            �            1259    16579    order_slip_id_seq    SEQUENCE     �   CREATE SEQUENCE public.order_slip_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 (   DROP SEQUENCE public.order_slip_id_seq;
       public          postgres    false    216    4            �           0    0    order_slip_id_seq    SEQUENCE OWNED BY     G   ALTER SEQUENCE public.order_slip_id_seq OWNED BY public.order_slip.id;
          public          postgres    false    215                       2604    16583    order_slip id    DEFAULT     n   ALTER TABLE ONLY public.order_slip ALTER COLUMN id SET DEFAULT nextval('public.order_slip_id_seq'::regclass);
 <   ALTER TABLE public.order_slip ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    216    215    216            �          0    16586    detail_order_slip 
   TABLE DATA           M   COPY public.detail_order_slip (id_order_slip, id_book, quantity) FROM stdin;
    public          postgres    false    217   �       �          0    16580 
   order_slip 
   TABLE DATA           P   COPY public.order_slip (id, order_date, staff_account, id_supplier) FROM stdin;
    public          postgres    false    216   �       �           0    0    order_slip_id_seq    SEQUENCE SET     @   SELECT pg_catalog.setval('public.order_slip_id_seq', 1, false);
          public          postgres    false    215            "           2606    16590 (   detail_order_slip detail_order_slip_pkey 
   CONSTRAINT     z   ALTER TABLE ONLY public.detail_order_slip
    ADD CONSTRAINT detail_order_slip_pkey PRIMARY KEY (id_order_slip, id_book);
 R   ALTER TABLE ONLY public.detail_order_slip DROP CONSTRAINT detail_order_slip_pkey;
       public            postgres    false    217    217                        2606    16585    order_slip order_slip_pkey 
   CONSTRAINT     X   ALTER TABLE ONLY public.order_slip
    ADD CONSTRAINT order_slip_pkey PRIMARY KEY (id);
 D   ALTER TABLE ONLY public.order_slip DROP CONSTRAINT order_slip_pkey;
       public            postgres    false    216            #           2606    16591 6   detail_order_slip detail_order_slip_id_order_slip_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.detail_order_slip
    ADD CONSTRAINT detail_order_slip_id_order_slip_fkey FOREIGN KEY (id_order_slip) REFERENCES public.order_slip(id);
 `   ALTER TABLE ONLY public.detail_order_slip DROP CONSTRAINT detail_order_slip_id_order_slip_fkey;
       public          postgres    false    4640    217    216            �      x������ � �      �      x������ � �     