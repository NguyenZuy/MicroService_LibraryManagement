PGDMP  *    -                |         	   loan_slip    16.2    16.2     �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            �           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            �           1262    16520 	   loan_slip    DATABASE     �   CREATE DATABASE loan_slip WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'English_United States.1252';
    DROP DATABASE loan_slip;
                postgres    false            �            1259    16704    detail_loan_slip    TABLE       CREATE TABLE public.detail_loan_slip (
    id_book character varying(255) NOT NULL,
    borrow_date timestamp(6) without time zone,
    quantity integer,
    return_date timestamp(6) without time zone,
    loan_slip_id integer NOT NULL,
    status character varying(10)
);
 $   DROP TABLE public.detail_loan_slip;
       public         heap    postgres    false            �            1259    16522 	   loan_slip    TABLE     I  CREATE TABLE public.loan_slip (
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
       public         heap    postgres    false            �            1259    16521    loan_slip_id_seq    SEQUENCE     �   CREATE SEQUENCE public.loan_slip_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 '   DROP SEQUENCE public.loan_slip_id_seq;
       public          postgres    false    216            �           0    0    loan_slip_id_seq    SEQUENCE OWNED BY     E   ALTER SEQUENCE public.loan_slip_id_seq OWNED BY public.loan_slip.id;
          public          postgres    false    215                       2604    16525    loan_slip id    DEFAULT     l   ALTER TABLE ONLY public.loan_slip ALTER COLUMN id SET DEFAULT nextval('public.loan_slip_id_seq'::regclass);
 ;   ALTER TABLE public.loan_slip ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    216    215    216            �          0    16704    detail_loan_slip 
   TABLE DATA           m   COPY public.detail_loan_slip (id_book, borrow_date, quantity, return_date, loan_slip_id, status) FROM stdin;
    public          postgres    false    217   �       �          0    16522 	   loan_slip 
   TABLE DATA           }   COPY public.loan_slip (id, email, phone_number, address, first_name, last_name, customer_account, staff_account) FROM stdin;
    public          postgres    false    216          �           0    0    loan_slip_id_seq    SEQUENCE SET     ?   SELECT pg_catalog.setval('public.loan_slip_id_seq', 32, true);
          public          postgres    false    215            "           2606    16708 &   detail_loan_slip detail_loan_slip_pkey 
   CONSTRAINT     w   ALTER TABLE ONLY public.detail_loan_slip
    ADD CONSTRAINT detail_loan_slip_pkey PRIMARY KEY (id_book, loan_slip_id);
 P   ALTER TABLE ONLY public.detail_loan_slip DROP CONSTRAINT detail_loan_slip_pkey;
       public            postgres    false    217    217                        2606    16529    loan_slip loan_slip_pkey 
   CONSTRAINT     V   ALTER TABLE ONLY public.loan_slip
    ADD CONSTRAINT loan_slip_pkey PRIMARY KEY (id);
 B   ALTER TABLE ONLY public.loan_slip DROP CONSTRAINT loan_slip_pkey;
       public            postgres    false    216            #           2606    16709 ,   detail_loan_slip fk7y7e6k3b1vctbbiewjj6eochp    FK CONSTRAINT     �   ALTER TABLE ONLY public.detail_loan_slip
    ADD CONSTRAINT fk7y7e6k3b1vctbbiewjj6eochp FOREIGN KEY (loan_slip_id) REFERENCES public.loan_slip(id);
 V   ALTER TABLE ONLY public.detail_loan_slip DROP CONSTRAINT fk7y7e6k3b1vctbbiewjj6eochp;
       public          postgres    false    4640    217    216            �   b   x�KL��$N##]S]#Ks+ �4���� �p���奦p%��`w�� #$�v�1v`3�` ����/�Db���bD� ;�]      �   �   x�Mͽ
� ����V
A?�[��L�HZThB��h!p���p
GvII��~s�ӭy��Fp�J
c�!>c
`CI�&`��g���Nʐ�zm(���0f󞛳g���R/�ݟo�`pw��Km�P�,!���1�     