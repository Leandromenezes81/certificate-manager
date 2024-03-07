ALTER TABLE IF EXISTS public.digital_certificate
    RENAME CONSTRAINT unique_federal_tax_created_at TO federal_tax_number_created_at_unique;