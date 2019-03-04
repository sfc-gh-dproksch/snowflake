#!/bin/bash
R CMD javareconf
R --save << EOF
install.packages("rJava",repos='http://cran.us.r-project.org')
library("rJava")
.jinit()
.jcall("java/lang/System", "S", "getProperty", "java.runtime.version")

install.packages("RJDBC",repos='http://cran.us.r-project.org')
EOF


