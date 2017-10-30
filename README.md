[![CircleCI](https://circleci.com/gh/monster-slayers/web-is.svg?style=svg)](https://circleci.com/gh/monster-slayers/web-is)
# Monster Slayers' Web-based Information system
This project is part of semestral project for course PA165 (Java EE) taught at Faculty of Informatics, Masaryk University which is part of master degree studies for computer science students. Please see our [Wiki pages](https://github.com/monster-slayers/web-is/wiki) for more information.
## No unit tests for repository objects?
As we extended our repository interfaces ("daos") by `CrudRepository` implementation of these interfaces is up to Spring Framework. Thus we are not implementing the unit tests for those as one does not write unit tests for others implementation. Instead we sufficiently covered by unit testing our entity classes.
