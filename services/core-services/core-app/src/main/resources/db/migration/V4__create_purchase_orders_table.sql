CREATE TABLE purchase_orders (
    id BIGSERIAL PRIMARY KEY,
    branch_id BIGINT NOT NULL,
    date DATE NOT NULL,
    total_cost NUMERIC(10,2) NOT NULL,
    payment_method VARCHAR(10) NOT NULL,
    notes VARCHAR(500),
    created_by BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_purchase_orders_date_branch ON purchase_orders(date, branch_id);