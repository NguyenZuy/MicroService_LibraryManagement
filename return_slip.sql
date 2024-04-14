PGDMP  	    )                |            return_slip    16.2    16.2     �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            �           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            �           1262    16540    return_slip    DATABASE     �   CREATE DATABASE return_slip WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'English_United States.1252';
    DROP DATABASE return_slip;
                postgres    false                        2615    2200    public    SCHEMA        CREATE SCHEMA public;
    DROP SCHEMA public;
                pg_database_owner    false            �           0    0    SCHEMA public    COMMENT     6   COMMENT ON SCHEMA public IS 'standard public schema';
                   pg_database_owner    false    4            �            1259    16550    detail_return_slip    TABLE     �   CREATE TABLE public.detail_return_slip (
    id_return_slip integer NOT NULL,
    id_book integer NOT NULL,
    quantity integer,
    borrow_date date,
    return_date date
);
 &   DROP TABLE public.detail_return_slip;
       public         heap    postgres    false    4            �            1259    16542    return_slip    TABLE     J  CREATE TABLE public.return_slip (
    id integer NOT NULL,
    email character varying(50),
    phone_number character varying(15),
    address character varying(255),
    first_name character varying(50),
    last_name character varying(50),
    customer_account character varying(50),
    staff_account character varying(50)
);
    DROP TABLE public.return_slip;
       public         heap    postgres    false    4            �            1259    16541    return_slip_id_seq    SEQUENCE     �   CREATE SEQUENCE public.return_slip_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 )   DROP SEQUENCE public.return_slip_id_seq;
       public          postgres    false    216    4            �           0    0    return_slip_id_seq    SEQUENCE OWNED BY     I   ALTER SEQUENCE public.return_slip_id_seq OWNED BY public.return_slip.id;
          public          postgres    false    215                       2604    16545    return_slip id    DEFAULT     p   ALTER TABLE ONLY public.return_slip ALTER COLUMN id SET DEFAULT nextval('public.return_slip_id_seq'::regclass);
 =   ALTER TABLE public.return_slip ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    215    216    216            �          0    16550    detail_return_slip 
   TABLE DATA           i   COPY public.detail_return_slip (id_return_slip, id_book, quantity, borrow_date, return_date) FROM stdin;
    public          postgres    false    217   -       �          0    16542    return_slip 
   TABLE DATA              COPY public.return_slip (id, email, phone_number, address, first_name, last_name, customer_account, staff_account) FROM stdin;
    public          postgres    false    216   J       �           0    0    return_slip_id_seq    SEQUENCE SET     A   SELECT pg_catalog.setval('public.return_slip_id_seq', 1, false);
          public          postgres    false    215            "           2606    16554 *   detail_return_slip detail_return_slip_pkey 
   CONSTRAINT     }   ALTER TABLE ONLY public.detail_return_slip
    ADD CONSTRAINT detail_return_slip_pkey PRIMARY KEY (id_return_slip, id_book);
 T   ALTER TABLE ONLY public.detail_return_slip DROP CONSTRAINT detail_return_slip_pkey;
       public            postgres    false    217    217                        2606    16549    return_slip return_slip_pkey 
   CONSTRAINT     Z   ALTER TABLE ONLY public.return_slip
    ADD CONSTRAINT return_slip_pkey PRIMARY KEY (id);
 F   ALTER TABLE ONLY public.return_slip DROP CONSTRAINT return_slip_pkey;
       public            postgres    false    216            #           2606    16555 9   detail_return_slip detail_return_slip_id_return_slip_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.detail_return_slip
    ADD CONSTRAINT detail_return_slip_id_return_slip_fkey FOREIGN KEY (id_return_slip) REFERENCES public.return_slip(id);
 c   ALTER TABLE ONLY public.detail_return_slip DROP CONSTRAINT detail_return_slip_id_return_slip_fkey;
       public          postgres    false    216    217    4640            �      x������ � �      �      x������ � �     