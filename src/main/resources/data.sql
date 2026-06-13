INSERT INTO TB_ROLES (role_id, nome) VALUES (1, 'ADMIN') ON CONFLICT (role_id) DO NOTHING;
INSERT INTO TB_ROLES (role_id, nome) VALUES (2, 'BASIC') ON CONFLICT (role_id) DO NOTHING;

INSERT INTO TB_USER (
    user_id,
    email,
    senha
)
VALUES (
    gen_random_uuid(),
    'admin@admin.com',
    '$2a$10$S90xFhTdE2zwbB4nyEHqpeL6WbY.5SE98U7oYC3s0OhRGYXRlYEQm'
)
ON CONFLICT (email) DO NOTHING;

INSERT INTO TB_USER_ROLES (user_fk, role_fk)
SELECT u.user_id, r.role_id
FROM TB_USER u
JOIN TB_ROLES r ON r.nome = 'ADMIN'
WHERE email = 'admin@admin.com'
ON CONFLICT DO NOTHING;