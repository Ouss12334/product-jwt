# Product

* Products API 
* JWT using local server (spring-boot-oauth2-resource-server)
* Wishlist and cart API with generics
* h2 db
* in-memory users

## Certificates for JWT Conf
generated using openssl3
```
openssl genrsa -out keypair.pem 2048
openssl rsa -in keypair.pem -pubout -out public.pem
openssl pkcs8 -topk8 -inform PEM -outform PEM -nocrypt -in keypair.pem -out private.pem
```

