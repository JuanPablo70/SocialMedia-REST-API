INSERT INTO USER_DETAILS (ID, NAME, BIRTH_DATE) VALUES (10001, 'Michael', current_date())
INSERT INTO USER_DETAILS (ID, NAME, BIRTH_DATE) VALUES (10002, 'Dwight', current_date())
INSERT INTO USER_DETAILS (ID, NAME, BIRTH_DATE) VALUES (10003, 'Jim', current_date())

INSERT INTO POST (ID, DESCRIPTION, USER_ID) VALUES (20001, 'The best boss of the world', 10001)
INSERT INTO POST (ID, DESCRIPTION, USER_ID) VALUES (20002, 'Michael is the best boss of the world', 10002)
INSERT INTO POST (ID, DESCRIPTION, USER_ID) VALUES (20003, 'Pam is beautiful', 10003)