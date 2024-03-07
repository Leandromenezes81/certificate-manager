ALTER TABLE public.digital_certificate DROP CONSTRAINT federal_tax_number_unique;

ALTER TABLE public.digital_certificate ADD CONSTRAINT unique_federal_tax_created_at UNIQUE (federal_tax_number, created_at);
