FROM postgres:9.6
COPY ./repro.steps /tmp
USER postgres
RUN /bin/bash /tmp/repro.steps 
USER root
