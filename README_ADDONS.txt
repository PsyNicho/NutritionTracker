
Added features:
- Dashboard menus for User and Admin (grid of big buttons).
- 'Food Eaten Today' table with per-item nutrients and totals.
- 'Food History' with date picker, per-item nutrients and totals.
- Admin: All Users list with 'View' button -> see a user's logs by date + add notes.
- Admin: Manage Accounts frame to create users, change passwords, and delete users.
- DAO: Added getDetailedLogsByUserAndDate; UserDAO now supports updatePassword & deleteUser.
- DTO: FoodLogDetail for joined/derived nutrient values per logged item.
How to use:
- Run SQL: nutritiondb.sql (already includes needed tables).
- Start app, login. Admin sees Admin Dashboard; normal users see User Dashboard.
