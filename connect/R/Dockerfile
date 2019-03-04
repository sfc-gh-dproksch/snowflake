FROM r-base
#
# Add in the OpenJDK 8
# Over time, this will need to be updated to reflect
# new patch versions of the OpenJDK
#
RUN cd /tmp && wget https://github.com/AdoptOpenJDK/openjdk8-binaries/releases/download/jdk8u202-b08/OpenJDK8U-jdk_x64_linux_hotspot_8u202b08.tar.gz && tar -zxvf OpenJDK8U-jdk_x64_linux_hotspot_8u202b08.tar.gz && /bin/rm -f OpenJDK8U-jdk_x64_linux_hotspot_8u202b08.tar.gz && wget https://repo1.maven.org/maven2/net/snowflake/snowflake-jdbc/3.6.9/snowflake-jdbc-3.6.9.jar

#
# Set JAVA_HOME and prepend $JAVA_HOME/bin to $PATH
#
ENV JAVA_HOME=/tmp/jdk8u202-b08
ENV PATH="${JAVA_HOME}/bin:${PATH}"
ENV CLASSPATH="${CLASSPATH}:/tmp/snowflake-jdbc-3.6.9.jar"

#
# Configure the R Java Environment
#
WORKDIR /tmp
COPY conf.sh /tmp/
RUN chmod 0755 ./conf.sh && ./conf.sh
COPY connect.R /tmp/
#RUN "R --no-save connect.R"
