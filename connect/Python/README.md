# Connecting to Snowflake Using Python

The Snowflake Python connector supports Python 2.7.9+ and Python 3.4.3+  Always check [here](https://docs.snowflake.net/manuals/user-guide/python-connector.html) for the latest Python version requirements.

**Note:** The Python examples were created using 
```
conda create -n py37 python=3.7
conda activate py37
conda deactivate py37
```

## Install the Python Connector
1.  Check the version of Python.  
```
python --version
Python 3.7.2
```

2.  Install pip into the active Python environment.  Conda had already installed the latest version of Python.
```
python -m pip install --upgrade pip
Requirement already up-to-date: pip in /anaconda3/envs/py37/lib/python3.7/site-packages (19.0.3)
```

3.  Install the Snowflake Python connector
```
pip install --upgrade snowflake-connector-python
```
Pip will install the Snowflake connector **and** a boatload of dependencies.  The installation took less than two minutes.

4.  Validate the installation was successfull.
Execute the script __validate.py__.  Be mindful to change the values in the script to reflect the values needed to connect to your Snowflake account.
    - user
    - password
    - account
```
./validate.py 
3.15.2
```

## Connect a Python script to Snowflake
The sample script, connect.py

## Query Snowflake via Python
