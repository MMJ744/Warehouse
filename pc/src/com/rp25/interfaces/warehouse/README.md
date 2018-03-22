# Warehouse Interface

Robots are abstractly represented by the `com.rp25,tools.Robot` class. 

A `Robot` has:
1. An `id` represented by an integer.
2. A single `Job`, which is the job it is currently on.
3. An `x` and a `y` coordinate represented by integers.

The state of the warehouse is stored in `com.rp25.interfaces.warehouse.WarehouseState`

The `WarehouseState` contains a Hash Map of Robots, with their ID as the key.

## Updating Robot Information
To update a Robot's information, you need a reference to the Warehouse State. Then the following methods from the Warehouse State can be called:
```java
public void updateBotPos(int id, int x, int y);
public void updateBotJob(int id, Job j);
```

## Updating Completed Jobs Information
There is a Collection of jobs stored in the warehouse state which can be obtained by calling:
```java
public Collection<Job> getAllCompletedJobs();
```
There is a method within the warehouse state to add a Job that has been completed to that collection by calling:
```java
public void addCompletedJob(Job j);
```