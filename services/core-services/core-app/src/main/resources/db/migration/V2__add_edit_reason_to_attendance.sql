-- Records why an admin corrected a time-in/time-out — required by the
-- Time Records edit UI, shown back to the employee for transparency.
ALTER TABLE attendance ADD COLUMN edit_reason VARCHAR(500);