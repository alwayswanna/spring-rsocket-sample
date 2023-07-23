CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE user_message(
    id              uuid                                PRIMARY KEY     DEFAULT uuid_generate_v4(),
    first_name       text                                NOT NULL,
    last_name       text                                NOT NULL,
    age             int2                                NOT NULL,
    birth_date      date                                NOT NULL,
    last_update     timestamp without time zone         NOT NULL
);