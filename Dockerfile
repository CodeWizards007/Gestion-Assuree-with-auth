FROM openjdk:11
EXPOSE 8074
ADD target/GestionAssuree.jar GestionAssuree.jar
ENTRYPOINT ["java","-jar","/GestionAssuree.jar"]
