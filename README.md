# Quản lý CLB & Sự kiện

## Chạy

1. PostgreSQL + database `rungroup`
2. Run `Demmo1Application`
3. http://localhost:8080/clubs

## Bảng events

Lần **đầu** sau khi sửa code, app xóa bảng `events` cũ (sai cột `club-id` / `clubs`) và Hibernate tạo lại bảng chuẩn:

- `id`, `name`, `start_time`, `end_time`, `created_on`, `club_id`

Khi đã chạy ổn, có thể tắt xóa tự động bằng cách comment 3 dòng trong `application.properties`:

```properties
# spring.jpa.defer-datasource-initialization=true
# spring.sql.init.mode=always
# spring.sql.init.schema-locations=classpath:db/reset-events-table.sql
```

## Flow

- `/clubs` — danh sách CLB
- `/clubs/{id}/events` — sự kiện theo CLB
- `/clubs/{id}/events/new` — tạo sự kiện
