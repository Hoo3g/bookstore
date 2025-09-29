
-- Thêm dữ liệu mẫu cho categories
INSERT INTO categories (name, description) VALUES
('Khoa học', 'Sách về khoa học và công nghệ'),
('Văn học', 'Sách văn học trong và ngoài nước'),
('Lịch sử', 'Sách về lịch sử và địa lý'),
('Kinh tế', 'Sách về kinh tế và quản lý'),
('Triết học', 'Sách về triết học và tư tưởng'),
('Nghệ thuật', 'Sách về nghệ thuật và văn hóa'),
('Thể thao', 'Sách về thể thao và sức khỏe'),
('Nấu ăn', 'Sách về ẩm thực và nấu ăn');

-- Thêm dữ liệu mẫu cho books
INSERT INTO books (title, author, isbn, category_id, total_copies, available_copies, description, publication_year) VALUES
('Java Programming Masterclass', 'John Smith', '978-0123456789', 1, 5, 5, 'Cuốn sách học lập trình Java từ cơ bản đến nâng cao', 2023),
('Truyện Kiều', 'Nguyễn Du', '978-0987654321', 2, 3, 3, 'Tác phẩm văn học kinh điển của Việt Nam', 1820),
('Lịch sử Việt Nam', 'Trần Trọng Kim', '978-0111222333', 3, 4, 4, 'Tổng quan lịch sử dân tộc Việt Nam', 1920),
('Kinh tế học đại cương', 'Nguyễn Văn A', '978-0444555666', 4, 6, 6, 'Giáo trình kinh tế học cơ bản', 2022),
('Triết học phương Đông', 'Lê Thị B', '978-0777888999', 5, 2, 2, 'Tìm hiểu triết học các nước phương Đông', 2021),
('Spring Boot in Action', 'Craig Walls', '978-1617292545', 1, 4, 4, 'Hướng dẫn xây dựng ứng dụng với Spring Boot', 2024),
('React.js Handbook', 'Flavio Copes', '978-1234567890', 1, 3, 3, 'Cẩm nang React.js cho developer', 2024);
