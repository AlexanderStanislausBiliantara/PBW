DROP TABLE IF EXISTS setlist_items CASCADE;
DROP TABLE IF EXISTS setlist_version CASCADE;
DROP TABLE IF EXISTS reviews CASCADE;
DROP TABLE IF EXISTS setlists CASCADE;
DROP TABLE IF EXISTS gallery CASCADE;
DROP TABLE IF EXISTS songs CASCADE;
DROP TABLE IF EXISTS artists CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS shows CASCADE;

CREATE TABLE shows (
    show_id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    venue VARCHAR(255) NOT NULL,
    show_date DATE NOT NULL,
    start_time TIME NOT NULL,
    duration INT NOT NULL
);

CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL,
    is_active BOOLEAN DEFAULT TRUE
);

CREATE TABLE artists (
    artist_id SERIAL PRIMARY KEY,
    artist_photo_url VARCHAR(255),
    name VARCHAR(255) NOT NULL
);

CREATE TABLE songs (
    song_id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    artist_id INT NOT NULL,
    FOREIGN KEY (artist_id) REFERENCES artists(artist_id)
);

CREATE TABLE gallery (
    gallery_id SERIAL PRIMARY KEY,
    show_id INT NOT NULL,
    url VARCHAR(255) NOT NULL,
    FOREIGN KEY (show_id) REFERENCES shows(show_id)
);

CREATE TABLE setlists (
    setlist_id SERIAL PRIMARY KEY,
    show_id INT NOT NULL,
    artist_id INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (show_id) REFERENCES shows(show_id),
    FOREIGN KEY (artist_id) REFERENCES artists(artist_id)
);

CREATE TABLE reviews (
    review_id SERIAL PRIMARY KEY,
    comment TEXT NOT NULL,
    user_id INT NOT NULL,
    setlist_id INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (setlist_id) REFERENCES setlists(setlist_id)
);

CREATE TABLE setlist_version (
    version_id SERIAL PRIMARY KEY,
    setlist_id INT NOT NULL,
    review_id INT NOT NULL,
    FOREIGN KEY (setlist_id) REFERENCES setlists(setlist_id),
    FOREIGN KEY (review_id) REFERENCES reviews(review_id)
);

CREATE TABLE setlist_items (
    version_id INT NOT NULL,
    song_id INT NOT NULL,
    song_order INT NOT NULL,
    PRIMARY KEY (version_id, song_id),
    FOREIGN KEY (version_id) REFERENCES setlist_version(version_id),
    FOREIGN KEY (song_id) REFERENCES songs(song_id)
);
