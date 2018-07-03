mysql -u root -e "drop database if exists bd2_grupo2;
                  create database bd2_grupo2;
                  CREATE USER IF NOT EXISTS bd2_grupo2 IDENTIFIED BY 'topsecret';
                  GRANT ALL PRIVILEGES ON bd2_grupo2.* TO 'bd2_grupo2'@'localhost';
                  FLUSH PRIVILEGES;
                  "
