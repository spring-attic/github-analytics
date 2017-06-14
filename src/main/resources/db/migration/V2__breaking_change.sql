-- This change is backward incompatible - you can't do A/B testing
ALTER TABLE issues CHANGE repository repo VARCHAR(255);