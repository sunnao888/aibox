FROM eclipse-temurin:21-jre

# 创建目录，并使用它作为工作目录
RUN mkdir -p /aibox-server
WORKDIR /aibox-server

# 将后端项目的 Jar 文件，复制到镜像中
COPY ./jar/aibox-server.jar app.jar

# 设置 TZ 时区
ENV TZ=Asia/Shanghai
# 设置 JAVA_OPTS 环境变量，可通过 docker run -e "JAVA_OPTS=" 进行覆盖
ENV JAVA_OPTS="-Xms1024m -Xmx1024m -Djava.security.egd=file:/dev/./urandom"

# 应用参数
ENV ARGS=""

# 暴露后端项目的 48080 端口
EXPOSE 48080

# 启动后端项目
CMD java ${JAVA_OPTS} -jar app.jar $ARGS