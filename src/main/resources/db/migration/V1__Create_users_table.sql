-- Migration: Create users table
-- Description: Initial schema for OAuth users
-- Author: System
-- Date: 2025-01-XX

CREATE TABLE IF NOT EXISTS `users` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `uuid` VARCHAR(255) NOT NULL COMMENT 'OAuth provider user ID',
    `provider` VARCHAR(50) NOT NULL COMMENT 'OAuth provider (OAUTH_GOOGLE, OAUTH_TWITTER, etc.)',
    `username` VARCHAR(255) NOT NULL,
    `nickname` VARCHAR(255) DEFAULT NULL,
    `avatar` VARCHAR(500) DEFAULT NULL,
    `email` VARCHAR(255) DEFAULT NULL,
    `created_at` DATETIME NOT NULL,
    `updated_at` DATETIME NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_uuid_provider` (`uuid`, `provider`),
    KEY `idx_provider` (`provider`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

