# Use the official MariaDB base image
FROM mariadb:10.4

# Optionally, set environment variables if needed
ENV MYSQL_ROOT_PASSWORD=root
ENV MYSQL_DATABASE=dbtocsv

RUN mkdir -p /var/lib/mysql_data
ADD schema-mysql.sql /

VOLUME /var/lib/mysql_data