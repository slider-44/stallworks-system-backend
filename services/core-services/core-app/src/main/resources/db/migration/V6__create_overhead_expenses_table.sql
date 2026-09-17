CREATE TABLE overhead_expenses (
    id BIGSERIAL PRIMARY KEY,
    branch_id BIGINT NOT NULL,
    date DATE NOT NULL,
    description VARCHAR(255) NOT NULL,
    amount NUMERIC(10,2) NOT NULL,
    created_by BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);
CREATE INDEX idx_overhead_expenses_date_branch ON overhead_expenses(date, branch_id);