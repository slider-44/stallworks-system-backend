
-- Branch table
CREATE TABLE branches (
    id BIGSERIAL PRIMARY KEY,
    branch_name VARCHAR(255) NOT NULL UNIQUE,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);


-- Employee table with recommended constraints
CREATE TABLE employees (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    phone_number VARCHAR(20),
    role VARCHAR(50) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    
    -- Unique constraint on phone_number (recommended)
    CONSTRAINT uq_employee_phone UNIQUE (phone_number)
);


-- Employee branch mapping table
CREATE TABLE employee_branches (
    id BIGSERIAL PRIMARY KEY,
    employee_id BIGINT NOT NULL,
    branch_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,

    CONSTRAINT fk_employee_branch_employee
        FOREIGN KEY (employee_id)
        REFERENCES employees(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_employee_branch_branch
        FOREIGN KEY (branch_id)
        REFERENCES branches(id)
        ON DELETE CASCADE,

    CONSTRAINT uq_employee_branch
        UNIQUE (employee_id, branch_id)
);

CREATE TABLE containers (
    id BIGSERIAL PRIMARY KEY,
    container_size VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(255),
    pieces INTEGER NOT NULL,
    price NUMERIC(10,2) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE
);


CREATE TABLE sales_reports (
    id BIGSERIAL PRIMARY KEY,
    employee_id BIGINT NOT NULL,
    branch_id BIGINT NOT NULL,
    date DATE NOT NULL,
    time_in TIME NOT NULL,
    time_out TIME,
    total_sales NUMERIC(10,2) NOT NULL,
    created_by BIGINT,
    updated_by BIGINT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW(),

    -- Unique constraint matching your entity
    CONSTRAINT uk_sales_reports_date_branch UNIQUE (date, branch_id)
);


CREATE TABLE sales_line_items (
    id BIGSERIAL PRIMARY KEY,
    sales_report_id BIGINT NOT NULL,
    container_size VARCHAR(50) NOT NULL,
    unit_price NUMERIC(10,2) NOT NULL,
    quantity_sold INTEGER NOT NULL,
    line_total NUMERIC(10,2) NOT NULL,

    CONSTRAINT fk_sales_report
        FOREIGN KEY (sales_report_id)
        REFERENCES sales_reports(id)
        ON DELETE CASCADE
);

CREATE TABLE daily_expenses (
    id BIGSERIAL PRIMARY KEY,
    branch_id BIGINT NOT NULL,
    date DATE NOT NULL,
    description TEXT NOT NULL,                  
    amount NUMERIC(10,2) NOT NULL,  
     created_by BIGINT,
    updated_by BIGINT,              
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE cash_summaries (
    id BIGSERIAL PRIMARY KEY,
    date DATE NOT NULL,
    branch_id BIGINT NOT NULL,
    petty_cash_yesterday NUMERIC(10,2),
    gcash NUMERIC(10,2),
    petty_cash_nextday NUMERIC(10,2),
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW(),

    CONSTRAINT uk_cash_summaries_date_branch UNIQUE (date, branch_id)
);

CREATE TABLE bill_count_lines (
    id BIGSERIAL PRIMARY KEY,
    cash_summary_id BIGINT NOT NULL,
    denomination INTEGER NOT NULL,
    count INTEGER NOT NULL,

    CONSTRAINT fk_bill_count_lines_cash_summary
        FOREIGN KEY (cash_summary_id) REFERENCES cash_summaries(id) ON DELETE CASCADE
);

CREATE INDEX idx_bill_count_lines_cash_summary_id ON bill_count_lines(cash_summary_id);

-- =============================================
-- Attendance Table
-- =============================================

CREATE TABLE attendance (
    id BIGSERIAL PRIMARY KEY,
    
    employee_id BIGINT NOT NULL,
    branch_id BIGINT NOT NULL,
    date DATE NOT NULL,
    time_in TIME,
    time_out TIME,
    updated_by BIGINT,
    created_by BIGINT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW(),

    -- Unique constraint: One attendance record per employee per day
    CONSTRAINT uk_attendance_employee_date 
        UNIQUE (employee_id, date),

    -- Foreign key constraints
    CONSTRAINT fk_attendance_employee
        FOREIGN KEY (employee_id)
        REFERENCES employees(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_attendance_branch
        FOREIGN KEY (branch_id)
        REFERENCES branches(id)
        ON DELETE CASCADE
);