# KotlinDES

A simple discrete event simulation (DES) framework implemented in Kotlin. This repository can be seen as a companion implementation to an article on DES that I wrote recently. For now, the article is available as a Google doc (https://docs.google.com/document/d/11T9kca1bcb7eDI7yi9DxPq2TQoQ46_X9uPsuGJ2XyKA); let me know if you need access. However, the article will be posted somewhere reasoable at some point, and I will update the link.

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
