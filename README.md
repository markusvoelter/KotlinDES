# KotlinDES

A simple discrete event simulation framework implemented in Kotlin



## Open Issues
 
* No support for dense time. If you register multiple
  events for the same time, their execution order is 
  non-deterministic. Potential simple solution: add a 
  global counter as part of the time that is sorted 
  after the main time.
  