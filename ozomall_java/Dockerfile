# 拉取基础镜像
FROM java:8
MAINTAINER dubo
ADD app.jar /usr/local/application/app.jar
EXPOSE 8090
ENTRYPOINT ["java","-jar","app.jar"]