To setup the MySQL database, create the database and user for the default
database setup.  (Don't you just love the out-of-the-box names for the
database, username, and password. :)

mysql -u root
mysql> CREATE DATABASE db;
mysql> CREATE USER 'username'@'localhost' IDENTIFIED BY 'password';
mysql> CREATE USER 'username'@'%' IDENTIFIED BY 'password';
mysql> GRANT ALL PRIVILEGES ON db.* TO 'username'@'localhost' WITH GRANT OPTION;
mysql> GRANT ALL PRIVILEGES ON db.* TO 'username'@'%' WITH GRANT OPTION;
mysql> exit
