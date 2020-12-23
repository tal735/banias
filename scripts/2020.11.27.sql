create table usr(
	id numeric(19,0) primary key,
    first_name character varying(64),
    last_name character varying(64),
	email character varying(256),
    password character varying (128),
	phone character varying(64),
    country_code character varying(16),
	verified boolean default false,
	registration_date timestamp default now()
)

CREATE SEQUENCE usr_id_sequence START 1;





create table booking(
                        id numeric(19,0) primary key,
                        date_from timestamp,
                        date_to timestamp,
                        user_id numeric(19,0),
                        date_created timestamp default now(),
                        date_modified timestamp,
                        status character varying(32),
                        guests integer,
                        constraint user_id_fk FOREIGN KEY (user_id) references usr(id)
);

create index booking_date_idx on booking(date_from, date_to);
create index booking_user_id_idx on booking(user_id);
CREATE SEQUENCE booking_id_sequence START 1;



create table booking_notes (
	id numeric(19,0) primary key,
	user_id numeric(19,0),
	booking_id numeric(19,0),
	date timestamp,
	note character varying(2048),
	constraint booking_member_user_id_fk FOREIGN KEY (user_id) references usr(id),
	constraint booking_member_booking_id_fk FOREIGN KEY (booking_id) references booking(id)
)
CREATE SEQUENCE booking_notes_id_sequence START 1;



create table board_message(
	id numeric(19,0) primary key,
	type character varying(32),
	message text
)
CREATE SEQUENCE board_message_id_sequence START 1;



create table password_reset(
                               id numeric(19,0) primary key,
                               user_id numeric(19,0) not null,
                               token character varying (50) not null,
                               used boolean default false,
                               date_created timestamp default now(),
                               constraint user_id_fk FOREIGN KEY (user_id) references usr(id)
);
create index password_reset_user_id_idx on booking(user_id);
CREATE SEQUENCE password_reset_id_sequence START 1;