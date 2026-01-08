# Database Migrations with Flyway

This directory contains Flyway database migration scripts. Migrations are automatically applied when the application starts.

## Migration File Naming Convention

Flyway migration files must follow this naming pattern:
- **Versioned migrations**: `V{version}__{description}.sql`
  - Example: `V1__Create_users_table.sql`
  - Example: `V2__Add_index_to_email.sql`
- **Repeatable migrations**: `R__{description}.sql` (runs every time if checksum changes)
  - Example: `R__Update_views.sql`

## Creating New Migrations

1. **Create a new SQL file** following the naming convention:
   ```
   V{next_version_number}__{descriptive_name}.sql
   ```

2. **Write your SQL statements** in the file (e.g., `V2__Add_index_to_email.sql`):
   ```sql
   -- Add index to email column for faster lookups
   CREATE INDEX idx_email ON users(email);
   ```

3. **Version numbers must be sequential** and higher than the previous migration.

## Important Notes

- **Never edit existing migration files** once they've been applied to any environment
- **Test migrations in dev** before applying to production
- **Use transactions** where possible for rollback safety
- **Always backup production** before running migrations
- Flyway creates a `flyway_schema_history` table to track applied migrations

## Migration Order

Migrations are applied in version order:
1. `V1__Create_users_table.sql`
2. `V2__...` (next migration)
3. `V3__...` (and so on)

## Checking Migration Status

You can check which migrations have been applied by querying:
```sql
SELECT * FROM flyway_schema_history ORDER BY installed_rank;
```

## Configuration

Flyway is configured in `application.yml` and profile-specific files:
- Base config: `application.yml`
- Dev: `application-dev.yml`
- Prod: `application-prod.yml`

Configuration includes:
- `baseline-on-migrate: true` - Automatically baseline existing databases
- `validate-on-migrate: true` - Validate migrations before applying
- `clean-disabled: true` - Prevent accidental database drops

