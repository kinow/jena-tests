FROM java:8-jdk
MAINTAINER Bruno P. Kinoshita

RUN apt-get update && apt-get install -y wget git curl zip maven && rm -rf /var/lib/apt/lists/*

RUN git clone https://github.com/apache/jena.git

