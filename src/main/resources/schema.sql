DROP
DATABASE IF EXISTS cards_db;
CREATE
DATABASE cards_db;
USE
cards_db;

CREATE TABLE users
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(20) NOT NULL,
    email    VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(60) NOT NULL,
    role     VARCHAR(20) NOT NULL,
    enabled BOOLEAN NOT NULL
);

CREATE TABLE authorities
(
    email      VARCHAR(50) NOT NULL,
    authority  VARCHAR(50) NOT NULL,
    CONSTRAINT fk_authorities_users FOREIGN KEY (email) REFERENCES users(email)
);


--password for everyone is 12345
INSERT INTO users (name, email, password, role, enabled)
VALUES ('admin1', 'admin1@gmail.com', '$2a$12$r6beHAxCbvq/KYrtgkpmG.xLbq2.p27maFbUyz1V.qEBN0lu2pxqq', 'ADMIN', true);
INSERT INTO users (name, email, password, role, enabled)
VALUES ('admin2','admin2@gmail.com', '$2a$12$kztZ.Ogu2PjGu6ccqtVXpO0uGBG83fM9UFg9zQZWP7wfc7sK6RnEi', 'ADMIN', true);
INSERT INTO users (name, email, password, role, enabled)
VALUES ('member1','member1@gmail.com', '$2a$12$2oKtJTjO3xqhfzH3E0nyQeDuZBxRIBB1e4tlHMyaDYnN.gdGeLSPW', 'MEMBER', true);
INSERT INTO users (name, email, password, role, enabled)
VALUES ('member2','member2@gmail.com', '$2a$12$PQzchFBYaNviZQpBDfl3KeTpv8i1B43OoR4cw9Rw7FNqAVEimq2.6', 'MEMBER', true);
INSERT INTO users (name, email, password, role, enabled)
VALUES ('member3','member3@gmail.com', '$2a$12$eyPnhpDIvq.6Ro6H8X4hsulTO9xJx1nz3GYPZC12KnUvGOVaGJmIO', 'MEMBER', true);

INSERT INTO authorities (email, authority)
VALUES ('admin1@gmail.com', 'ROLE_ADMIN');
INSERT INTO authorities (email, authority)
VALUES ('admin2@gmail.com', 'ROLE_ADMIN');
INSERT INTO authorities (email, authority)
VALUES ('member1@gmail.com', 'ROLE_MEMBER');
INSERT INTO authorities (email, authority)
VALUES ('member2@gmail.com', 'ROLE_MEMBER');
INSERT INTO authorities (email, authority)
VALUES ('member3@gmail.com', 'ROLE_MEMBER');


CREATE TABLE IF NOT EXISTS cards
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    name VARCHAR (255) NOT NULL,
    description TEXT,
    color VARCHAR (7),
    status ENUM('TO_DO','IN_PROGRESS','DONE') NOT NULL DEFAULT 'TO_DO',
    creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY(user_id) REFERENCES users(id)
);
