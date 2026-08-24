-- Hourly wage rate per employee, used by daily salary calculation
-- (core-payroll). Nullable — an employee with no rate set yet simply
-- can't have payroll calculated until an admin fills it in.
ALTER TABLE employees ADD COLUMN hourly_rate NUMERIC(10,2);