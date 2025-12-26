-- Crear schema compartido
CREATE SCHEMA IF NOT EXISTS shared;

-- Tabla tenants con UUID
CREATE TABLE shared.tenants (
                                id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                name VARCHAR(256) NOT NULL UNIQUE,
                                schema_name VARCHAR(256) NOT NULL UNIQUE,
                                subdomain VARCHAR(100) NOT NULL UNIQUE,
                                owner_email VARCHAR(255) NOT NULL,
                                status VARCHAR(20) DEFAULT 'active',
                                subscription_plan VARCHAR(50),
                                created_at TIMESTAMP DEFAULT NOW(),
                                updated_at TIMESTAMP DEFAULT NOW(),
                                max_users INTEGER DEFAULT 500,
                                max_branches INTEGER DEFAULT 5
);

CREATE INDEX idx_tenants_subdomain ON shared.tenants(subdomain);
CREATE INDEX idx_tenants_status ON shared.tenants(status);

-- Super admins con UUID
CREATE TABLE shared.superadmins (
                                    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                    user_id UUID NOT NULL UNIQUE,
                                    email VARCHAR(255) NOT NULL UNIQUE,
                                    full_name VARCHAR(255),
                                    password_hash VARCHAR(255) NOT NULL,
                                    is_active BOOLEAN DEFAULT TRUE,
                                    created_at TIMESTAMP DEFAULT NOW()
);

-- Extension para UUID si no existe
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
