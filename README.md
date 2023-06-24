# KotlinDES

A simple discrete event simulation framework implemented in Kotlin



## Open Issues
 
* Performance: more efficient sorted list for the queue
* Performance: We could add a variant of monitors that fire only once;
  they would then be removed from the list so we don't 
  unnecessarily continue testing condition.
* Performance: Simulation.latest is inefficient.
  