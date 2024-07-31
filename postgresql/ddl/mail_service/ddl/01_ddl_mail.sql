\c messenger_mail_service

CREATE SCHEMA app AUTHORIZATION mail_service_app;

CREATE TABLE app.mails
(
    id uuid NOT NULL,
    dt_create timestamp(3),
    dt_update timestamp(3),
    status character varying,
    sender character varying,
    topic character varying,
    addressee character varying,
    text character varying,
    CONSTRAINT mails_id_pk PRIMARY KEY (id),
    CONSTRAINT mails_dt_create_not_null CHECK (dt_create IS NOT NULL),
    CONSTRAINT mails_status_not_null CHECK (status IS NOT NULL),
    CONSTRAINT mails_sender_not_null CHECK (sender IS NOT NULL),
    CONSTRAINT mails_topic_not_null CHECK (topic IS NOT NULL),
    CONSTRAINT mails_addressee_not_null CHECK (addressee IS NOT NULL),
    CONSTRAINT mails_text_not_null CHECK (text IS NOT NULL)
);

ALTER TABLE IF EXISTS app.mails
    OWNER to mail_service_app;