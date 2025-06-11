INSERT INTO students (name, kana_name, nickname, email, area, age, sex, remark, isDeleted)
VALUES
('佐藤 太郎', 'サトウ タロウ', 'たろちゃん', 'taro.sato@example.com', '東京', 20, '男性', '', FALSE),
('鈴木 花子', 'スズキ ハナコ', 'はなちゃん', 'hanako.suzuki@example.com', '大阪', 22, '女性', '', FALSE),
('高橋 健', 'タカハシ ケン', 'けんちゃん', 'ken.takahashi@example.com', '名古屋', 21, '男性', '', FALSE),
('田中 美咲', 'タナカ ミサキ', 'みさきちゃん', 'misaki.tanaka@example.com', '福岡', 23, '女性', '', FALSE),
('山本 大輔', 'ヤマモト ダイスケ', 'だいちゃん', 'daisuke.yamamoto@example.com', '札幌', 19, '男性', '', FALSE);

INSERT INTO students_courses (student_id, course_name, course_start_at, course_end_at)
VALUES
(1, 'データベース基礎', '2025-06-01 09:00:00', '2025-06-30 17:00:00'),
(2, 'Webプログラミング', '2025-06-01 10:00:00', '2025-06-30 16:00:00'),
(3, 'ネットワーク入門', '2025-06-02 13:00:00', '2025-06-29 15:00:00'),
(4, 'AIと機械学習', '2025-06-03 08:30:00', '2025-06-28 14:30:00'),
(5, 'クラウドコンピューティング', '2025-06-04 11:00:00', '2025-06-27 12:00:00');