CREATE TABLE refresh_tokens (
                                id SERIAL PRIMARY KEY,

                                refresh_token VARCHAR(255) NOT NULL UNIQUE,

                                expire_date TIMESTAMP NOT NULL,

                                user_id BIGINT NOT NULL,

                                CONSTRAINT fk_refresh_tokens_user
                                    FOREIGN KEY (user_id)
                                        REFERENCES users(id)
                                        ON DELETE CASCADE
);

