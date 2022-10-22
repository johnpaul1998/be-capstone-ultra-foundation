DROP SCHEMA IF EXISTS pearl cascade;
CREATE SCHEMA pearl;

CREATE TABLE pearl.USERS (
                                user_id uuid,
                                first_name varchar(150),
                                last_name varchar(150),
                                password varchar(150),
                                email varchar(150),
                                image_link varchar(150),
                                created_date TIMESTAMP WITH TIME ZONE,
                                modified_date TIMESTAMP WITH TIME ZONE,
                                PRIMARY KEY (user_id)
);
CREATE TABLE pearl.FUNDRAISERS (
                                      fundraiser_id uuid,
                                      fundraiser_name varchar(150),
                                      description varchar,
                                      target_amount float,
                                      amount_generated float,
                                      image_link varchar(150),
                                      created_date TIMESTAMP WITH TIME ZONE,
                                      modified_date TIMESTAMP WITH TIME ZONE,
                                      PRIMARY KEY (fundraiser_id)
);
CREATE TABLE pearl.PROGRAMS (
                                   program_id uuid,
                                   program_name varchar(150),
                                   description varchar,
                                   image_link varchar(150),
                                   program_time int,
                                   program_date int,
                                   points_to_earn float,
                                   duration float,
                                   location varchar(150),
                                   created_date TIMESTAMP WITH TIME ZONE,
                                   modified_date TIMESTAMP WITH TIME ZONE,
                                   PRIMARY KEY (program_id)
);
CREATE TABLE pearl.DONATIONS (
                                    donation_id uuid,
                                    first_name varchar(150),
                                    last_name varchar(150),
                                    email varchar(150),
                                    amount float,
                                    created_date TIMESTAMP WITH TIME ZONE,
                                    modified_date TIMESTAMP WITH TIME ZONE,
                                    PRIMARY KEY (donation_id)
);