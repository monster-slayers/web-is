[![CircleCI](https://circleci.com/gh/monster-slayers/web-is.svg?style=svg)](https://circleci.com/gh/monster-slayers/web-is)
# Monster Slayers' Web-based Information system
This project is part of semestral project for course PA165 (Java EE) taught at Faculty of Informatics, Masaryk University which is part of master degree studies for computer science students. Please see our [Wiki pages](https://github.com/monster-slayers/web-is/wiki) for more information.
## No unit tests for repository objects?
As we extended our repository interfaces ("daos") by `CrudRepository` implementation of these interfaces is up to Spring Framework. Thus we are not implementing the unit tests for those as one does not write unit tests for others implementation. Instead we sufficiently covered by unit testing our entity classes.
## Examples of rest layer
### Logging in
```
curl -i -X POST -d username=maksym@manager.com -d password=manager -c cook.txt http://localhost:8080/pa165/login
```
*You need to be logged in in order to access the REST API.*

### Getting all jobs
```
curl -b cook.txt http://localhost:8080/pa165/rest/job
```
*Output of this script might be really long.*

### Updating status of a job
```
curl -X PUT -b cook.txt http://localhost:8080/pa165/rest/job/update-status/1/DONE
```
### Evaluating a job
```
curl -X PUT -b cook.txt http://localhost:8080/pa165/rest/job/update-evaluation/1/80
```

You can observe all these changes in your browser, just don't forget to reload the page.
