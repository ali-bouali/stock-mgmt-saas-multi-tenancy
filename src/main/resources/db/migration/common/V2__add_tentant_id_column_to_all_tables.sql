-- categories
alter table public.categories
    add tenant_id varchar(255) not null;

comment on column public.categories.tenant_id is 'Tenant ID';

-- products
alter table public.products
    add tenant_id varchar(255) not null;

comment on column public.products.tenant_id is 'Tenant ID';

-- stock_mvts
alter table public.stock_mvts
    add tenant_id varchar(255) not null;

comment on column public.stock_mvts.tenant_id is 'Tenant ID';