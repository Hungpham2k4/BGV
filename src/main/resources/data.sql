INSERT INTO users (id, name, username, email, password, role, active, created_at, updated_at)
VALUES
    (1, 'Admin', 'admin', 'admin@bgv.com', '$2a$12$HTVcdpBsXVu.ULiEP0s05...TzNL/tFlgnekzN/mkjZIwUjMN3jfu', 'ADMIN', true, NOW(), NOW()),
    (2, 'Mod', 'moderator', 'mod@bgv.com', '$2a$12$HTVcdpBsXVu.ULiEP0s05...TzNL/tFlgnekzN/mkjZIwUjMN3jfu', 'MODERATOR', true, NOW(), NOW()),
    (3, 'User', 'user1', 'user1@bgv.com', '$2a$12$HTVcdpBsXVu.ULiEP0s05...TzNL/tFlgnekzN/mkjZIwUjMN3jfu', 'USER', true, NOW(), NOW());

INSERT INTO genres (id, name) VALUES
                                  (1, 'Action'),
                                  (2, 'Drama'),
                                  (3, 'Comedy'),
                                  (4, 'Sci-Fi');

INSERT INTO movies (id, title, description, director, thumbnail_url, background_url, video_url, release_date, duration) VALUES
                                                                                                            (1, 'Interstellar', 'A team travels through a wormhole to find a new planet.', 'Christopher Nolan', 'https://ephoto360.com/uploads/worigin/2020/07/20/taoanhcheposterphimdinhmusuongonline5f156a56b32ca_da3b502225e6c1f091aa5fafb6be6ea4.jpg', 'https://static.nutscdn.com/vimg/1920-0/42563b75492c2722705efab50834b270.webp' ,'https://ia800600.us.archive.org/3/items/12a-12/12a12.mp4', '2014-11-07', 169),
                                                                                                            (2, 'The Godfather', 'The aging patriarch of an organized crime dynasty transfers control to his reluctant son.', 'Francis Ford Coppola', 'https://assets2.htv.com.vn/Images/TAP%20CHI%20HTV/XEM%20GI%20HOM%20NAY/THAO/2020/Thang%2011/28.11phamnhi/H3%20poster%20dinh%20mu%20suong-ok.jpg', 'https://static.nutscdn.com/vimg/1920-0/5b8cf6141e2949adfb1564ef172ef125.webp','https://drive.google.com/uc?export=download&id=1qkQB0AJCyJ_Jy7Ta6PGfnYLuzeDOTb3-', '1972-03-24', 175);
INSERT INTO movie_genres (movie_id, genre_id) VALUES
                                                  (1, 4),  -- Interstellar - Sci-Fi
                                                  (1, 2),  -- Interstellar - Drama
                                                  (2, 2);  -- The Godfather - Drama
INSERT INTO comments (id, content, created_at, user_id, movie_id) VALUES
                                                                      (1, 'One of the best sci-fi movies ever.', NOW(), 2, 1),
                                                                      (2, 'Classic movie with deep story.', NOW(), 3, 2);
INSERT INTO favorites (id, user_id, movie_id) VALUES
                                                  (1, 2, 1),
                                                  (2, 3, 2);
INSERT INTO ratings (id, score, review, user_id, movie_id) VALUES
                                                               (1, 5, 'Masterpiece!', 2, 1),
                                                               (2, 4, 'Brilliant film!', 3, 2);
INSERT INTO watch_history (id, watched_at, last_position, user_id, movie_id) VALUES
                                                                                 (1, NOW(), 720, 2, 1),
                                                                                 (2, NOW(), 1500, 3, 2);
