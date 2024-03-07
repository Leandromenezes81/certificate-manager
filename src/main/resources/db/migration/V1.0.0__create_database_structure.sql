CREATE TABLE IF NOT EXISTS public.digital_certificate (
    id numeric NOT NULL,
    company_name varchar NOT NULL,
    federal_tax_number varchar NOT NULL,
    company_type char(1) NOT NULL,
    issuer varchar NOT NULL,
    certificate_data text NOT NULL,
    password varchar NOT NULL,
    created_at timestamp NULL,
    valid_until timestamp NULL,
    CONSTRAINT digital_certificate_pk PRIMARY KEY (id),
    CONSTRAINT federal_tax_number_unique UNIQUE (federal_tax_number),
    CONSTRAINT check_company_type CHECK (company_type IN ('F', 'J'))
    );
CREATE INDEX digital_certificate_company_name ON public.digital_certificate USING btree (company_name);
CREATE INDEX digital_certificate_federal_tax_number ON public.digital_certificate USING btree (federal_tax_number);
