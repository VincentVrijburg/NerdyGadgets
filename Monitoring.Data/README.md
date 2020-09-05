# Monitoring.Data

This package gives you the opportunity to communicate with the database through various classes and methods.

The basic data operations (Create, Read, Update and Delete) will be explained below. This will cover most of the needs, 
but feel free to extend the capabilities of this package by creating additional methods.

### Repository pattern
To put it simply, Repository pattern is a kind of container where data access logic is stored. 
It hides the details of data access logic from business logic. 
In other words, we allow business logic to access the data object without having knowledge of underlying data 
access architecture.

#### Available repositories
Each Entity in the ERD scheme has its own repository class and interface.
- **User**
  - UserRepository
  - UserRepositoryInterface
- **Design**
  - DesignRepository
  - DesignRepositoryInterface
- **DesignComponent**
  - DesignComponentRepository
  - DesignComponentRepositoryInterface
- **Component**
  - ComponentRepository
  - ComponentRepositoryInterface
- **ComponentDetail**
  - ComponentDetailRepository
  - ComponentDetailRepositoryInterface
- **Log**
  - LogRepository
  - LogRepositoryInterface

### Usage of repositories

#### Find a User object by its identifier
```java
UserRepositoryInterface userRepository = new UserRepository();
User user = userRepository.findById("6186e4f8-883d-4847-9ba9-e59beb4b697a");
```

#### Get a list of User objects
```java
UserRepositoryInterface userRepository = new UserRepository();
List<User> users = userRepository.getUsers();
```

#### Create a User object
```java
UserRepositoryInterface userRepository = new UserRepository();
User user = new User(); // The identifier is set automatically!
user.setUsername("username");
user.setPassword();
user.setActive(true); // Defaults to true if empty
userRepository.persist(user);
```

#### Update a User object
```java
UserRepositoryInterface userRepository = new UserRepository();
// Using an existing User object...
user.setActive(false);
userRepository.persist(user);
```

#### Remove a User object
```java
UserRepositoryInterface userRepository = new UserRepository();
// Using an existing User object...
userRepository.remove(user);
```

**The above usages are the same for the other Entities, as of now!**