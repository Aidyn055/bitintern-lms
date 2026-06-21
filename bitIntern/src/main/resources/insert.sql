SET session_replication_role = 'replica';

DELETE FROM user_role_mapping WHERE user_id IN ('usr-admin-001', 'usr-teacher-002', 'usr-user-003') OR role_id IN ('role-admin-id-001', 'role-teacher-id-002', 'role-user-id-003');
DELETE FROM credential WHERE user_id IN ('usr-admin-001', 'usr-teacher-002', 'usr-user-003');
DELETE FROM user_entity WHERE id IN ('usr-admin-001', 'usr-teacher-002', 'usr-user-003') OR username IN ('admin', 'teacher', 'user');
DELETE FROM keycloak_role WHERE id IN ('role-admin-id-001', 'role-teacher-id-002', 'role-user-id-003');

INSERT INTO keycloak_role (id, client_role, name, realm_id, description)
VALUES ('role-admin-id-001', false, 'ROLE_ADMIN', '9937ebf5-cb0b-4460-bc34-da53d7c33098', 'Administrator');

INSERT INTO keycloak_role (id, client_role, name, realm_id, description)
VALUES ('role-teacher-id-002', false, 'ROLE_TEACHER', '9937ebf5-cb0b-4460-bc34-da53d7c33098', 'Teacher');

INSERT INTO keycloak_role (id, client_role, name, realm_id, description)
VALUES ('role-user-id-003', false, 'ROLE_USER', '9937ebf5-cb0b-4460-bc34-da53d7c33098', 'Regular User');

INSERT INTO user_entity (id, email, email_constraint, email_verified, enabled, first_name, last_name, username, realm_id, created_timestamp)
VALUES ('usr-admin-001', 'admin@bitintern.com', 'admin@bitintern.com', true, true, 'Admin', 'System', 'admin', '9937ebf5-cb0b-4460-bc34-da53d7c33098', 1718800000000);

INSERT INTO user_entity (id, email, email_constraint, email_verified, enabled, first_name, last_name, username, realm_id, created_timestamp)
VALUES ('usr-teacher-002', 'teacher@bitintern.com', 'teacher@bitintern.com', true, true, 'Teacher', 'Instructor', 'teacher', '9937ebf5-cb0b-4460-bc34-da53d7c33098', 1718800000000);

INSERT INTO user_entity (id, email, email_constraint, email_verified, enabled, first_name, last_name, username, realm_id, created_timestamp)
VALUES ('usr-user-003', 'user@bitintern.com', 'user@bitintern.com', true, true, 'User', 'Student', 'user', '9937ebf5-cb0b-4460-bc34-da53d7c33098', 1718800000000);

INSERT INTO credential (id, user_id, type, user_label, secret_data, credential_data, priority)
VALUES ('cred-admin-001', 'usr-admin-001', 'password', 'Password', '{"value":"gsh6mZ8Wn8VvWbKz9vGvXw7Wv8E=","salt":"8vGvXw7Wv8E=","additionalParameters":{}}', '{"hashIterations":27500,"algorithm":"pbkdf2-sha256","additionalParameters":{}}', 10);

INSERT INTO credential (id, user_id, type, user_label, secret_data, credential_data, priority)
VALUES ('cred-teacher-002', 'usr-teacher-002', 'password', 'Password', '{"value":"bXpWNDU2Nzg5MGFiY2RlZg==","salt":"c2FsdF90ZWFjaGVy","additionalParameters":{}}', '{"hashIterations":27500,"algorithm":"pbkdf2-sha256","additionalParameters":{}}', 10);

INSERT INTO credential (id, user_id, type, user_label, secret_data, credential_data, priority)
VALUES ('cred-user-003', 'usr-user-003', 'password', 'Password', '{"value":"Y29tY29tY29tY29tY29t","salt":"c2FsdF91c2Vy","additionalParameters":{}}', '{"hashIterations":27500,"algorithm":"pbkdf2-sha256","additionalParameters":{}}', 10);

INSERT INTO user_role_mapping (user_id, role_id) VALUES ('usr-admin-001', 'role-admin-id-001');
INSERT INTO user_role_mapping (user_id, role_id) VALUES ('usr-teacher-002', 'role-teacher-id-002');
INSERT INTO user_role_mapping (user_id, role_id) VALUES ('usr-user-003', 'role-user-id-003');

SET session_replication_role = 'origin';