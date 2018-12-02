FROM java:8
COPY mysql-connector-java-5.1.6-bin.jar ./
COPY Zadanie1.java ./
EXPOSE 3306
RUN javac Zadanie1.java
CMD ["java", "-cp", "mysql-connector-java-5.1.6-bin.jar:.", "Zadanie1"]
