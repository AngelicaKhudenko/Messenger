\c messenger_user_service

CREATE SCHEMA app AUTHORIZATION user_service_app;

CREATE TABLE app.users
(
    id uuid NOT NULL,
    dt_create timestamp(3),
    dt_update timestamp(3),
    mail character varying UNIQUE,
    key_word character varying,
    first_name character varying,
    second_name character varying,
    role character varying,
    status character varying,
    password character varying,
    CONSTRAINT users_id_pk PRIMARY KEY (id),
    CONSTRAINT users_dt_create_not_null CHECK (dt_create IS NOT NULL),
    CONSTRAINT users_mail_not_null CHECK (mail IS NOT NULL),
    CONSTRAINT users_first_name_not_null CHECK (first_name IS NOT NULL),
    CONSTRAINT users_second_name_not_null CHECK (second_name IS NOT NULL),
    CONSTRAINT users_role_not_null CHECK (role IS NOT NULL),
    CONSTRAINT users_status_not_null CHECK (status IS NOT NULL),
    CONSTRAINT users_password_not_null CHECK (password IS NOT NULL)
);

ALTER TABLE IF EXISTS app.users
    OWNER to user_service_app;

CREATE TABLE app.friends (
    user_id uuid NOT NULL,
    friend_id uuid NOT NULL,
    CONSTRAINT user_friend_pk PRIMARY KEY (user_id, friend_id),
    CONSTRAINT user_friends_user_fk FOREIGN KEY (user_id) REFERENCES app.users (id),
    CONSTRAINT user_friends_friend_fk FOREIGN KEY (friend_id) REFERENCES app.users (id)
);

ALTER TABLE IF EXISTS app.friends
    OWNER to user_service_app;

CREATE TABLE app.verification
(
    mail character varying NOT NULL,
    code character varying,
    CONSTRAINT verification_id_pk PRIMARY KEY (mail),
    CONSTRAINT verification_code_not_null CHECK (code IS NOT NULL)
);

ALTER TABLE IF EXISTS app.verification
    OWNER to user_service_app;