
**TO FIX:
token get from it role and check if it can access the resource
call api fron angular
configure spring security + oauth 2.0 + keycloak ?

jacoco
cumcumber
dto layer
java relationships: 
one-one, one-many, many-many
unit testing	=> front
integration testing => front
unit testing	=> back
integration testing => back
add kafka
add angular app calls 2x api
transform 2 api => microservices
add doc => swagger
redis => caching ?
add monitoring ?
add logging dev
run jenkins pipeline java/spring, maven multi env ?
deploy on k8s ?



**Auth API (JWT/Role-Based):**
```bash
# REGISTER
curl -X POST http://localhost:8080/auth/register \
     -H "Content-Type: application/json" \
     -d '{"id": 2, "username": "john", "password": "secret", "role": "ADMIN"}'

curl -X POST http://localhost:8080/auth/register \
     -H "Content-Type: application/json" \
     -d '{"id": 1, "username": "jane", "password": "secret", "role": "MODERATOR"}'
     
# LOGIN
curl -X POST "http://localhost:8080/auth/login?username=john&password=secret"`  
curl -X POST "http://localhost:8080/auth/login?username=jane&password=secret"

# check protected routes
curl -H "Authorization: Bearer token" -X GET "http://localhost:8080/auth/admin/secure"
curl -H "Authorization: Bearer token" -X GET "http://localhost:8080/auth/moderator/secure"
```

**Product API:**
```sh
curl -H "Authorization: Bearer token" -H 'Content-Type: application/json' \
      -d '{ "name": "product name 1","price": 20.85, "id": 1}' \
      -X POST \
      http://localhost:8080/api/products
curl -H "Authorization: Bearer token" -H 'Content-Type: application/json' \
      -d '{ "name": "product name 2","price": 20.85, "id": 2}' \
      -X POST \
      http://localhost:8080/api/products
curl -H "Authorization: Bearer token" -H 'Content-Type: application/json' \
      -d '{ "name": "product name 3","price": 20.85, "id": 3}' \
      -X POST \
      http://localhost:8080/api/products
      
curl -H "Authorization: Bearer token" -X GET http://localhost:8080/api/products/1

curl -H "Authorization: Bearer token" -X GET http://localhost:8080/api/products

curl -H "Authorization: Bearer token" -X "DELETE" 'http://localhost:8080/api/products/2'
```

**Post API:**
```sh
curl -H "Authorization: Bearer token" -X GET http://localhost:8080/api/posts

curl -H "Authorization: Bearer token" -X GET http://localhost:8080/api/posts/1

curl -H "Authorization: Bearer token" -X POST http://localhost:8080/api/posts \
     -H "Content-Type: application/json" \
     -d '{
           "userId": 1,
           "id": 1,
           "title": "Nouveau Post",
           "body": "Contenu du post"
         }'

curl -H "Authorization: Bearer token" -X PUT http://localhost:8080/api/posts/1 \
     -H "Content-Type: application/json" \
     -d '{
           "userId": 1,
           "id": 1,
           "title": "Post modifié",
           "body": "Contenu mis à jour"
         }'
```


**Tutorial API:**
```shell
curl -X GET http://localhost:8083/api/tutorials

curl -X POST http://localhost:8083/api/tutorials \
     -H "Content-Type: application/json" \
     -d '{"id": 2, "username": "john", "password": "secret", "role": "ADMIN"}'
```
