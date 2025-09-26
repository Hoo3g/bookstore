-- src/main/resources/db/migration/V3__Add_book_ratings.sql
-- Thêm bảng đánh giá sách
CREATE TABLE book_ratings (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    book_id BIGINT NOT NULL REFERENCES books(id) ON DELETE CASCADE,
    rating INTEGER NOT NULL CHECK (rating >= 1 AND rating <= 5),
    review TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(user_id, book_id)
);

-- Thêm cột average_rating cho books
ALTER TABLE books ADD COLUMN average_rating DECIMAL(3,2) DEFAULT 0;
ALTER TABLE books ADD COLUMN total_ratings INTEGER DEFAULT 0;

-- Tạo index
CREATE INDEX idx_book_ratings_book ON book_ratings(book_id);
CREATE INDEX idx_book_ratings_user ON book_ratings(user_id);
CREATE INDEX idx_books_rating ON books(average_rating);
