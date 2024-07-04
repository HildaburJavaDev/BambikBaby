# BambikBaby

## Создание Базы Данных

`Создание таблицы с ролей пользователей`

```postgresql
CREATE TABLE user_roles (
	id SERIAL PRIMARY KEY,
	name TEXT NOT NULL
);

INSERT INTO user_roles VALUES (DEFAULT, 'admin'), (DEFAULT, 'employee'), (DEFAULT, 'user');
```

`Создание таблицы пользователей`

```postgresql
CREATE TABLE users (
	id SERIAL PRIMARY KEY,
	role_id BIGINT REFERENCES user_roles(id),
	phone_number VARCHAR(11) NOT NULL,
	firstname TEXT NOT NULL,
	lastname TEXT NOT NULL,
	patronymic TEXT,
	password TEXT NOT NULL,
	created_at DATE,
	deleted_at DATE
)
```
`создание таблицы детских групп`

```postgresql
create table child_groups (
	id SERIAL PRIMARY KEY,
	chief_id BIGINT REFERENCES users(id),
	title TEXT NOT NULL
)
```