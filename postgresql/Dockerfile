FROM postgres:16.3-alpine3.19
MAINTAINER ITAcademy

ADD /ddl/mail_service/ddl/00_init_mail_db.sql /docker-entrypoint-initdb.d/
ADD /ddl/user_service/ddl/00_init_user_db.sql /docker-entrypoint-initdb.d/

ADD /ddl/mail_service/ddl/01_ddl_mail.sql /docker-entrypoint-initdb.d/
ADD /ddl/user_service/ddl/01_ddl_user.sql /docker-entrypoint-initdb.d/