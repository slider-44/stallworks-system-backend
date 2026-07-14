


INSERT INTO containers (container_size, description, pieces, price, active)
VALUES
('SOLO', '320CC/450ML', 4, 49.00, true),
('DOUBLE', '520CC/750ML', 8, 95.00, true),
('DOZEN', '750CC/1000ML', 12, 139.00, true),
('BARKADA', '(RE2000ML)', 30, 235.00, true),
('PAMILYA', '(RE3200ML)', 30, 349.00, true),
('BARANGAY', '(RE4000ML)', 50, 579.00, false)
ON CONFLICT (container_size) DO NOTHING;


INSERT INTO branches (branch_name, active, created_at, updated_at)
VALUES
('SAN AGUSTIN', true, NOW(), NOW()),
('EVER GREEN', true, NOW(), NOW())
ON CONFLICT (branch_name) DO NOTHING;