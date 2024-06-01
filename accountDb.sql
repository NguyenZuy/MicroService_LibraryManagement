PGDMP      -                |            account    16.2    16.2     �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            �           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            �           1262    16455    account    DATABASE     �   CREATE DATABASE account WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'English_United States.1252';
    DROP DATABASE account;
                postgres    false            �            1259    16470    account    TABLE     �   CREATE TABLE public.account (
    username character varying(50),
    password character varying(100),
    email character varying(200),
    permission character varying(50),
    id_user integer
);
    DROP TABLE public.account;
       public         heap    postgres    false            �            1259    16464    user    TABLE     �   CREATE TABLE public."user" (
    id integer NOT NULL,
    first_name character varying(50),
    last_name character varying(50),
    gender character(1),
    address character varying(255),
    phone_number character varying(15)
);
    DROP TABLE public."user";
       public         heap    postgres    false            �            1259    16463    user_id_seq    SEQUENCE     �   CREATE SEQUENCE public.user_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 "   DROP SEQUENCE public.user_id_seq;
       public          postgres    false    216            �           0    0    user_id_seq    SEQUENCE OWNED BY     =   ALTER SEQUENCE public.user_id_seq OWNED BY public."user".id;
          public          postgres    false    215                       2604    16467    user id    DEFAULT     d   ALTER TABLE ONLY public."user" ALTER COLUMN id SET DEFAULT nextval('public.user_id_seq'::regclass);
 8   ALTER TABLE public."user" ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    216    215    216            �          0    16470    account 
   TABLE DATA           Q   COPY public.account (username, password, email, permission, id_user) FROM stdin;
    public          postgres    false    217          �          0    16464    user 
   TABLE DATA           Z   COPY public."user" (id, first_name, last_name, gender, address, phone_number) FROM stdin;
    public          postgres    false    216   �       �           0    0    user_id_seq    SEQUENCE SET     :   SELECT pg_catalog.setval('public.user_id_seq', 1, false);
          public          postgres    false    215                        2606    16469    user user_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public."user"
    ADD CONSTRAINT user_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public."user" DROP CONSTRAINT user_pkey;
       public            postgres    false    216            !           2606    16473    account account_id_user_fkey    FK CONSTRAINT     |   ALTER TABLE ONLY public.account
    ADD CONSTRAINT account_id_user_fkey FOREIGN KEY (id_user) REFERENCES public."user"(id);
 F   ALTER TABLE ONLY public.account DROP CONSTRAINT account_id_user_fkey;
       public          postgres    false    216    217    4640            �      x������ � �      �      x������ � �     