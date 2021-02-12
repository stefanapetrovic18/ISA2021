
insert into role (id, name) values (1, 'ROLE_PATIENT');
insert into role (id, name) values (2, 'ROLE_PHARMACIST');
insert into role (id, name) values (3, 'ROLE_DERMATOLOGIST');
insert into role (id, name) values (4, 'ROLE_SYSTEM_ADMIN');
insert into role (id, name) values (5, 'ROLE_SUPPLIER');
insert into role (id, name) values (6, 'ROLE_PHARMACY_ADMIN');

insert into pricelist(id, consultation_price, examination_price, valid_from, valid_until) values (1, '13', '11', '2021-02-20T22:11', '2021-06-20T22:11');
insert into pricelist(id, consultation_price, examination_price, valid_from, valid_until) values (2, '13', '11', '2021-02-10T22:11', '2021-06-20T22:11');
insert into pricelist(id, consultation_price, examination_price, valid_from, valid_until) values (3, '13', '11', '2021-02-10T22:11', '2021-06-20T22:11');
insert into pricelist(id, consultation_price, examination_price, valid_from, valid_until) values (4, '13', '11', '2021-02-10T22:11', '2021-06-20T22:11');

insert into pharmacy(id, address, description, name, rating, pricelist_id) values (1, 'Lovcenska 1', 'Hej loo', 'Zegin', '4.3', 1);
insert into pharmacy(id, address, description, name, rating, pricelist_id) values (2, 'Lovcenska 2', 'Sve na jednom mestu', 'Benu', '4.2', 2);
insert into pharmacy(id, address, description, name, rating, pricelist_id) values (3, 'Lovcenska 3', 'Nasi proizvodi za vase dobro', 'Jankovic', '4.8', 3);
insert into pharmacy(id, address, description, name, rating, pricelist_id) values (4, 'Lovcenska 4', 'Prirodno i dobro', 'Zelena apoteka', '4.1', 4);

insert into user(id, address, city, country, enabled, forename, password, password_changed, phone, surname,  username, validated) values (1, 'Slovacka 13', 'Novi Sad', 'Srbija', b'1', 'Milic', '$2a$10$PCJEmmf5YwLYkfy2yAUZI.j4wgsurdFRXu.RU4RGRcqsCNN0T59my', b'1', '123456', 'Milic', 'patient@gmail.com', b'1');
insert into user(id, address, city, country, enabled, forename, password, password_changed, phone, surname,  username, validated) values (2, 'Slovacka 13', 'Novi Sad', 'Srbija', b'1', 'Milic', '$2a$10$PCJEmmf5YwLYkfy2yAUZI.j4wgsurdFRXu.RU4RGRcqsCNN0T59my', b'1', '123456', 'Milic', 'pharmacist@gmail.com', b'1');
insert into user(id, address, city, country, enabled, forename, password, password_changed, phone, surname,  username, validated) values (3, 'Slovacka 13', 'Novi Sad', 'Srbija', b'1', 'Milic', '$2a$10$PCJEmmf5YwLYkfy2yAUZI.j4wgsurdFRXu.RU4RGRcqsCNN0T59my', b'1', '123456', 'Milic', 'dermatologist@gmail.com', b'1');
insert into user(id, address, city, country, enabled, forename, password, password_changed, phone, surname,  username, validated) values (4, 'Slovacka 13', 'Novi Sad', 'Srbija', b'1', 'Milic', '$2a$10$PCJEmmf5YwLYkfy2yAUZI.j4wgsurdFRXu.RU4RGRcqsCNN0T59my', b'1', '123456', 'Milic', 'admin@gmail.com', b'1');
insert into user(id, address, city, country, enabled, forename, password, password_changed, phone, surname,  username, validated) values (5, 'Slovacka 13', 'Novi Sad', 'Srbija', b'1', 'Milic', '$2a$10$PCJEmmf5YwLYkfy2yAUZI.j4wgsurdFRXu.RU4RGRcqsCNN0T59my', b'1', '123456', 'Milic', 'supplier@gmail.com', b'1');
insert into user(id, address, city, country, enabled, forename, password, password_changed, phone, surname,  username, validated) values (6, 'Slovacka 13', 'Novi Sad', 'Srbija', b'1', 'Milic', '$2a$10$PCJEmmf5YwLYkfy2yAUZI.j4wgsurdFRXu.RU4RGRcqsCNN0T59my', b'1', '123456', 'Milic', 'pharmacy_admin@gmail.com', b'1');

insert into pharmacist(rating, vacation_end, vacation_start, id, pharmacy) values (5, '2021-05-20T22:11', '2021-02-20T22:11', 2, 1);
insert into dermatologist(rating, vacation_end,vacation_start, id) values (3, '2021-05-20T22:11', '2021-05-20T22:11', 3);
insert into patient(points, id) values (12, 1);
insert into pharmacy_admin(id, pharmacy) values (6, 1);
insert into system_admin(id) values (4);

insert into pharmacy_dermatologists(pharmacy_id, dermatologists_id) values (1, 3);

insert into user_roles(user_id, role_id) values (1,1);
insert into user_roles(user_id, role_id) values (2,2);
insert into user_roles(user_id, role_id) values (3,3);
insert into user_roles(user_id, role_id) values (4,4);
insert into user_roles(user_id, role_id) values (5,5);
insert into user_roles(user_id, role_id) values (6,6);

insert into medicine(id, code, form, manufacturer, name, prescription_necessary, recommended_dose, side_effects, type) values (1, '1234', 'form', 'Galenika', 'Bromazepam', b'1', 'One per day', 'none', 'type');
insert into medicine(id, code, form, manufacturer, name, prescription_necessary, recommended_dose, side_effects, type) values (2, '1234', 'form', 'Galenika', 'Aspirin', b'1', 'One per day', 'none', 'type');
insert into medicine(id, code, form, manufacturer, name, prescription_necessary, recommended_dose, side_effects, type) values (3, '1234', 'form', 'Alkaloid', 'Magnezijum direkt', b'1', 'One per day', 'none', 'type');
insert into medicine(id, code, form, manufacturer, name, prescription_necessary, recommended_dose, side_effects, type) values (4, '1234', 'form', 'Alkaloid', 'Kolagen beauty', b'1', 'One per day', 'none', 'type');

insert into medicine_ingredients(medicine_id, ingredients) values (1, 'H2');
insert into medicine_ingredients(medicine_id, ingredients) values (1, 'rakija');
insert into medicine_ingredients(medicine_id, ingredients) values (1, 'O');
insert into medicine_ingredients(medicine_id, ingredients) values (2, 'C');
insert into medicine_ingredients(medicine_id, ingredients) values (3, 'Mg');
insert into medicine_ingredients(medicine_id, ingredients) values (4, 'Biotin');
insert into medicine_ingredients(medicine_id, ingredients) values (3, 'C');
insert into medicine_ingredients(medicine_id, ingredients) values (4, 'C');

insert into item(id, price, quantity, medicine, pricelist) values (1, 10.0, 1, 1, 1);
insert into item(id, price, quantity, medicine, pricelist) values (2, 11.0, 1, 2, 1);
insert into item(id, price, quantity, medicine, pricelist) values (3, 12.0, 1, 3, 1);
insert into item(id, price, quantity, medicine, pricelist) values (4, 15.0, 1, 4, 1);

insert into consultation(id, consultation_date, duration, price, patient, pharmacist, pharmacy) values (1, '2021-02-20T22:11', '2', '13', 1, 2, 1);
insert into consultation(id, consultation_date, duration, price, patient, pharmacist, pharmacy) values (2, '2021-02-21T22:11', '2', '31', 1, 2, 1);
insert into consultation(id, consultation_date, duration, price, patient, pharmacist, pharmacy) values (3, '2021-02-22T22:11', '2', '23', 1, 2, 1);

insert into examination( id, duration, examination_date, price, quick_reservation, dermatologist, patient, pharmacy) VALUES (1, '2', '2021-02-20T22:11', '31', b'1', 3, 1, 1);

insert into reservation(id, collected, collection_date, reservation_date, reservation_number, medicine, patient, pharmacy) VALUES (1, b'1', '2021-02-20T22:11', '2021-02-20T22:11', '1', 1, 1, 1);
insert into reservation(id, collected, collection_date, reservation_date, reservation_number, medicine, patient, pharmacy) VALUES (2, b'1', '2021-02-20T22:11', '2021-02-20T22:11', '2', 2, 1, 1);
insert into reservation(id, collected, collection_date, reservation_date, reservation_number, medicine, patient, pharmacy) VALUES (3, b'1', '2021-02-20T22:11', '2021-02-20T22:11', '3', 3, 1, 1);

insert into vacation_request(id, accepted, employeeid, rejected, vacation_end, vacation_start, pharmacy_id) VALUES (1, b'1', 2, b'0', '2021-02-20T22:11', '2021-02-26T22:11', 1);
insert into vacation_request(id, accepted, employeeid, rejected, vacation_end, vacation_start, pharmacy_id) VALUES (2, b'0', 3, b'1', '2021-02-20T22:11', '2021-02-26T22:11', 1);

INSERT INTO working_hours(id, day_of_week, employeeid, shift_end, shift_start, pharmacy) VALUES (1, 0, 3, '11:00', '08:00', 1);
INSERT INTO working_hours(id, day_of_week, employeeid, shift_end, shift_start, pharmacy) VALUES (2, 1, 3, '11:00', '08:00', 1);
INSERT INTO working_hours(id, day_of_week, employeeid, shift_end, shift_start, pharmacy) VALUES (3, 2, 3, '11:00', '08:00', 1);
INSERT INTO working_hours(id, day_of_week, employeeid, shift_end, shift_start, pharmacy) VALUES (4, 3, 3, '11:00', '08:00', 1);
INSERT INTO working_hours(id, day_of_week, employeeid, shift_end, shift_start, pharmacy) VALUES (5, 4, 3, '11:00', '08:00', 2);
INSERT INTO working_hours(id, day_of_week, employeeid, shift_end, shift_start, pharmacy) VALUES (6, 5, 3, '11:00', '08:00', 2);
INSERT INTO working_hours(id, day_of_week, employeeid, shift_end, shift_start, pharmacy) VALUES (7, 6, 3, '11:00', '08:00', 2);

INSERT INTO working_hours(id, day_of_week, employeeid, shift_end, shift_start, pharmacy) VALUES (8, 0, 2, '11:00', '08:00', 1);
INSERT INTO working_hours(id, day_of_week, employeeid, shift_end, shift_start, pharmacy) VALUES (9, 1, 2, '11:00', '08:00', 1);
INSERT INTO working_hours(id, day_of_week, employeeid, shift_end, shift_start, pharmacy) VALUES (10, 2, 2, '11:00', '08:00', 1);
INSERT INTO working_hours(id, day_of_week, employeeid, shift_end, shift_start, pharmacy) VALUES (11, 3, 2, '11:00', '08:00', 1);
INSERT INTO working_hours(id, day_of_week, employeeid, shift_end, shift_start, pharmacy) VALUES (12, 4, 2, '11:00', '08:00', 1);
INSERT INTO working_hours(id, day_of_week, employeeid, shift_end, shift_start, pharmacy) VALUES (13, 5, 2, '11:00', '08:00', 1);
INSERT INTO working_hours(id, day_of_week, employeeid, shift_end, shift_start, pharmacy) VALUES (14, 6, 2, '11:00', '08:00', 1);

insert into pharmacist_working_hours(pharmacist_id, working_hours_id) values (2, 8);
insert into pharmacist_working_hours(pharmacist_id, working_hours_id) values (2, 9);
insert into pharmacist_working_hours(pharmacist_id, working_hours_id) values (2, 10);
insert into pharmacist_working_hours(pharmacist_id, working_hours_id) values (2, 11);
insert into pharmacist_working_hours(pharmacist_id, working_hours_id) values (2, 12);
insert into pharmacist_working_hours(pharmacist_id, working_hours_id) values (2, 13);
insert into pharmacist_working_hours(pharmacist_id, working_hours_id) values (2, 14);

insert into patient_allergies(patient_id, allergies_id) values (1,1);

insert into supplier(id) values (5);

insert into inventory_item(id, quantity, medicine) values (1, 10000, 1);
insert into inventory_item(id, quantity, medicine) values (2, 10000, 2);
insert into inventory_item(id, quantity, medicine) values (3, 10000, 3);
insert into inventory_item(id, quantity, medicine) values (4, 10000, 4);

insert into inventory(id, supplier_id) values (1, 5);
insert into inventory_items(inventory_id, items_id) values (1, 1);
insert into inventory_items(inventory_id, items_id) values (1, 2);
insert into inventory_items(inventory_id, items_id) values (1, 3);
insert into inventory_items(inventory_id, items_id) values (1, 4);

update supplier set inventory_id=1 where id=5;

insert into pharmacy_order(id, expiry_date, pharmacy_id) values (1, '2022-02-22T22:11', 1);
insert into order_item(id, quantity, medicine) values (1, 50, 1);
insert into order_item(id, quantity, medicine) values (2, 50, 2);
insert into pharmacy_order_items(order_id, items_id) values (1, 1);
insert into pharmacy_order_items(order_id, items_id) values (1, 2);

insert into offer(id, accepted, price, shipping_date, order_id, supplier_id) values (1, b'1', 300.0, '2022-02-23T22:11', 1, 5);
insert into supplier_offers(supplier_id, offers_id) values (5, 1);