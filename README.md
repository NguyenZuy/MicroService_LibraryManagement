# MicroService Libraby Management

This is a mciroservice-base library management system.

## Table of Contentss

### Overview

The MicroService Library Management system is designed to provide a scalable and modular solution for managing library operations. It consists of multiple microservices that handle different aspects of library management, such as book cataloging, user management, and borrowing/returning books.

### Services

Here are a few APIs in this:

- [**Book Service**](https://github.com/NguyenZuy/MicroService_LibraryManagement/tree/main/BooksService) - Book management (Add, remove, change,...etc)
- [**Account Service**]() - Account management (Add, delete, change,...etc)
- [**Loan/Return Service**]() - Loan/Return management (Borrow, return, cancel,...etc)
- [**Analyst Service**]() - Analyst management

### Quick Start

- Clone Repository

```
git clone https://github.com/NguyenZuy/MicroService_LibraryManagement.git
```

- Import to IDE
- Run: run `service-registry` -> `api-gateway` -> `other services`

### Deployment

To deploy using Docker with Kubernetes, follow these steps:
- Build `executable jar` file for each service
- Create `Dockerfile` for each service
- Build Docker images for each service: Open Windows Powershell (Administrator) -> `cd <direction-service>`

Build local image

```
docker build -t <image-name> .  
```

Push to Docker Hub (create repository on <https://hub.docker.com/>)

```
docker tag books-service <tab-name>
```

```
docker push <tab-name>
```

Delete local image

```
docker rmi <image-name> <tag-name>
```

Run Docker

```
docker run -p <port>:<port> <tag-name>
```
