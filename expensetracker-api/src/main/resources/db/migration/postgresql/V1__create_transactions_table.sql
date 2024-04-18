create sequence if not exists ex_trn_seq start with 1 increment by 50;
create table if not exists transaction (
        id bigint default nextval('ex_trn_seq'),
        amount numeric(38,2) not null,
        category varchar(255) check (category in ('FOOD','RENT','TRANSPORTATION','ENTERTAINMENT','GROCERIES','SHOPPING','MUTUAL_FUNDS','STOCKS','OTHER_INVESTMENTS','LOAN','OTHER','SALARY')),
        created_at timestamp(6) not null,
        transaction_date date not null,
        description varchar(255) not null,
        type varchar(255) not null check (type in ('INCOME','EXPENSE','INVESTMENT')),
        user_id bigint default null,
        primary key (id)
    );