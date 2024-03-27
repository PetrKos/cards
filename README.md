# Cards
run/debug the app or run  `mvn spring-boot:run`

At startup the application will create a schema and users.

Every time the app reruns, the schema will be dropped and start from the initial data.

a user will login with **email** and password. The password is hashed using Bcrypt. The password for every user is **12345**

![img.png](img.png)

# Cards API documentation
As long as the app is up and running, the documentation can be viewed at  http://localhost:8080/swagger-ui/index.html

![img_1.png](img_1.png)

# TOKEN GENERATION

In order to make requests, you need to get a token.
in order to get a token, you can use the following curl command.

`curl --location 'localhost:8080/api/login/token' \
--header 'Content-Type: application/json' \
--header 'Cookie: JSESSIONID=6749BA7BAE041853557D6B2E2AA493F0' \
--data-raw '{
"username":"admin2@gmail.com",
"password":"12345"
}'`

Notes: 

chose NO AUTH

![img_4.png](img_4.png)


In the request json, put any user from the database showed above.

![img_5.png](img_5.png)

**the token will be generated and copy paste it and use it to make the other API calls.

In the auth tab, put the token we generated earlier.

That happens because a user should not affect a resource that belongs to another user -security.**

# CREATE A CARD
`curl --location 'localhost:8080/api/cards/card-creation' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer eyJraWQiOiJlZTRlNGY1Zi0xYWEzLTQwM2QtOTY5Yi00ZTliNWQ0ZGIwMzYiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJzZWxmIiwic3ViIjoiYWRtaW4yQGdtYWlsLmNvbSIsImV4cCI6MTcxMTU3ODY2MSwiaWF0IjoxNzExNTQyNjYxLCJzY29wZSI6IlJPTEVfQURNSU4ifQ.ESq4OpJj75pMACgkdlu0jPB9xGTsXqm7A7LhuvfxO6lxpMWoTxmbSUw7w9JvfsuFHR4CsO7lUp3qJVrZXorbmvIt_GfYsaIUqikW7DBzTqgCSJRGRmA65RZaw0kDaWfo4_d8wUV5OQ1r90xUAt0p0ExL1N1b-17tlXIseD9njkJV2_zSOcsoJtpAulS4TPXrDQcJf2gykb1z3C58ko6xMFD9Lz9e8hliHnT8rUurGEvcxAkzqXhCUjSzn2x7LcGR2WoOM7rOT18qktgwwEqrkt88VrfQVSkcs9DVBCSXj0z3o9WCcxK5yRnJWLB_nZlLFLYf2m-BcUhc7KQf9ydPIg' \
--header 'Cookie: JSESSIONID=6749BA7BAE041853557D6B2E2AA493F0' \
--data '{
"name": "breakfast",
"description": "what to do",
"color": "#abc234"
}
'`

![img_6.png](img_6.png)


![img_7.png](img_7.png)

# RETRIEVE CARDS
`curl --location 'localhost:8080/api/cards/cards-retrieval' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer eyJraWQiOiJlZTRlNGY1Zi0xYWEzLTQwM2QtOTY5Yi00ZTliNWQ0ZGIwMzYiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJzZWxmIiwic3ViIjoiYWRtaW4yQGdtYWlsLmNvbSIsImV4cCI6MTcxMTU3ODY2MSwiaWF0IjoxNzExNTQyNjYxLCJzY29wZSI6IlJPTEVfQURNSU4ifQ.ESq4OpJj75pMACgkdlu0jPB9xGTsXqm7A7LhuvfxO6lxpMWoTxmbSUw7w9JvfsuFHR4CsO7lUp3qJVrZXorbmvIt_GfYsaIUqikW7DBzTqgCSJRGRmA65RZaw0kDaWfo4_d8wUV5OQ1r90xUAt0p0ExL1N1b-17tlXIseD9njkJV2_zSOcsoJtpAulS4TPXrDQcJf2gykb1z3C58ko6xMFD9Lz9e8hliHnT8rUurGEvcxAkzqXhCUjSzn2x7LcGR2WoOM7rOT18qktgwwEqrkt88VrfQVSkcs9DVBCSXj0z3o9WCcxK5yRnJWLB_nZlLFLYf2m-BcUhc7KQf9ydPIg' \
--header 'Cookie: JSESSIONID=6749BA7BAE041853557D6B2E2AA493F0' \
--data '{
"pageNumber": 0,
"pageSize": 10,
"sortDirection": "DESC",
"sortBy": "name",
"name": "",
"color": "",
"status": "",
"creationDate": ""
}
'`

Adjust the JSON body to get manipulate the data you want to see. 
**sortDirection is by default ASC. Use ASC or DESC specifically, otherwise it will throw error eg. desc is not allowed. **

![img_8.png](img_8.png)


# UPDATE A CARD
`curl --location --request PUT 'localhost:8080/api/cards/card-modification/2' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer eyJraWQiOiJlZTRlNGY1Zi0xYWEzLTQwM2QtOTY5Yi00ZTliNWQ0ZGIwMzYiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJzZWxmIiwic3ViIjoiYWRtaW4yQGdtYWlsLmNvbSIsImV4cCI6MTcxMTU3ODY2MSwiaWF0IjoxNzExNTQyNjYxLCJzY29wZSI6IlJPTEVfQURNSU4ifQ.ESq4OpJj75pMACgkdlu0jPB9xGTsXqm7A7LhuvfxO6lxpMWoTxmbSUw7w9JvfsuFHR4CsO7lUp3qJVrZXorbmvIt_GfYsaIUqikW7DBzTqgCSJRGRmA65RZaw0kDaWfo4_d8wUV5OQ1r90xUAt0p0ExL1N1b-17tlXIseD9njkJV2_zSOcsoJtpAulS4TPXrDQcJf2gykb1z3C58ko6xMFD9Lz9e8hliHnT8rUurGEvcxAkzqXhCUjSzn2x7LcGR2WoOM7rOT18qktgwwEqrkt88VrfQVSkcs9DVBCSXj0z3o9WCcxK5yRnJWLB_nZlLFLYf2m-BcUhc7KQf9ydPIg' \
--header 'Cookie: JSESSIONID=6749BA7BAE041853557D6B2E2AA493F0' \
--data '{
"name": "modified card 1",
"description": "modification without color",
"color": "",
"status": "IN_PROGRESS"
}'`

![img_9.png](img_9.png)


# DELETE A CARD

`curl --location --request DELETE 'localhost:8080/api/cards/card-deletion/2' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer eyJraWQiOiJlZTRlNGY1Zi0xYWEzLTQwM2QtOTY5Yi00ZTliNWQ0ZGIwMzYiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJzZWxmIiwic3ViIjoiYWRtaW4yQGdtYWlsLmNvbSIsImV4cCI6MTcxMTU3ODY2MSwiaWF0IjoxNzExNTQyNjYxLCJzY29wZSI6IlJPTEVfQURNSU4ifQ.ESq4OpJj75pMACgkdlu0jPB9xGTsXqm7A7LhuvfxO6lxpMWoTxmbSUw7w9JvfsuFHR4CsO7lUp3qJVrZXorbmvIt_GfYsaIUqikW7DBzTqgCSJRGRmA65RZaw0kDaWfo4_d8wUV5OQ1r90xUAt0p0ExL1N1b-17tlXIseD9njkJV2_zSOcsoJtpAulS4TPXrDQcJf2gykb1z3C58ko6xMFD9Lz9e8hliHnT8rUurGEvcxAkzqXhCUjSzn2x7LcGR2WoOM7rOT18qktgwwEqrkt88VrfQVSkcs9DVBCSXj0z3o9WCcxK5yRnJWLB_nZlLFLYf2m-BcUhc7KQf9ydPIg' \
--header 'Cookie: JSESSIONID=6749BA7BAE041853557D6B2E2AA493F0' \
--data ''`


![img_10.png](img_10.png)
