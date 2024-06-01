PGDMP      .                |            return_slip    16.2    16.2     �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            �           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            �           1262    16540    return_slip    DATABASE     �   CREATE DATABASE return_slip WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'English_United States.1252';
    DROP DATABASE return_slip;
                postgres    false            �            1259    16752    detail_return_slip    TABLE     �   CREATE TABLE public.detail_return_slip (
    id_book character varying(255) NOT NULL,
    borrow_date timestamp(6) without time zone,
    quantity integer,
    return_date timestamp(6) without time zone,
    return_slip_id integer NOT NULL
);
 &   DROP TABLE public.detail_return_slip;
       public         heap    postgres    false            �            1259    16542    return_slip    TABLE     h  CREATE TABLE public.return_slip (
    id integer NOT NULL,
    email character varying(50),
    phone_number character varying(15),
    address character varying(255),
    first_name character varying(50),
    last_name character varying(50),
    customer_account character varying(50),
    staff_account character varying(50),
    loan_id integer NOT NULL
);
    DROP TABLE public.return_slip;
       public         heap    postgres    false            �            1259    16541    return_slip_id_seq    SEQUENCE     �   CREATE SEQUENCE public.return_slip_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 )   DROP SEQUENCE public.return_slip_id_seq;
       public          postgres    false    216            �           0    0    return_slip_id_seq    SEQUENCE OWNED BY     I   ALTER SEQUENCE public.return_slip_id_seq OWNED BY public.return_slip.id;
          public          postgres    false    215                       2604    16545    return_slip id    DEFAULT     p   ALTER TABLE ONLY public.return_slip ALTER COLUMN id SET DEFAULT nextval('public.return_slip_id_seq'::regclass);
 =   ALTER TABLE public.return_slip ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    216    215    216            �          0    16752    detail_return_slip 
   TABLE DATA           i   COPY public.detail_return_slip (id_book, borrow_date, quantity, return_date, return_slip_id) FROM stdin;
    public          postgres    false    217   �       �          0    16542    return_slip 
   TABLE DATA           �   COPY public.return_slip (id, email, phone_number, address, first_name, last_name, customer_account, staff_account, loan_id) FROM stdin;
    public          postgres    false    216   D       �           0    0    return_slip_id_seq    SEQUENCE SET     A   SELECT pg_catalog.setval('public.return_slip_id_seq', 18, true);
          public          postgres    false    215            "           2606    16756 *   detail_return_slip detail_return_slip_pkey 
   CONSTRAINT     }   ALTER TABLE ONLY public.detail_return_slip
    ADD CONSTRAINT detail_return_slip_pkey PRIMARY KEY (id_book, return_slip_id);
 T   ALTER TABLE ONLY public.detail_return_slip DROP CONSTRAINT detail_return_slip_pkey;
       public            postgres    false    217    217                        2606    16549    return_slip return_slip_pkey 
   CONSTRAINT     Z   ALTER TABLE ONLY public.return_slip
    ADD CONSTRAINT return_slip_pkey PRIMARY KEY (id);
 F   ALTER TABLE ONLY public.return_slip DROP CONSTRAINT return_slip_pkey;
       public            postgres    false    216            #           2606    16757 .   detail_return_slip fk5hdev507fyetrssxqsowopqax    FK CONSTRAINT     �   ALTER TABLE ONLY public.detail_return_slip
    ADD CONSTRAINT fk5hdev507fyetrssxqsowopqax FOREIGN KEY (return_slip_id) REFERENCES public.return_slip(id);
 X   ALTER TABLE ONLY public.detail_return_slip DROP CONSTRAINT fk5hdev507fyetrssxqsowopqax;
       public          postgres    false    4640    217    216            �   >   x�KL��$N##]S]#Ks+ �4�.jƕ��3G��n�v}��3ƥ/F��� �H0[      �   x   x��̻
�@��z�U2{u��ؘjK�E��g@��W�@��)��o��%��u���-��-��;�1x�fnS��Y����5Gh��/,ܞ�u>�>���ʮ'�W����Q6�     