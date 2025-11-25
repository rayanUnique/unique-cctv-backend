--
-- PostgreSQL database dump
--

-- Dumped from database version 17.5
-- Dumped by pg_dump version 17.5

-- Started on 2025-11-24 15:10:25

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 226 (class 1259 OID 49874)
-- Name: appointments; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.appointments (
    id bigint NOT NULL,
    address character varying(255) NOT NULL,
    appointment_date timestamp(6) without time zone NOT NULL,
    appointment_time character varying(255),
    booking_date timestamp(6) without time zone NOT NULL,
    description character varying(1000),
    email character varying(255) NOT NULL,
    mobile_number character varying(255) NOT NULL,
    name character varying(255) NOT NULL,
    selected_service character varying(255) NOT NULL,
    status character varying(255) NOT NULL
);


ALTER TABLE public.appointments OWNER TO postgres;

--
-- TOC entry 225 (class 1259 OID 49873)
-- Name: appointments_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.appointments_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.appointments_id_seq OWNER TO postgres;

--
-- TOC entry 4937 (class 0 OID 0)
-- Dependencies: 225
-- Name: appointments_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.appointments_id_seq OWNED BY public.appointments.id;


--
-- TOC entry 218 (class 1259 OID 49781)
-- Name: contacts; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.contacts (
    id bigint NOT NULL,
    created_at timestamp(6) without time zone,
    email character varying(150) NOT NULL,
    is_read boolean,
    message text NOT NULL,
    name character varying(100) NOT NULL,
    phone character varying(20)
);


ALTER TABLE public.contacts OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 49780)
-- Name: contacts_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.contacts_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.contacts_id_seq OWNER TO postgres;

--
-- TOC entry 4938 (class 0 OID 0)
-- Dependencies: 217
-- Name: contacts_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.contacts_id_seq OWNED BY public.contacts.id;


--
-- TOC entry 222 (class 1259 OID 49810)
-- Name: homepage_content; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.homepage_content (
    id bigint NOT NULL,
    created_at timestamp(6) without time zone,
    hero_image character varying(255),
    slide_1_description text,
    slide_1_image character varying(255),
    slide_1_title character varying(255),
    slide_2_description text,
    slide_2_image character varying(255),
    slide_2_title character varying(255),
    slide_3_description text,
    slide_3_image character varying(255),
    slide_3_title character varying(255),
    slide_4_description text,
    slide_4_image character varying(255),
    slide_4_title character varying(255),
    updated_at timestamp(6) without time zone,
    hero_button_text character varying(255),
    hero_subtitle text,
    hero_title character varying(255),
    slide1_button_text character varying(255),
    slide1_description text,
    slide1_image character varying(255),
    slide1_title character varying(255),
    slide2_button_text character varying(255),
    slide2_description text,
    slide2_image character varying(255),
    slide2_title character varying(255),
    slide3_button_text character varying(255),
    slide3_description text,
    slide3_image character varying(255),
    slide3_title character varying(255),
    slide4_button_text character varying(255),
    slide4_description text,
    slide4_image character varying(255),
    slide4_title character varying(255)
);


ALTER TABLE public.homepage_content OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 49809)
-- Name: homepage_content_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.homepage_content_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.homepage_content_id_seq OWNER TO postgres;

--
-- TOC entry 4939 (class 0 OID 0)
-- Dependencies: 221
-- Name: homepage_content_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.homepage_content_id_seq OWNED BY public.homepage_content.id;


--
-- TOC entry 224 (class 1259 OID 49865)
-- Name: products; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.products (
    id bigint NOT NULL,
    category character varying(100) NOT NULL,
    created_at timestamp(6) without time zone,
    description text,
    image character varying(500),
    in_stock boolean,
    name character varying(200) NOT NULL,
    price double precision NOT NULL,
    specifications text,
    stock_quantity integer,
    updated_at timestamp(6) without time zone
);


ALTER TABLE public.products OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 49864)
-- Name: products_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.products_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.products_id_seq OWNER TO postgres;

--
-- TOC entry 4940 (class 0 OID 0)
-- Dependencies: 223
-- Name: products_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.products_id_seq OWNED BY public.products.id;


--
-- TOC entry 228 (class 1259 OID 49883)
-- Name: projects; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.projects (
    id bigint NOT NULL,
    created_at timestamp(6) without time zone,
    description character varying(1000),
    image character varying(255),
    is_active boolean,
    title character varying(255) NOT NULL,
    type character varying(255) NOT NULL,
    updated_at timestamp(6) without time zone
);


ALTER TABLE public.projects OWNER TO postgres;

--
-- TOC entry 227 (class 1259 OID 49882)
-- Name: projects_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.projects_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.projects_id_seq OWNER TO postgres;

--
-- TOC entry 4941 (class 0 OID 0)
-- Dependencies: 227
-- Name: projects_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.projects_id_seq OWNED BY public.projects.id;


--
-- TOC entry 220 (class 1259 OID 49799)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    id bigint NOT NULL,
    created_at timestamp(6) without time zone,
    email character varying(150) NOT NULL,
    is_active boolean,
    name character varying(100) NOT NULL,
    password character varying(255) NOT NULL,
    phone character varying(20),
    role character varying(20) NOT NULL,
    updated_at timestamp(6) without time zone
);


ALTER TABLE public.users OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 49798)
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.users_id_seq OWNER TO postgres;

--
-- TOC entry 4942 (class 0 OID 0)
-- Dependencies: 219
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;


--
-- TOC entry 4771 (class 2604 OID 49877)
-- Name: appointments id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.appointments ALTER COLUMN id SET DEFAULT nextval('public.appointments_id_seq'::regclass);


--
-- TOC entry 4767 (class 2604 OID 49784)
-- Name: contacts id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.contacts ALTER COLUMN id SET DEFAULT nextval('public.contacts_id_seq'::regclass);


--
-- TOC entry 4769 (class 2604 OID 49813)
-- Name: homepage_content id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.homepage_content ALTER COLUMN id SET DEFAULT nextval('public.homepage_content_id_seq'::regclass);


--
-- TOC entry 4770 (class 2604 OID 49868)
-- Name: products id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.products ALTER COLUMN id SET DEFAULT nextval('public.products_id_seq'::regclass);


--
-- TOC entry 4772 (class 2604 OID 49886)
-- Name: projects id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.projects ALTER COLUMN id SET DEFAULT nextval('public.projects_id_seq'::regclass);


--
-- TOC entry 4768 (class 2604 OID 49802)
-- Name: users id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);


--
-- TOC entry 4784 (class 2606 OID 49881)
-- Name: appointments appointments_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.appointments
    ADD CONSTRAINT appointments_pkey PRIMARY KEY (id);


--
-- TOC entry 4774 (class 2606 OID 49788)
-- Name: contacts contacts_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.contacts
    ADD CONSTRAINT contacts_pkey PRIMARY KEY (id);


--
-- TOC entry 4780 (class 2606 OID 49817)
-- Name: homepage_content homepage_content_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.homepage_content
    ADD CONSTRAINT homepage_content_pkey PRIMARY KEY (id);


--
-- TOC entry 4782 (class 2606 OID 49872)
-- Name: products products_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.products
    ADD CONSTRAINT products_pkey PRIMARY KEY (id);


--
-- TOC entry 4786 (class 2606 OID 49890)
-- Name: projects projects_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.projects
    ADD CONSTRAINT projects_pkey PRIMARY KEY (id);


--
-- TOC entry 4776 (class 2606 OID 49808)
-- Name: users uk_6dotkott2kjsp8vw4d0m25fb7; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT uk_6dotkott2kjsp8vw4d0m25fb7 UNIQUE (email);


--
-- TOC entry 4778 (class 2606 OID 49806)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


-- Completed on 2025-11-24 15:10:25

--
-- PostgreSQL database dump complete
--

