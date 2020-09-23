# Public API  Test Suite

This Test Suite is Based on Java 8 Lenguage, uses TestNg Testing tool. It has Extent TestNG Reports

## Installation

Clone the repository from https://github.com/raslan066/publicApi.git.

```bash
git clone https://github.com/raslan066/publicApi.git
```

## Usage
1. Go to the project folder
2. w/o test group specified:
```mvn
 mvn clean test (It will run smoke test)
```
3. With specific group names:
```
a. smoke
 mvn clean test -Dgroups=smoke
b. login
 mvn clean test -Dgroups=login
c. logout
 mvn clean test -Dgroups=logout
d. update
 mvn clean test -Dgroups=update
e. delete
 mvn clean test -Dgroups=delete
``` 

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.
