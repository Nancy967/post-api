CREATE TABLE IF NOT EXISTS post
(
    post_id            INT           NOT NULL PRIMARY KEY AUTO_INCREMENT,
    title              VARCHAR(128)  NOT NULL,
    image_url          VARCHAR(256),
    description        VARCHAR(1024) NOT NULL,
    created_date       TIMESTAMP     NOT NULL,
    last_modified_date TIMESTAMP     NOT NULL
    );
