FROM mysql:8.0.13

ADD ./conf/custom.cnf /etc/mysql/conf.d
ADD ./sql /docker-entrypoint-initdb.d

# must not be 777 because the file is not include
RUN chmod 644 /etc/mysql/conf.d/custom.cnf
