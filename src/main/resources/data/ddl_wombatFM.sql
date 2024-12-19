--DROP TABLE--
DROP TABLE IF EXISTS setlists CASCADE;
DROP TABLE IF EXISTS shows CASCADE;
DROP TABLE IF EXISTS artists CASCADE;
DROP TABLE IF EXISTS gallery CASCADE;
DROP TABLE IF EXISTS songs CASCADE;
DROP TABLE IF EXISTS review CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS setlist_version CASCADE;
DROP TYPE ROLES;

--CREATE TABLE--
CREATE TYPE ROLES AS ENUM ('admin', 'member');
CREATE TABLE users (
	user_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	username VARCHAR(255) NOT NULL,
	password VARCHAR(255) NOT NULL,
	role ROLES NOT NULL DEFAULT 'member'
);

CREATE TABLE shows (
	show_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	title VARCHAR(255) NOT NULL,
	venue VARCHAR(255) NOT NULL,
	show_date DATE NOT NULL,
	start_time TIME NOT NULL,
	duration INT NOT NULL --show duration in minutes
);

CREATE TABLE artists (
	artist_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	artist_photo_url VARCHAR(255),
	name VARCHAR(255) NOT NULL
);

CREATE TABLE songs (
	song_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	title VARCHAR(255) NOT NULL
);

CREATE TABLE gallery (
	gallery_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	show_id INT REFERENCES shows(show_id) NOT NULL,
	url VARCHAR(255) NOT NULL
);

CREATE TABLE setlist_version (
	version_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	show_id INT REFERENCES shows(show_id) NOT NULL,
	acc_status BOOLEAN NOT NULL,
	created_by INT REFERENCES users(user_id) NOT NULL
);

CREATE TABLE setlists (
	version_id INT REFERENCES setlist_version(version_id) NOT NULL,
	artist_id INT REFERENCES artists(artist_id) NOT NULL,
	song_id INT REFERENCES songs(song_id) NOT NULL,
	PRIMARY KEY(version_id, artist_id, song_id)
);

CREATE TABLE review (
	review_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	commment TEXT NOT NULL
);

--INSERT--
INSERT INTO users (username, password, role) VALUES
('jamesw', 'jamespwd', 'admin'),
('johndoe', 'johnpwd', 'member'),
('janesmith', 'janepwd', 'member');

INSERT INTO shows (title, venue, show_date, start_time, duration) VALUES
('Rock Night', 'Madison Square Garden', '2024-12-25', '19:00:00', 120),
('Jazz Evening', 'Blue Note Club', '2024-12-26', '20:00:00', 90),
('Pop Concert', 'Staples Center', '2024-12-27', '18:30:00', 150);

INSERT INTO artists (artist_photo_url, name) VALUES
('https://example.com/photos/artist1.jpg', 'The Rockers'),
('https://example.com/photos/artist2.jpg', 'Jazz Ensemble'),
('https://example.com/photos/artist3.jpg', 'Pop Stars');

INSERT INTO songs (title) VALUES
('Song of Freedom'),
('Smooth Jazz Night'),
('Pop Anthem'),
('Rock Ballad'),
('Melodic Jazz');

INSERT INTO gallery (show_id, url) VALUES
(1, 'https://example.com/gallery/rock-night-1.jpg'),
(1, 'https://example.com/gallery/rock-night-2.jpg'),
(2, 'https://example.com/gallery/jazz-evening.jpg'),
(3, 'https://example.com/gallery/pop-concert.jpg');

INSERT INTO setlist_version (show_id, acc_status, created_by) VALUES
(1, TRUE, 1),
(2, FALSE, 2),
(3, TRUE, 2);

INSERT INTO setlists (version_id, artist_id, song_id) VALUES
(1, 1, 1),
(1, 1, 4),
(2, 2, 2),
(2, 2, 5),
(3, 3, 3);

INSERT INTO review (commment) VALUES
('GILA KEREN BAT!'),
('Seru bangettttt!!! Lagunya enak-enak!!'),
('10/10, KEREN BANGET!!!');

--SELECT--
SELECT * FROM users;
SELECT * FROM shows;
SELECT * FROM artists;
SELECT * FROM songs;
SELECT * FROM gallery;
SELECT * FROM setlist_version;
SELECT * FROM setlists;
SELECT * FROM review;