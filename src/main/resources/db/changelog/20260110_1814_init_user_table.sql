CREATE TABLE x6_user.user (
                               id SERIAL PRIMARY KEY ,
                               login VARCHAR UNIQUE NOT NULL,
                               first_name VARCHAR NOT NULL ,
                               last_name VARCHAR  ,
                               email VARCHAR UNIQUE NOT NULL
);