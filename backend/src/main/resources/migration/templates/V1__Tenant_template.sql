-- ============================================
-- 1) Preparación de schema
-- ============================================
-- Hay que recordar que Flyway se ejecuta y despues realiza la migracion,
-- solo necesario si se ejecuta manual
-- ============================================
-- 2) ROLES Y PERMISOS
-- ============================================
CREATE TABLE roles (
                       id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                       name VARCHAR(100) NOT NULL UNIQUE,
                       display_name VARCHAR(100) NOT NULL,
                       description TEXT,
                       level INTEGER NOT NULL,
                       created_at TIMESTAMP DEFAULT NOW(),
                       updated_at TIMESTAMP DEFAULT NOW()
    -- SoftDeletableEntity NO se aplica aquí (no usamos deleted_at/is_active)
);

CREATE TABLE permissions (
                             id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                             name VARCHAR(100) NOT NULL UNIQUE,
                             resource VARCHAR(100) NOT NULL,
                             action VARCHAR(50) NOT NULL,
                             description TEXT,
                             created_at TIMESTAMP DEFAULT NOW(),
                             updated_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE role_permissions (
                                  role_id UUID NOT NULL REFERENCES roles(id) ON DELETE CASCADE,
                                  permission_id UUID NOT NULL REFERENCES permissions(id) ON DELETE CASCADE,
                                  PRIMARY KEY (role_id, permission_id)
);

-- ============================================
-- 3) SUCURSALES (SoftDeletableEntity)
-- ============================================
CREATE TABLE branches (
                          id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                          name VARCHAR(255) NOT NULL,
                          address TEXT,
                          city VARCHAR(100),
                          state VARCHAR(100),
                          postal_code VARCHAR(20),
                          phone VARCHAR(20),
                          email VARCHAR(255),
                          opening_hours JSONB,
    -- SoftDeletableEntity
                          is_active BOOLEAN DEFAULT TRUE,
                          created_at TIMESTAMP DEFAULT NOW(),
                          updated_at TIMESTAMP DEFAULT NOW(),
                          deleted_at TIMESTAMP
);

-- ============================================
-- 4) USUARIOS (SoftDeletableEntity)
-- ============================================
CREATE TABLE users (
                       id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                       email VARCHAR(255) NOT NULL UNIQUE,
                       phone VARCHAR(20),
                       password_hash VARCHAR(255) NOT NULL,
                       full_name VARCHAR(255) NOT NULL,
                       date_of_birth DATE,
                       gender VARCHAR(20),
                       profile_image_url TEXT,
                       emergency_contact_name VARCHAR(255),
                       emergency_contact_phone VARCHAR(20),
                       role_id UUID NOT NULL REFERENCES roles(id),
                       branch_id UUID REFERENCES branches(id),
                       email_verified BOOLEAN DEFAULT FALSE,
                       created_by UUID REFERENCES users(id),
                       fcm_token TEXT,
                       language_preference VARCHAR(10) DEFAULT 'es',
    -- SoftDeletableEntity
                       is_active BOOLEAN DEFAULT TRUE,
                       created_at TIMESTAMP DEFAULT NOW(),
                       updated_at TIMESTAMP DEFAULT NOW(),
                       deleted_at TIMESTAMP
);

CREATE INDEX idx_users_email   ON users(email);
CREATE INDEX idx_users_role    ON users(role_id);
CREATE INDEX idx_users_branch  ON users(branch_id);
CREATE INDEX idx_users_deleted ON users(deleted_at);

-- ============================================
-- 5) PLANES Y MEMBRESÍAS
-- ============================================
CREATE TABLE membership_plans (
                                  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                  name VARCHAR(255) NOT NULL,
                                  description TEXT,
                                  duration_days INTEGER NOT NULL,
                                  price DECIMAL(10,2) NOT NULL,
                                  currency VARCHAR(3) DEFAULT 'USD',
                                  benefits JSONB,
                                  is_active BOOLEAN DEFAULT TRUE,
                                  created_at TIMESTAMP DEFAULT NOW(),
                                  updated_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE memberships (
                             id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                             user_id UUID NOT NULL REFERENCES users(id),
                             plan_id UUID NOT NULL REFERENCES membership_plans(id),
                             branch_id UUID REFERENCES branches(id),
                             start_date DATE NOT NULL,
                             end_date DATE NOT NULL,
                             status VARCHAR(20) DEFAULT 'active',
                             auto_renew BOOLEAN DEFAULT FALSE,
                             created_at TIMESTAMP DEFAULT NOW(),
                             updated_at TIMESTAMP DEFAULT NOW()
);

CREATE INDEX idx_memberships_user   ON memberships(user_id);
CREATE INDEX idx_memberships_status ON memberships(status);
CREATE INDEX idx_memberships_dates  ON memberships(end_date, status);

-- ============================================
-- 6) PAGOS
-- ============================================
CREATE TABLE payments (
                          id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                          membership_id UUID REFERENCES memberships(id),
                          user_id UUID NOT NULL REFERENCES users(id),
                          amount DECIMAL(10,2) NOT NULL,
                          currency VARCHAR(3) DEFAULT 'USD',
                          payment_method VARCHAR(50),
                          payment_date DATE NOT NULL,
                          status VARCHAR(20) DEFAULT 'completed',
                          notes TEXT,
                          processed_by UUID REFERENCES users(id),
                          created_at TIMESTAMP DEFAULT NOW(),
                          updated_at TIMESTAMP DEFAULT NOW()
);

CREATE INDEX idx_payments_user ON payments(user_id);
CREATE INDEX idx_payments_date ON payments(payment_date DESC);

-- ============================================
-- 7) ASISTENCIA
-- ============================================
CREATE TABLE attendance (
                            id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                            user_id UUID NOT NULL REFERENCES users(id),
                            branch_id UUID REFERENCES branches(id),
                            check_in TIMESTAMP NOT NULL DEFAULT NOW(),
                            check_out TIMESTAMP,
                            check_in_method VARCHAR(20),
                            check_out_method VARCHAR(20),
                            notes TEXT,
                            created_at TIMESTAMP DEFAULT NOW(),
                            updated_at TIMESTAMP DEFAULT NOW()
);

CREATE INDEX idx_attendance_user   ON attendance(user_id);
CREATE INDEX idx_attendance_date   ON attendance(check_in DESC);
CREATE INDEX idx_attendance_branch ON attendance(branch_id);

CREATE TABLE staff_attendance (
                                  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                  staff_id UUID NOT NULL REFERENCES users(id),
                                  branch_id UUID REFERENCES branches(id),
                                  shift_start TIMESTAMP NOT NULL,
                                  shift_end TIMESTAMP,
                                  check_in TIMESTAMP,
                                  check_out TIMESTAMP,
                                  check_in_method VARCHAR(20),
                                  status VARCHAR(20) DEFAULT 'scheduled',
                                  notes TEXT,
                                  created_at TIMESTAMP DEFAULT NOW(),
                                  updated_at TIMESTAMP DEFAULT NOW()
);

CREATE INDEX idx_staff_attendance_staff ON staff_attendance(staff_id);
CREATE INDEX idx_staff_attendance_date  ON staff_attendance(shift_start DESC);

-- ============================================
-- 8) RUTINAS Y EJERCICIOS
-- ============================================
CREATE TABLE routines (
                          id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                          name VARCHAR(255) NOT NULL,
                          description TEXT,
                          difficulty_level VARCHAR(20),
                          goal VARCHAR(100),
                          duration_weeks INTEGER,
                          created_by UUID REFERENCES users(id),
                          is_template BOOLEAN DEFAULT FALSE,
                          is_active BOOLEAN DEFAULT TRUE,
                          created_at TIMESTAMP DEFAULT NOW(),
                          updated_at TIMESTAMP DEFAULT NOW(),
                          deleted_at TIMESTAMP
);

CREATE TABLE exercises (
                           id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                           name VARCHAR(255) NOT NULL,
                           description TEXT,
                           muscle_group VARCHAR(100),
                           equipment_needed VARCHAR(255),
                           video_url TEXT,
                           thumbnail_url TEXT,
                           demonstration_images JSONB,
                           instructions TEXT,
                           tips TEXT,
                           created_at TIMESTAMP DEFAULT NOW(),
                           updated_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE routine_exercises (
                                   id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                   routine_id UUID NOT NULL REFERENCES routines(id) ON DELETE CASCADE,
                                   exercise_id UUID NOT NULL REFERENCES exercises(id),
                                   day_of_week INTEGER,
                                   order_index INTEGER,
                                   sets INTEGER,
                                   reps VARCHAR(50),
                                   rest_seconds INTEGER,
                                   notes TEXT,
                                   created_at TIMESTAMP DEFAULT NOW(),
                                   updated_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE user_routines (
                               id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                               user_id UUID NOT NULL REFERENCES users(id),
                               routine_id UUID NOT NULL REFERENCES routines(id),
                               assigned_by UUID REFERENCES users(id),
                               start_date DATE NOT NULL,
                               end_date DATE,
                               status VARCHAR(20) DEFAULT 'active',
                               progress_notes TEXT,
                               created_at TIMESTAMP DEFAULT NOW(),
                               updated_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE routine_progress (
                                  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                  user_routine_id UUID NOT NULL REFERENCES user_routines(id),
                                  exercise_id UUID NOT NULL REFERENCES exercises(id),
                                  completed_date DATE NOT NULL,
                                  sets_completed INTEGER,
                                  reps_completed JSONB,
                                  weight_used DECIMAL(6,2),
                                  notes TEXT,
                                  created_at TIMESTAMP DEFAULT NOW(),
                                  updated_at TIMESTAMP DEFAULT NOW()
);

-- ============================================
-- 9) INVENTARIO
-- ============================================
CREATE TABLE inventory_categories (
                                      id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                      name VARCHAR(100) NOT NULL,
                                      description TEXT,
                                      parent_category_id UUID REFERENCES inventory_categories(id),
                                      created_at TIMESTAMP DEFAULT NOW(),
                                      updated_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE inventory_items (
                                 id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                 category_id UUID REFERENCES inventory_categories(id),
                                 branch_id UUID REFERENCES branches(id),
                                 name VARCHAR(255) NOT NULL,
                                 description TEXT,
                                 item_type VARCHAR(50),
                                 sku VARCHAR(100) UNIQUE,
                                 quantity INTEGER DEFAULT 0,
                                 min_stock_level INTEGER,
                                 unit_price DECIMAL(10,2),
                                 supplier VARCHAR(255),
                                 purchase_date DATE,
                                 warranty_expiry DATE,
                                 condition VARCHAR(50),
                                 location VARCHAR(255),
                                 image_url TEXT,
    -- SoftDeletableEntity
                                 is_sellable BOOLEAN DEFAULT FALSE,
                                 is_active BOOLEAN DEFAULT TRUE,
                                 created_at TIMESTAMP DEFAULT NOW(),
                                 updated_at TIMESTAMP DEFAULT NOW(),
                                 deleted_at TIMESTAMP
);

CREATE TABLE inventory_movements (
                                     id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                     item_id UUID NOT NULL REFERENCES inventory_items(id),
                                     movement_type VARCHAR(20),
                                     quantity INTEGER NOT NULL,
                                     previous_quantity INTEGER,
                                     new_quantity INTEGER,
                                     unit_price DECIMAL(10,2),
                                     total_amount DECIMAL(10,2),
                                     from_branch_id UUID REFERENCES branches(id),
                                     to_branch_id UUID REFERENCES branches(id),
                                     notes TEXT,
                                     processed_by UUID REFERENCES users(id),
                                     created_at TIMESTAMP DEFAULT NOW(),
                                     updated_at TIMESTAMP DEFAULT NOW()
);

-- ============================================
-- 10) VENTAS
-- ============================================
CREATE TABLE sales (
                       id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                       branch_id UUID REFERENCES branches(id),
                       customer_id UUID REFERENCES users(id),
                       total_amount DECIMAL(10,2) NOT NULL,
                       payment_method VARCHAR(50),
                       status VARCHAR(20) DEFAULT 'completed',
                       sold_by UUID REFERENCES users(id),
                       notes TEXT,
                       created_at TIMESTAMP DEFAULT NOW(),
                       updated_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE sale_items (
                            id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                            sale_id UUID NOT NULL REFERENCES sales(id) ON DELETE CASCADE,
                            item_id UUID NOT NULL REFERENCES inventory_items(id),
                            quantity INTEGER NOT NULL,
                            unit_price DECIMAL(10,2) NOT NULL,
                            subtotal DECIMAL(10,2) NOT NULL,
                            created_at TIMESTAMP DEFAULT NOW(),
                            updated_at TIMESTAMP DEFAULT NOW()
);

-- ============================================
-- 11) EVENTOS Y PARTICIPANTES
-- ============================================
CREATE TABLE events (
                        id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                        branch_id UUID REFERENCES branches(id),
                        title VARCHAR(255) NOT NULL,
                        description TEXT,
                        event_type VARCHAR(50),
                        event_date DATE NOT NULL,
                        start_time TIME,
                        end_time TIME,
                        location VARCHAR(255),
                        max_participants INTEGER,
                        registered_count INTEGER DEFAULT 0,
                        created_by UUID REFERENCES users(id),
                        is_active BOOLEAN DEFAULT TRUE,
                        created_at TIMESTAMP DEFAULT NOW(),
                        updated_at TIMESTAMP DEFAULT NOW(),
                        deleted_at TIMESTAMP
);

CREATE TABLE event_participants (
                                    event_id UUID REFERENCES events(id) ON DELETE CASCADE,
                                    user_id UUID REFERENCES users(id),
                                    registration_date TIMESTAMP DEFAULT NOW(),
                                    status VARCHAR(20) DEFAULT 'registered',
                                    PRIMARY KEY (event_id, user_id)
);

-- ============================================
-- 12) NOTIFICACIONES
-- ============================================
CREATE TABLE notifications (
                               id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                               user_id UUID REFERENCES users(id),
                               title VARCHAR(255) NOT NULL,
                               body TEXT NOT NULL,
                               notification_type VARCHAR(50),
                               data JSONB,
                               is_read BOOLEAN DEFAULT FALSE,
                               sent_at TIMESTAMP DEFAULT NOW(),
                               read_at TIMESTAMP,
                               created_at TIMESTAMP DEFAULT NOW(),
                               updated_at TIMESTAMP DEFAULT NOW()
);

-- ============================================
-- 13) ARCHIVOS MULTIMEDIA
-- ============================================
CREATE TABLE media_files (
                             id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                             filename VARCHAR(255) NOT NULL,
                             filepath TEXT NOT NULL,
                             file_type VARCHAR(50),
                             mime_type VARCHAR(100),
                             file_size BIGINT,
                             related_entity VARCHAR(50),
                             related_entity_id UUID,
                             uploaded_by UUID REFERENCES users(id),
                             created_at TIMESTAMP DEFAULT NOW(),
                             updated_at TIMESTAMP DEFAULT NOW()
);
