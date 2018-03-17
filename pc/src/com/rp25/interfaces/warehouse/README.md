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

## Getting Cancellations
There are buttons on the GUI which allow the user to cancel jobs at will. The warehouse state contains a blocking queue which has the ID numbers of the robots whose jobs are to be cancelled. When a user presses the cancel button on a particular robot, the robot's ID will get passed into the blocking queue. This method from the Warehouse State can be called to get this queue:
```java
public BlockingQueue<Integer> getCancellations();
```