insert into artists (name, country, birth_year) values
    ('Claude Monet',      'France',      1840),
    ('Vincent van Gogh',  'Netherlands', 1853),
    ('Pablo Picasso',     'Spain',       1881),
    ('Frida Kahlo',       'Mexico',      1907),
    ('Salvador Dali',     'Spain',       1904),
    ('Paul Cezanne',      'France',      1839);

insert into artworks (title, created_year, price, style, artist_id) values
    ('Impression, Sunrise',                 1872, 1200000, 'Impressionism',      (select id from artists where name = 'Claude Monet')),
    ('Water Lilies',                        1916, 1500000, 'Impressionism',      (select id from artists where name = 'Claude Monet')),
    ('Haystacks',                           1890, 1100000, 'Impressionism',      (select id from artists where name = 'Claude Monet')),
    ('The Starry Night',                    1889, 2000000, 'Post-Impressionism', (select id from artists where name = 'Vincent van Gogh')),
    ('Sunflowers',                          1888, 1800000, 'Post-Impressionism', (select id from artists where name = 'Vincent van Gogh')),
    ('The Bedroom',                         1888,  900000, 'Post-Impressionism', (select id from artists where name = 'Vincent van Gogh')),
    ('Guernica',                            1937, 2500000, 'Cubism',             (select id from artists where name = 'Pablo Picasso')),
    ('Les Demoiselles d''Avignon',          1907, 2200000, 'Cubism',             (select id from artists where name = 'Pablo Picasso')),
    ('The Weeping Woman',                   1937, 1300000, 'Cubism',             (select id from artists where name = 'Pablo Picasso')),
    ('The Two Fridas',                      1939, 1400000, 'Surrealism',         (select id from artists where name = 'Frida Kahlo')),
    ('Self-Portrait with Thorn Necklace',   1940, 1250000, 'Surrealism',         (select id from artists where name = 'Frida Kahlo')),
    ('The Broken Column',                   1944, 1150000, 'Surrealism',         (select id from artists where name = 'Frida Kahlo')),
    ('The Persistence of Memory',           1931, 2100000, 'Surrealism',         (select id from artists where name = 'Salvador Dali')),
    ('Swans Reflecting Elephants',          1937,  950000, 'Surrealism',         (select id from artists where name = 'Salvador Dali')),
    ('The Elephants',                       1948,  870000, 'Surrealism',         (select id from artists where name = 'Salvador Dali')),
    ('The Card Players',                    1894, 1700000, 'Post-Impressionism', (select id from artists where name = 'Paul Cezanne')),
    ('Mont Sainte-Victoire',                1904, 1000000, 'Post-Impressionism', (select id from artists where name = 'Paul Cezanne')),
    ('The Basket of Apples',                1895,  800000, 'Post-Impressionism', (select id from artists where name = 'Paul Cezanne'));

insert into exhibitions (title, start_date, end_date) values
    ('Impressionism and Beyond', '2026-09-01', '2026-12-31'),
    ('Spanish Masters',          '2026-10-01', '2027-01-31'),
    ('Self-Portraits',           '2026-06-01', '2026-09-15');

insert into exhibition_artworks (exhibition_id, artwork_id)
select e.id, a.id from exhibitions e, artworks a
where e.title = 'Impressionism and Beyond'
  and a.title in ('Impression, Sunrise', 'Water Lilies', 'Haystacks',
                  'The Starry Night', 'Sunflowers', 'The Card Players');

insert into exhibition_artworks (exhibition_id, artwork_id)
select e.id, a.id from exhibitions e, artworks a
where e.title = 'Spanish Masters'
  and a.title in ('Guernica', 'Les Demoiselles d''Avignon', 'The Weeping Woman',
                  'The Persistence of Memory', 'Swans Reflecting Elephants');

insert into exhibition_artworks (exhibition_id, artwork_id)
select e.id, a.id from exhibitions e, artworks a
where e.title = 'Self-Portraits'
  and a.title in ('The Two Fridas', 'Self-Portrait with Thorn Necklace', 'The Broken Column');