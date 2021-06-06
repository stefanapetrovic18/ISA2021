INSERT INTO apoteka.role(id, name) VALUES
(1, 'ROLE_PATIENT'),
(2, 'ROLE_PHARMACIST'),
(3, 'ROLE_DERMATOLOGIST'),
(4, 'ROLE_SYSTEM_ADMIN'),
(5, 'ROLE_SUPPLIER'),
(6, 'ROLE_PHARMACY_ADMIN'),
(7, 'ROLE_PHARMACIST');

INSERT INTO apoteka.pricelist(id, consultation_price, examination_price) VALUES
(1, 13, 11),
(2, 10, 15),
(3, 20, 30),
(4, 30, 40),
(5, 45, 60);

INSERT INTO apoteka.pharmacy(id, address, description, name, rating, pricelist_id) VALUES
(1, 'Zmaj Jovina 2, Novi Sad', 'Hej loo', 'Zegin', 3, 5),
(2, 'Lovcenska 2, Novi Sad', 'Sve na jednom mestu', 'Benu', 4.2, 2),
(3, 'Vojvode Bojovica 1, Novi Sad', 'Nasi proizvodi za vase dobro', 'Jankovic', 4.8, 3),
(4, 'Bulevar Oslobodjenja 22, Novi Sad', 'Prirodno i dobro', 'Zelena apoteka', 4.1, 4),
(5, 'Strazilovska 1, Novi Sad', 'Rucno dodata', 'BigPharma', NULL, 1);

INSERT INTO apoteka.user(id, address, city, country, enabled, forename, password, password_changed, phone, surname, username, validated) VALUES
(1, 'Slovacka 1', 'Novi Sad', 'Srbija', True, 'PAC1', '$2a$10$PCJEmmf5YwLYkfy2yAUZI.j4wgsurdFRXu.RU4RGRcqsCNN0T59my', True, '1234567', 'PAC1', 'patient@gmail.com', True),
(2, 'Slovacka 2', 'Novi Sad', 'Srbija', True, 'PHA1', '$2a$10$PCJEmmf5YwLYkfy2yAUZI.j4wgsurdFRXu.RU4RGRcqsCNN0T59my', True, '1234562', 'PHA1', 'pharmacist@gmail.com', True),
(3, 'Slovacka 3', 'Novi Sad', 'Srbija', True, 'DER1', '$2a$10$PCJEmmf5YwLYkfy2yAUZI.j4wgsurdFRXu.RU4RGRcqsCNN0T59my', True, '1230456', 'DER1', 'dermatologist@gmail.com', True),
(4, 'Slovacka 4', 'Novi Sad', 'Srbija', True, 'SAD1', '$2a$10$PCJEmmf5YwLYkfy2yAUZI.j4wgsurdFRXu.RU4RGRcqsCNN0T59my', True, '1023456', 'SAD1', 'admin@gmail.com', True),
(5, 'Slovacka 5', 'Novi Sad', 'Srbija', True, 'SUP1', '$2a$10$PCJEmmf5YwLYkfy2yAUZI.j4wgsurdFRXu.RU4RGRcqsCNN0T59my', True, '1234056', 'SUP1', 'supplier@gmail.com', True),
(6, 'Slovacka 6', 'Novi Sad', 'Srbija', True, 'PAD1', '$2a$10$PCJEmmf5YwLYkfy2yAUZI.j4wgsurdFRXu.RU4RGRcqsCNN0T59my', True, '1203456', 'PAD1', 'pharmacy_admin@gmail.com', True),
(7, 'Slovacka 7', 'Novi Sad', 'Srbija', True, 'PAD2', '$2a$10$PCJEmmf5YwLYkfy2yAUZI.j4wgsurdFRXu.RU4RGRcqsCNN0T59my', True, '0698784', 'PAD2', 'pharmacy_admin2@gmail.com', True),
(8, 'Slovacka 8', 'Novi Sad', 'Srbija', True, 'PAD3', '$2a$10$PCJEmmf5YwLYkfy2yAUZI.j4wgsurdFRXu.RU4RGRcqsCNN0T59my', True, '2342343', 'PAD3', 'pharmacy_admin1@gmail.com', True),
(9, 'Slovacka 9', 'Novi Sad', 'Srbija', True, 'PHA2', '$2a$10$PCJEmmf5YwLYkfy2yAUZI.j4wgsurdFRXu.RU4RGRcqsCNN0T59my', True, '3090568', 'PHA2', 'pharmacist1@gmail.com', True);

INSERT INTO apoteka.pharmacist(rating, vacation_end, vacation_start, id, pharmacy) VALUES
(5, '2021-05-20 22:11:00', '2021-02-21 22:11:00', 2, 1),
(0, NULL, NULL, 9, 1);
INSERT INTO apoteka.dermatologist(rating, vacation_end, vacation_start, id) VALUES
(5, '2021-05-20 22:11:00', '2021-05-22 22:11:00', 3),
(NULL, NULL, NULL, 8);
INSERT INTO apoteka.patient(points, id) VALUES
(0, 1);
INSERT INTO apoteka.pharmacy_admin(id, pharmacy) VALUES
(6, 1),
(7, 5);
INSERT INTO apoteka.system_admin(id) VALUES
(4);

INSERT INTO apoteka.pharmacy_dermatologists(pharmacy_id, dermatologists_id) VALUES
(1, 3),
(1, 3);


INSERT INTO apoteka.user_roles(user_id, role_id) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5),
(6, 6);

INSERT INTO apoteka.medicine(id, code, form, manufacturer, name, prescription_necessary, recommended_dose, side_effects, type, rating) VALUES
(1, '1234', 'form', 'Galenika', 'Bromazepam', False, 'One per day', 'none', 'type', NULL),
(2, '1122', 'form', 'Galenika', 'Aspirin ++', False, 'One per day', 'none', 'type', 4),
(3, '1212', 'form', 'Alkaloid', 'Magnezijum', False, 'Ten per day', 'ache', 'type', NULL),
(4, '1221', 'form', 'Alkaloid', 'KolagenBTY', False, 'One per day', 'none', 'type', NULL),
(5, '2211', 'form', 'PharmaSX', 'Caffetin X', False, 'Two per day', 'pain', 'type', NULL);

INSERT INTO apoteka.medicine_ingredients(medicine_id, ingredients) VALUES
(1, 'H2O'),
(1, 'rakija'),
(1, 'O'),
(2, 'C'),
(2, 'Cl'),
(3, 'Mg'),
(3, 'C'),
(3, 'Fe'),
(3, 'Ce'),
(4, 'Biotin'),
(4, 'C vitamin'),
(4, 'Kreatin'),
(4, 'D vitamin'),
(5, 'Febricet');

INSERT INTO apoteka.item(id, price, valid_from, valid_until, medicine, pricelist) VALUES
(1, 255, '2021-02-20 22:11:00', '2022-02-20 22:11:00', 1, 1),
(2, 150, '2021-02-20 22:11:00', '2022-02-20 22:11:00', 2, 1),
(3, 330, '2021-02-20 22:11:00', '2022-02-20 22:11:00', 3, 1),
(4, 440, '2021-06-09 10:06:00', '2021-06-25 10:06:00', 1, 1),
(5, 222, '2021-06-05 10:21:00', '2021-06-25 10:21:00', 1, 5),
(7, 350, '2021-06-26 16:33:00', '2021-07-01 16:33:00', 5, 5);

INSERT INTO apoteka.consultation(id, consultation_date, duration, price, patient, pharmacist, pharmacy) VALUES
(1, '2021-02-20 12:11:00', 20, 1300, 1, 2, 1),
(2, '2021-02-21 11:11:00', 25, 3010, 1, 2, 1),
(4, '2021-06-17 09:06:00', 66, 4500, 1, 2, 1),
(5, '2021-07-03 09:28:00', 55, 4500, 1, 2, 1),
(6, '2021-02-22 10:11:00', 32, 3100, 1, 2, 1),
(7, '2021-06-18 09:06:00', 66, 4500, 1, 2, 1),
(8, '2021-07-04 09:28:00', 55, 4500, 1, 2, 1),
(9, '2021-06-19 10:43:00', 75, 4500, 1, 2, 1);

INSERT INTO apoteka.examination(id, duration, examination_date, price, quick_reservation, dermatologist, patient, pharmacy) VALUES
(1, 12, '2021-02-20 22:11:00', 3100, True, 3, 1, 1),
(2, 13, '2022-02-21 08:11:00', 1000, True, 3, 1, 1),
(3, 12, '2021-06-17 10:09:00', 1555, True, 3, 1, 1),
(5, 12, '2021-06-18 11:45:00', 7200, True, 3, 1, 1),
(6, 12, '2021-06-19 12:45:00', 7200, True, 3, 1, 1),
(7, 10, '2021-06-20 13:53:00', 6000, True, 3, 1, 1),
(8, 10, '2021-06-21 14:53:00', 6000, True, 3, 1, 1),
(9, 10, '2021-06-22 15:58:00', 6000, True, 3, 1, 1);

INSERT INTO apoteka.reservation(id, collected, collection_date, penalized, reservation_date, reservation_number, medicine, patient, pharmacy) VALUES
(1, False, '2021-06-06 13:17:56', True, '2021-06-04 13:17:56.6', '0022212', 1, 1, 1),
(2, False, '2022-02-21 22:11:00', True, '2022-02-20 22:11:00', '212312321', 1, 1, 1),
(3, False, '2022-02-21 22:11:00', True, '2022-02-20 22:11:00', '054456456', 1, 1, 1),
(4, False, '2022-02-21 22:11:00', True, '2022-02-20 22:11:00', '012222444', 1, 1, 1),
(5, False, '2021-06-11 16:58:00', True, '2021-06-11 16:58:00', '243888888', 1, 1, 1),
(6, False, '2021-06-05 19:00:00', True, '2021-06-04 15:00:14.389', '23544', 1, 1, 1),
(7, False, '2021-06-18 17:16:00', False, '2021-06-04 15:16:48.601', '5353', 2, 1, 1),
(8, False, '2021-06-15 18:16:00', False, '2021-06-04 16:16:41.991', '4555', 1, 1, 1),
(9, True, '2020-06-08 18:17:00', False, '2020-06-04 16:17:03.287', '44558', 2, 1, 1),
(10, False, '2021-06-18 19:33:00', False, '2021-06-06 17:33:24.175', '011', 1, 1, 1);

INSERT INTO apoteka.vacation_request(id, accepted, employeeid, rejected, rejection_reason, vacation_end, vacation_start, pharmacy_id) VALUES
(1, True, 2, False, NULL, '2021-02-27 22:11:00', '2021-02-26 22:11:00', 1),
(2, False, 3, True, 'Eto tako', '2022-02-27 22:11:00', '2022-02-26 22:11:00', 1),
(3, True, 2, False, NULL, '2022-02-27 22:11:00', '2022-02-26 22:11:00', 1),
(4, False, 3, False, NULL, '2021-07-27 22:11:00', '2021-07-26 22:11:00', 1);

INSERT INTO apoteka.working_hours(id, day_of_week, employeeid, shift_end, shift_start, pharmacy) VALUES
(1, 0, 3, '20:00:00', '08:00:00', 1),
(2, 1, 3, '20:00:00', '08:00:00', 1),
(3, 2, 3, '20:00:00', '08:00:00', 1),
(4, 3, 3, '20:00:00', '08:00:00', 1),
(5, 4, 3, '20:00:00', '08:00:00', 1),
(6, 5, 3, '20:00:00', '08:00:00', 1),
(7, 6, 3, '20:00:00', '08:00:00', 1),
(8, 0, 2, '20:00:00', '08:00:00', 1),
(9, 1, 2, '20:00:00', '08:00:00', 1),
(10, 2, 2, '20:00:00', '08:00:00', 1),
(11, 3, 2, '20:00:00', '08:00:00', 1),
(12, 4, 2, '20:00:00', '08:00:00', 1),
(13, 5, 2, '20:00:00', '08:00:00', 1),
(14, 6, 2, '20:00:00', '08:00:00', 1);

INSERT INTO apoteka.pharmacist_working_hours(pharmacist_id, working_hours_id) VALUES
(2, 8),
(2, 9),
(2, 10),
(2, 11),
(2, 12),
(2, 13),
(2, 14);

INSERT INTO apoteka.dermatologist_working_hours(dermatologist_id, working_hours_id) VALUES
(3, 1),
(3, 2),
(3, 3),
(3, 4),
(3, 5),
(3, 6),
(3, 7);

INSERT INTO apoteka.inventory_item(id, quantity, medicine) VALUES
(1, 10, 1),
(2, 10, 2),
(3, 10, 3),
(4, 10, 4);

INSERT INTO apoteka.inventory(id) VALUES
(1),
(2),
(3),
(4);

INSERT INTO apoteka.supplier(id, inventory_id) VALUES
(5, 1);

INSERT INTO apoteka.inventory_items(inventory_id, items_id) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4);

INSERT INTO apoteka.pharmacy_order(id, expiry_date, pharmacy_id) VALUES
(1, '2022-02-22 22:11:00', 1),
(2, '2021-06-04 19:29:00', 1),
(3, '2021-06-11 17:03:00', 1);

INSERT INTO apoteka.order_item(id, quantity, medicine) VALUES
(1, 50, 1),
(2, 50, 2),
(3, 33, 1),
(4, 52, 5);

INSERT INTO apoteka.pharmacy_order_items(order_id, items_id) VALUES
(1, 1),
(1, 2),
(2, 3),
(3, 4);

INSERT INTO apoteka.offer(id, accepted, price, shipping_date, order_id, supplier_id) VALUES
(1, True, 300000, '2022-02-23 22:11:01.343039', 1, 5),
(2, False, 37673, '2021-06-08 19:29:51.179199', 2, 5),
(3, False, 45464, '2021-06-08 19:29:51.179199', 2, 5),
(4, True, 464646, '2021-06-08 19:29:51.179199', 2, 5),
(5, False, 64666, '2021-06-03 19:29:51.179199', 2, 5),
(6, NULL, 600000, '2021-06-19 19:29:51.179199', 1, 5),
(7, NULL, 600004, '2021-06-05 19:29:51.179199', 1, 5),
(8, NULL, 742910, '2021-06-05 17:03:50.544454', 3, 5);

#INSERT INTO apoteka.supplier_offers(supplier_id, offers_id) VALUES
#(5, 1);





INSERT INTO apoteka.complaint(id, resolved, text, patient) VALUES
(1, True, 'Zalba na apoteku.\n\nID apoteke: 1\n\nID pacijenta: 1\n\nDatum podnošenja: 2021-06-05T16:14:45.504289500', 1),
(2, False, 'Zalba na dermatologa.\n\nID dermatologa: 3\n\nID pacijenta: 1\n\nDatum podnošenja: 2021-06-06T02:40:10.405950300', 1),
(3, False, 'Zalba na apoteku.\n\nID apoteke: 1\n\nID pacijenta: 1\n\nDatum podnošenja: 2021-06-05T16:14:45.504289500', 1),
(4, False, 'Zalba na farmaceuta.\n\nID farmaceuta: 2\n\nID pacijenta: 1\n\nDatum podnošenja: 2021-06-06T02:40:10.405950300', 1);

INSERT INTO apoteka.dermatologist_pharmacies(dermatologist_id, pharmacies_id) VALUES
(3, 1),
(8, 1);

INSERT INTO apoteka.hibernate_sequence(next_val) VALUES
(1);

INSERT INTO apoteka.rating(id, rating, patient) VALUES
(1, 3, 1),
(2, 5, 1),
(3, 3, 1),
(4, 5, 1);
INSERT INTO apoteka.dermatologist_ratings(dermatologist_id, ratings_id) VALUES
(3, 4);
INSERT INTO apoteka.medicine_ratings(medicine_id, ratings_id) VALUES
(2, 3);
INSERT INTO apoteka.pharmacy_ratings(pharmacy_id, ratings_id) VALUES
(1, 1);

INSERT INTO apoteka.patient_allergies(patient_id, allergies_id) VALUES
(1, 4);

INSERT INTO apoteka.patient_subscriptions(patient_id, subscriptions_id) VALUES
(1, 1);


INSERT INTO apoteka.pharmacy_subscriptions(pharmacy_id, subscriptions_id) VALUES
(1, 1);

INSERT INTO apoteka.promotion(id, description, end_date, start_date, title, pharmacy_id) VALUES
(1, 'Desc1', '2022-02-21 22:11:00', '2021-06-04 13:17:56.6', 'T1', 1),
(2, 'Desc2', '2022-02-21 22:11:00', '2021-06-04 13:17:56.6', 'T2', 2),
(3, 'Desc3', '2022-02-21 22:11:00', '2021-06-04 13:17:56.6', 'T3', 3),
(4, 'Desc4', '2023-02-21 22:11:00', '2022-02-21 22:11:01.5', 'T4', 1),
(5, 'Desc5', '2021-07-03 19:27:00', '2021-06-12 19:26:01.4', 'T5', 1);

INSERT INTO apoteka.stockpile(id, quantity, medicine, pharmacy_id) VALUES
(1, 350, 5, 1),
(2, 154, 2, 1),
(3, 500, 3, 1),
(4, 400, 4, 2),
(5, 300, 1, 2);
