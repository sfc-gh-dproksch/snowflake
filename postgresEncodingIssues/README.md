# Postgres Encoding Issues WRT SQL_ASCII Encoded Databases

## Problem Statement
An SQL_ASCII encoded database will accept characters with an ascii code > 127.  Using psql, these characters are returned as "?".  Using a JDBC connection, an error is thrown.  ERROR: invalid byte sequence for encoding "UTF8": (hex code for character)

## Potential Workaround
