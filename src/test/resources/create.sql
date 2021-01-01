create table usr(
                    id numeric(19,0) primary key,
                    email character varying(256),
                    password character varying (128)
);
CREATE SEQUENCE usr_id_sequence START 1;


create table usr_role (
                          user_id numeric(19,0),
                          role character varying (16),
                          constraint usr_role_user_id_fk FOREIGN KEY (user_id) references usr(id)
);
create index usr_role_user_id_idx on usr_role(user_id);




create table booking(
                        id numeric(19,0) primary key,
                        date_from timestamp,
                        date_to timestamp,
                        status character varying(32),
                        guests integer,
                        contact_name character varying (128),
                        email character varying (128),
                        reference character varying (128),
                        date_created timestamp default now(),
                        date_modified timestamp
);
create index booking_date_idx on booking(date_from, date_to);
CREATE SEQUENCE booking_id_sequence START 1;




create table booking_notes (
                               id numeric(19,0) primary key,
                               user_id numeric(19,0),
                               booking_id numeric(19,0),
                               date timestamp,
                               note character varying(2048),
                               constraint booking_notes_user_id_fk FOREIGN KEY (user_id) references usr(id),
                               constraint booking_notes_booking_id_fk FOREIGN KEY (booking_id) references booking(id)
);
create index booking_notes_booking_id_idx on booking(id);
CREATE SEQUENCE booking_notes_id_sequence START 1;