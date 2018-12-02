FROM java:8
COPY Zadanie1.java ./
RUN javac Zadanie1.java
CMD ["java", "-cp", "mysql-connector-java-5.1.6-bin.jar:.", "Zadanie1"]
