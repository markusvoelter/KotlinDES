# KotlinDES

A simple discrete event simulation (DES) framework implemented in Kotlin. This repository can be seen as a companion implementation to an article on DES that I wrote recently (https://www.linkedin.com/pulse/how-build-discrete-event-simulations-markus-voelter).

## Runnnig

* This is an IntelliJ (2023.1.3) Gradle project. 
* After cloning it, you can either 
open it in IntelliJ and use its Gradle window to `test` the code, or you
can run `./gradlew test` from the command line. 
* The `test` task executes the
(as of now, few) tests in `src/test/kotlin`.


## Open Issues
 
* Performance: more efficient sorted list for the queue
* Performance: We could add a variant of monitors that fire only once;
  they would then be removed from the list so we don't 
  unnecessarily continue testing condition.
* Performance: Simulation.latest is inefficient.

## Acknowledgements

Thank you very much [Federico Tomassetti](https://github.com/ftomassetti) for reviewing my code and suggesting more idiomatic uses of Kotlin.
