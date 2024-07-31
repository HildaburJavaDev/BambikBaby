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

`создание таблицы для детей`
```postgresql
create table childs (
	id SERIAL primary key,
	name TEXT not null,
	surname TEXT not null,
	patronimyc TEXT,
	birthday DATE not null
)
```

`создание таблицы для установления связи между родителями и детьми`
```postgresql
create table childs_parents (
	child_id BIGINT references childs(id),
	parent_id BIGINT references users(id)
)
```

`создание таблицы посещений`
```postgresql
create table attendance (
	id SERIAL primary key,
	date DATE not null,
	child_id BIGINT references childs(id),
	status BOOLEAN
)
```

`создание таблицы оснований отсутствия детей`
```postgresql
create table absence_bases(
	id SERIAL primary key,
	name TEXT NOT NULL,
	status TEXT NOT NULL,
	constraint absence_basement_status check (status in ('уважительная', 'не уважительная'))
)
```

`создание таблицы отсутствия детей`
```postgresql
create table absence (
	id SERIAL primary key,
	date DATE not null,
	child_id BIGINT references childs(id),
	base_id BIGINT references absence_bases(id)
)
```