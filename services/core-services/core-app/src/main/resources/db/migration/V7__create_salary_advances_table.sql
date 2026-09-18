CREATE TABLE salary_advances (
    id BIGSERIAL PRIMARY KEY,
    employee_id BIGINT NOT NULL,
    branch_id BIGINT NOT NULL,
    date DATE NOT NULL,
    amount NUMERIC(10,2) NOT NULL,
    note VARCHAR(500),
    created_by BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);
CREATE INDEX idx_salary_advances_date_branch ON salary_advances(date, branch_id);
CREATE INDEX idx_salary_advances_employee_date ON salary_advances(employee_id, date);