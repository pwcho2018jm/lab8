FROM java:8
COPY mysql-connector-java-8.0.13.jar ./
COPY Zadanie1.java ./
RUN javac Zadanie1.java
CMD ["java", "-cp", "mysql-connector-java-8.0.13.jar:.", "Zadanie1"]
