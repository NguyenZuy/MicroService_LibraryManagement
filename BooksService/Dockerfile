FROM openjdk:17

EXPOSE 8095

ADD out/BooksService.jar BooksService.jar

ENTRYPOINT ["java","-jar","/BooksService.jar"]