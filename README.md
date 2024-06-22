# BambikBaby

## Создание Базы Данных

`Создание таблицы с ролей пользователей`

```sql
CREATE TABLE user_roles (
	id SERIAL PRIMARY KEY,
	title TEXT NOT NULL
);

INSERT INTO user_roles VALUES (DEFAULT, 'admin'), (DEFAULT, 'employee'), (DEFAULT, 'user');
```

`Создание таблицы пользователей`

```sql
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