# OpenEMP User API
OpenEMP API service to expose REST API endpoints to manage user's resources.

all responses return JSON object, All requests must include a `content-type` of `application/json`

## Setup

### Requirements

* Java >= 11
* maven >= 3
* PostgreSQL (for production - Optional)
### Build

To build the user API run the following:

```shell script
git clone https://github.com/openemp/openemp-api-user
cd openemp-api-user
export spring_profiles_active=dev
mvn clean install
```

### Start

to start with dev environement:

```shell script
export spring_profiles_active=dev
mvn spring-boot:run
```
for more details on how to run user api please visit the documentation [here](https://docs.openemp.org)

### Features

* Create and manage users
* Authenticate user and generate JWT Token
* Manage roles and privileges
* Manage profiles
* Roles based access control
