#定义一个容器，定义基础镜像，标识运行环境
FROM jdk:1.8

#定义临时变量，ENV表示容器变量
ARG workdir=/study
#定义一个工作目录
WORKDIR ${workdir}

ENV LANG C.UTF-8

#将需要jar添加到容器并重命名。只能添加当前目录上下文的问题，指定绝对路不支持径
ADD /target/study-0.0.1-SNAPSHOT-exec.jar study_app.jar
#声明容器对外端口，可以指定多个端口，不过不会自动对外映射
EXPOSE 8081
#执行启动命令 第一个为命令，后面为参数
ENTRYPOINT ["java","-jar","study_app.jar"]