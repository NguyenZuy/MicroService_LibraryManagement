PGDMP      -                |            import_slip    16.2    16.2     �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            �           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            �           1262    16560    import_slip    DATABASE     �   CREATE DATABASE import_slip WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'English_United States.1252';
    DROP DATABASE import_slip;
                postgres    false            �            1259    16568    detail_import_slip    TABLE     �   CREATE TABLE public.detail_import_slip (
    id_import_slip integer NOT NULL,
    id_book character varying(255) NOT NULL,
    quantity integer
);
 &   DROP TABLE public.detail_import_slip;
       public         heap    postgres    false            �            1259    16562    import_slip    TABLE     �   CREATE TABLE public.import_slip (
    id integer NOT NULL,
    import_date timestamp(6) without time zone,
    staff_account character varying(50),
    id_supplier integer,
    id_order_slip integer
);
    DROP TABLE public.import_slip;
       public         heap    postgres    false            �            1259    16561    import_slip_id_seq    SEQUENCE     �   CREATE SEQUENCE public.import_slip_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 )   DROP SEQUENCE public.import_slip_id_seq;
       public          postgres    false    216            �           0    0    import_slip_id_seq    SEQUENCE OWNED BY     I   ALTER SEQUENCE public.import_slip_id_seq OWNED BY public.import_slip.id;
          public          postgres    false    215                       2604    16565    import_slip id    DEFAULT     p   ALTER TABLE ONLY public.import_slip ALTER COLUMN id SET DEFAULT nextval('public.import_slip_id_seq'::regclass);
 =   ALTER TABLE public.import_slip ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    215    216    216            �          0    16568    detail_import_slip 
   TABLE DATA           O   COPY public.detail_import_slip (id_import_slip, id_book, quantity) FROM stdin;
    public          postgres    false    217   �       �          0    16562    import_slip 
   TABLE DATA           a   COPY public.import_slip (id, import_date, staff_account, id_supplier, id_order_slip) FROM stdin;
    public          postgres    false    216          �           0    0    import_slip_id_seq    SEQUENCE SET     A   SELECT pg_catalog.setval('public.import_slip_id_seq', 17, true);
          public          postgres    false    215            "           2606    16626 *   detail_import_slip detail_import_slip_pkey 
   CONSTRAINT     }   ALTER TABLE ONLY public.detail_import_slip
    ADD CONSTRAINT detail_import_slip_pkey PRIMARY KEY (id_import_slip, id_book);
 T   ALTER TABLE ONLY public.detail_import_slip DROP CONSTRAINT detail_import_slip_pkey;
       public            postgres    false    217    217                        2606    16567    import_slip import_slip_pkey 
   CONSTRAINT     Z   ALTER TABLE ONLY public.import_slip
    ADD CONSTRAINT import_slip_pkey PRIMARY KEY (id);
 F   ALTER TABLE ONLY public.import_slip DROP CONSTRAINT import_slip_pkey;
       public            postgres    false    216            #           2606    16573 9   detail_import_slip detail_import_slip_id_import_slip_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.detail_import_slip
    ADD CONSTRAINT detail_import_slip_id_import_slip_fkey FOREIGN KEY (id_import_slip) REFERENCES public.import_slip(id);
 c   ALTER TABLE ONLY public.detail_import_slip DROP CONSTRAINT detail_import_slip_id_import_slip_fkey;
       public          postgres    false    4640    216    217            �      x�34�L�NC.C$�$�@� �
      �   (   x�34�4202�50�56T00�20 "NNCNC#�=... g�y     