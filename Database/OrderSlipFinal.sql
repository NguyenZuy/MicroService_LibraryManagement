PGDMP      	    	            |         
   order_slip    16.2    16.2     �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            �           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            �           1262    16578 
   order_slip    DATABASE     �   CREATE DATABASE order_slip WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'English_United States.1252';
    DROP DATABASE order_slip;
                postgres    false            �            1259    16586    detail_order_slip    TABLE     �   CREATE TABLE public.detail_order_slip (
    id_order_slip integer NOT NULL,
    id_book character varying(255) NOT NULL,
    quantity integer,
    status character varying(100)
);
 %   DROP TABLE public.detail_order_slip;
       public         heap    postgres    false            �            1259    16580 
   order_slip    TABLE     �   CREATE TABLE public.order_slip (
    id integer NOT NULL,
    order_date timestamp(6) without time zone,
    id_supplier integer
);
    DROP TABLE public.order_slip;
       public         heap    postgres    false            �            1259    16579    order_slip_id_seq    SEQUENCE     �   CREATE SEQUENCE public.order_slip_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 (   DROP SEQUENCE public.order_slip_id_seq;
       public          postgres    false    216            �           0    0    order_slip_id_seq    SEQUENCE OWNED BY     G   ALTER SEQUENCE public.order_slip_id_seq OWNED BY public.order_slip.id;
          public          postgres    false    215                       2604    16583    order_slip id    DEFAULT     n   ALTER TABLE ONLY public.order_slip ALTER COLUMN id SET DEFAULT nextval('public.order_slip_id_seq'::regclass);
 <   ALTER TABLE public.order_slip ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    215    216    216            �          0    16586    detail_order_slip 
   TABLE DATA           U   COPY public.detail_order_slip (id_order_slip, id_book, quantity, status) FROM stdin;
    public          postgres    false    217   k       �          0    16580 
   order_slip 
   TABLE DATA           A   COPY public.order_slip (id, order_date, id_supplier) FROM stdin;
    public          postgres    false    216   �       �           0    0    order_slip_id_seq    SEQUENCE SET     @   SELECT pg_catalog.setval('public.order_slip_id_seq', 19, true);
          public          postgres    false    215            "           2606    16638 (   detail_order_slip detail_order_slip_pkey 
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
       public          postgres    false    4640    216    217            �   J   x�34�L�NCN�܂����.C$�$Ts�t8@���LL���bNSTq$(2�@�j�%g%����qqq �7�      �   =   x�34�4202�50�56T00�20 "NC.Cs�����B��!a�"a�U�И+F��� �P     