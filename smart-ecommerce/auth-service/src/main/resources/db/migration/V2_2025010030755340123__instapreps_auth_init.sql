-- Creating User Table
CREATE TABLE "users" (
  "id" UUID PRIMARY KEY,
  "first_name" varchar(50) NULL,
  "last_name" varchar(50) NULL,
  "username" varchar(50) UNIQUE NOT NULL,
  "password" varchar(255) NOT NULL,
  "email" varchar(100) UNIQUE NOT NULL,
  "mobile" varchar(15) UNIQUE NOT NULL,
  account_status varchar(50),
  "account_locked" boolean DEFAULT false,
  "account_expired" boolean DEFAULT false,
  "credentials_expired" boolean DEFAULT false,
  "password_expired" boolean DEFAULT false,
  "failed_attempts" int DEFAULT 0,
  "last_failed_attempt" TIMESTAMP,
  last_password_change TIMESTAMP, -- Last password change timestamp
  version bigint DEFAULT 0 NOT NULL, -- Version for optimistic locking
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Timestamp for creation
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP -- Timestamp for last update
);

-- Creating Roles Table
CREATE TABLE "roles" (
  "id" UUID PRIMARY KEY,
  "role_name" varchar(50) UNIQUE NOT NULL,
  "description" text,
  version bigint DEFAULT 0 NOT NULL, -- Version for optimistic locking
  "created_at" timestamp DEFAULT CURRENT_TIMESTAMP,
  "updated_at" timestamp DEFAULT CURRENT_TIMESTAMP
);
-- Creating Permissions Table
CREATE TABLE "permissions" (
  "id" UUID PRIMARY KEY,
  "permission_name" varchar(50) UNIQUE NOT NULL,
  "description" text,
  version bigint DEFAULT 0 NOT NULL, -- Version for optimistic locking
  "created_at" timestamp DEFAULT CURRENT_TIMESTAMP,
  "updated_at" timestamp DEFAULT CURRENT_TIMESTAMP
);

-- Creating user_roles Table
CREATE TABLE "user_roles" (
  "user_id" UUID NOT NULL,
  "role_id" UUID NOT NULL,
  version bigint DEFAULT 0 NOT NULL, -- Version for optimistic locking
  "created_at" timestamp DEFAULT CURRENT_TIMESTAMP,
  "updated_at" timestamp DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY ("user_id", "role_id") -- Composite primary key
);

-- Creating role_permissions Table
CREATE TABLE "role_permissions" (
  "role_id" UUID NOT NULL,
  "permission_id" UUID NOT NULL,
  version bigint DEFAULT 0 NOT NULL, -- Version for optimistic locking
  "created_at" timestamp DEFAULT CURRENT_TIMESTAMP,
  "updated_at" timestamp DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY ("role_id", "permission_id") -- Composite primary key
);

-- Creating tokens Table
CREATE TABLE "tokens" (
  "id" UUID PRIMARY KEY,
  "user_id" UUID NOT NULL,
  "token" text NOT NULL,
  "is_revoked" boolean DEFAULT false,
  "expires_at" timestamp NOT NULL,
  version bigint DEFAULT 0 NOT NULL, -- Version for optimistic locking
  "created_at" timestamp DEFAULT CURRENT_TIMESTAMP,
  "updated_at" timestamp DEFAULT CURRENT_TIMESTAMP
);

-- Creating Indexes
CREATE UNIQUE INDEX ON "user_roles" ("user_id", "role_id");

CREATE UNIQUE INDEX ON "role_permissions" ("role_id", "permission_id");

-- Adding foreign keys

ALTER TABLE "user_roles" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");

ALTER TABLE "user_roles" ADD FOREIGN KEY ("role_id") REFERENCES "roles" ("id");

ALTER TABLE "role_permissions" ADD FOREIGN KEY ("role_id") REFERENCES "roles" ("id");

ALTER TABLE "role_permissions" ADD FOREIGN KEY ("permission_id") REFERENCES "permissions" ("id");

ALTER TABLE "tokens" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");

-- Adding comment on table columns

COMMENT ON COLUMN "users"."id" IS 'Primary key as UUID';

COMMENT ON COLUMN "users"."username" IS 'Unique username';

COMMENT ON COLUMN "users"."password" IS 'Encrypted password';

COMMENT ON COLUMN "users"."email" IS 'Unique email';

COMMENT ON COLUMN "users"."mobile" IS 'Unique mobile';

COMMENT ON COLUMN "users"."account_status" IS 'Status of the account';

COMMENT ON COLUMN "users"."account_locked" IS 'Whether the account is locked';

COMMENT ON COLUMN "users"."account_expired" IS 'Whether the account is expired';

COMMENT ON COLUMN "users"."credentials_expired" IS 'Whether credentials are expired';

COMMENT ON COLUMN "users"."password_expired" IS 'Whether the password is expired';

COMMENT ON COLUMN "users"."failed_attempts" IS 'Number of failed login attempts';

COMMENT ON COLUMN "users"."last_failed_attempt" IS 'Timestamp of the last failed login attempt';

COMMENT ON COLUMN "users"."last_password_change" IS 'Last password change timestamp';

COMMENT ON COLUMN "users"."version" IS 'Version for optimistic locking';

COMMENT ON COLUMN "users"."created_at" IS 'Timestamp for creation';

COMMENT ON COLUMN "users"."updated_at" IS 'Timestamp for last update';

COMMENT ON COLUMN "roles"."id" IS 'Primary key as UUID';

COMMENT ON COLUMN "roles"."role_name" IS 'Unique role name (e.g., Admin, Partner)';

COMMENT ON COLUMN "roles"."description" IS 'Optional description of the role';

COMMENT ON COLUMN "roles"."created_at" IS 'Timestamp for creation';

COMMENT ON COLUMN "roles"."updated_at" IS 'Timestamp for last update';

COMMENT ON COLUMN "permissions"."id" IS 'Primary key as UUID';

COMMENT ON COLUMN "permissions"."permission_name" IS 'Unique permission name (e.g., READ_USER, WRITE_USER)';

COMMENT ON COLUMN "permissions"."description" IS 'Optional description of the permission';

COMMENT ON COLUMN "permissions"."created_at" IS 'Timestamp for creation';

COMMENT ON COLUMN "permissions"."updated_at" IS 'Timestamp for last update';

COMMENT ON COLUMN "user_roles"."user_id" IS 'Foreign key to users table';

COMMENT ON COLUMN "user_roles"."role_id" IS 'Foreign key to roles table';

COMMENT ON COLUMN "user_roles"."created_at" IS 'Timestamp for creation';

COMMENT ON COLUMN "role_permissions"."role_id" IS 'Foreign key to roles table';

COMMENT ON COLUMN "role_permissions"."permission_id" IS 'Foreign key to permissions table';

COMMENT ON COLUMN "role_permissions"."created_at" IS 'Timestamp for creation';

COMMENT ON COLUMN "tokens"."id" IS 'Primary key as UUID';

COMMENT ON COLUMN "tokens"."user_id" IS 'Foreign key to users table';

COMMENT ON COLUMN "tokens"."token" IS 'The refresh token';

COMMENT ON COLUMN "tokens"."is_revoked" IS 'Whether the token has been revoked';

COMMENT ON COLUMN "tokens"."expires_at" IS 'Expiration timestamp for the token';

COMMENT ON COLUMN "tokens"."created_at" IS 'Timestamp for creation';

COMMENT ON COLUMN "tokens"."updated_at" IS 'Timestamp for last update';

