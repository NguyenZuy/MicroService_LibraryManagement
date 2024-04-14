PGDMP      (                |         	   loan_slip    16.2    16.2     �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            �           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            �           1262    16520 	   loan_slip    DATABASE     �   CREATE DATABASE loan_slip WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'English_United States.1252';
    DROP DATABASE loan_slip;
                postgres    false                        2615    2200    public    SCHEMA        CREATE SCHEMA public;
    DROP SCHEMA public;
                pg_database_owner    false            �           0    0    SCHEMA public    COMMENT     6   COMMENT ON SCHEMA public IS 'standard public schema';
                   pg_database_owner    false    4            �            1259    16530    detail_loan_slip    TABLE     �   CREATE TABLE public.detail_loan_slip (
    id_loan_slip integer NOT NULL,
    id_book integer NOT NULL,
    quantity integer,
    borrow_date date,
    return_date date
);
 $   DROP TABLE public.detail_loan_slip;
       public         heap    postgres    false    4            �            1259    16522 	   loan_slip    TABLE     I  CREATE TABLE public.loan_slip (
    id integer NOT NULL,
    email character varying(200),
    phone_number character varying(15),
    address character varying(255),
    first_name character varying(50),
    last_name character varying(50),
    customer_account character varying(50),
    staff_account character varying(50)
);
    DROP TABLE public.loan_slip;
       public         heap    postgres    false    4            �            1259    16521    loan_slip_id_seq    SEQUENCE     �   CREATE SEQUENCE public.loan_slip_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 '   DROP SEQUENCE public.loan_slip_id_seq;
       public          postgres    false    216    4            �           0    0    loan_slip_id_seq    SEQUENCE OWNED BY     E   ALTER SEQUENCE public.loan_slip_id_seq OWNED BY public.loan_slip.id;
          public          postgres    false    215                       2604    16525    loan_slip id    DEFAULT     l   ALTER TABLE ONLY public.loan_slip ALTER COLUMN id SET DEFAULT nextval('public.loan_slip_id_seq'::regclass);
 ;   ALTER TABLE public.loan_slip ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    215    216    216            �          0    16530    detail_loan_slip 
   TABLE DATA           e   COPY public.detail_loan_slip (id_loan_slip, id_book, quantity, borrow_date, return_date) FROM stdin;
    public          postgres    false    217   �       �          0    16522 	   loan_slip 
   TABLE DATA           }   COPY public.loan_slip (id, email, phone_number, address, first_name, last_name, customer_account, staff_account) FROM stdin;
    public          postgres    false    216   �       �           0    0    loan_slip_id_seq    SEQUENCE SET     ?   SELECT pg_catalog.setval('public.loan_slip_id_seq', 1, false);
          public          postgres    false    215            "           2606    16534 &   detail_loan_slip detail_loan_slip_pkey 
   CONSTRAINT     w   ALTER TABLE ONLY public.detail_loan_slip
    ADD CONSTRAINT detail_loan_slip_pkey PRIMARY KEY (id_loan_slip, id_book);
 P   ALTER TABLE ONLY public.detail_loan_slip DROP CONSTRAINT detail_loan_slip_pkey;
       public            postgres    false    217    217                        2606    16529    loan_slip loan_slip_pkey 
   CONSTRAINT     V   ALTER TABLE ONLY public.loan_slip
    ADD CONSTRAINT loan_slip_pkey PRIMARY KEY (id);
 B   ALTER TABLE ONLY public.loan_slip DROP CONSTRAINT loan_slip_pkey;
       public            postgres    false    216            #           2606    16535 3   detail_loan_slip detail_loan_slip_id_loan_slip_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.detail_loan_slip
    ADD CONSTRAINT detail_loan_slip_id_loan_slip_fkey FOREIGN KEY (id_loan_slip) REFERENCES public.loan_slip(id);
 ]   ALTER TABLE ONLY public.detail_loan_slip DROP CONSTRAINT detail_loan_slip_id_loan_slip_fkey;
       public          postgres    false    216    217    4640            �      x������ � �      �      x������ � �     