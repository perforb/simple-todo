FROM mysql:8.0.13

ADD ./conf/custom.cnf /etc/mysql/conf.d
ADD ./sql /docker-entrypoint-initdb.d

# will not be combined if this file's permission is 777.
RUN chmod 644 /etc/mysql/conf.d/custom.cnf
