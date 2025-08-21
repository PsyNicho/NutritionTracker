-- Create database (run this first if DB doesn't exist)
CREATE DATABASE IF NOT EXISTS nutritiondb;
USE nutritiondb;

-- Users
CREATE TABLE IF NOT EXISTS users (
  id INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(100) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  role VARCHAR(20) NOT NULL DEFAULT 'user',
  goal_calories DOUBLE NOT NULL DEFAULT 0,
  goal_protein DOUBLE NOT NULL DEFAULT 0,
  goal_carbs DOUBLE NOT NULL DEFAULT 0,
  goal_fats DOUBLE NOT NULL DEFAULT 0,
  goal_fiber DOUBLE NOT NULL DEFAULT 0
);

-- Foods (per 100g nutrition values) - now owned per user
CREATE TABLE IF NOT EXISTS foods (
  id INT AUTO_INCREMENT PRIMARY KEY,
  user_id INT NOT NULL,
  name VARCHAR(150) NOT NULL,
  calories_per_100g DOUBLE NOT NULL DEFAULT 0,
  protein_per_100g DOUBLE NOT NULL DEFAULT 0,
  carbs_per_100g DOUBLE NOT NULL DEFAULT 0,
  fats_per_100g DOUBLE NOT NULL DEFAULT 0,
  fiber_per_100g DOUBLE NOT NULL DEFAULT 0,
  CONSTRAINT fk_food_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
  UNIQUE KEY uniq_user_foodname (user_id, name)
);


-- Food logs (what user ate on which day and grams)
CREATE TABLE IF NOT EXISTS food_log (
  id INT AUTO_INCREMENT PRIMARY KEY,
  user_id INT NOT NULL,
  food_id INT NOT NULL,
  grams_consumed DOUBLE NOT NULL,
  date DATE NOT NULL,
  CONSTRAINT fk_log_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
  CONSTRAINT fk_log_food FOREIGN KEY (food_id) REFERENCES foods(id) ON DELETE CASCADE,
  INDEX idx_log_user_date (user_id, date)
);

-- Notes from admin/trainer to user
CREATE TABLE IF NOT EXISTS notes (
  id INT AUTO_INCREMENT PRIMARY KEY,
  user_id INT NOT NULL,
  admin_id INT NOT NULL,
  note_text TEXT NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_note_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
