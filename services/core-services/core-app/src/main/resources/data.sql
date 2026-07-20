


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

-- =============================================
-- Employee Data (Idempotent)
-- =============================================

INSERT INTO employees (first_name, last_name, phone_number, role, active, created_at, updated_at)
VALUES 
    ('Elizabeth', 'Laguardia', '9260060186', 'ADMIN', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Mhay', 'Gojas', '9260060196', 'STAFF', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
ON CONFLICT (phone_number) DO NOTHING;


-- =============================================
-- Employee Branch Mappings (Idempotent)
-- =============================================

INSERT INTO employee_branches (employee_id, branch_id, created_at, updated_at)
VALUES 
    (1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),   -- Elizabeth - Branch 1
    (1, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),   -- Elizabeth - Branch 2
    (2, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)   -- Mhay - Branch 2
ON CONFLICT (employee_id, branch_id) DO NOTHING;


-- =============================================
-- TRUNCATE ALL SALES & EXPENSES (Fastest & Cleanest)
-- =============================================

TRUNCATE TABLE 
    sales_line_items,
    sales_reports,
    daily_expenses 
RESTART IDENTITY CASCADE;

-- Verify
SELECT 'Sales Reports:' AS table_name, COUNT(*) FROM sales_reports;
SELECT 'Sales Line Items:' AS table_name, COUNT(*) FROM sales_line_items;
SELECT 'Daily Expenses:' AS table_name, COUNT(*) FROM daily_expenses;