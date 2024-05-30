# PawBuddies Backend
This includes all about the backend side of the PawBuddies project.

## Quick start

### Clone the repository

```
git clone https://github.com/emirhanese/PawBuddies-Backend.git
```

### Build the project

```
cd PawBuddies-Backend/petcare
docker compose build
```

The project includes a ``web`` service, running the Java code, and a ``db`` service, running a MySQL database.
See the ``docker-compose.yml`` file for details.

### Run the project

```
docker compose up -d
````

Containers for both services will be launched. The project can be reached at http://localhost:8080
<br>
Swagger UI can be reached at http://localhost:8080/swagger-ui/index.html


## Working with PawBuddies in your IDE

### Prerequisites
The following items should be installed in your system:
* Java 17 or newer (full JDK, not a JRE).
* [git command line tool](https://help.github.com/articles/set-up-git)
* Your preferred IDE 
  * Eclipse with the m2e plugin. Note: when m2e is available, there is an m2 icon in `Help -> About` dialog. If m2e is
  not there, follow the install process [here](https://www.eclipse.org/m2e/)
  * [Spring Tools Suite](https://spring.io/tools) (STS)
  * [IntelliJ IDEA](https://www.jetbrains.com/idea/)
  * [VS Code](https://code.visualstudio.com)

### Steps:

1) On the command line run:
    ```
    git clone https://github.com/emirhanese/PawBuddies-Backend.git
    ```
2) Inside Eclipse or STS:
    ```
    File -> Import -> Maven -> Existing Maven project
    ```

3) Inside IntelliJ IDEA
    In the main menu, choose `File -> Open` and select the PawBuddies-Backend/petcare/[pom.xml](pom.xml). Click on the `Open` button.

    A run configuration named `PetcareApplication` should have been created for you if you're using a recent Ultimate version. Otherwise, run the application by right-clicking on the `PetcareApplication` main class and choosing `Run 'PetcareApplication'`.

4) Navigate to Petclinic

    Visit [http://localhost:8080](http://localhost:8080) in your browser.
