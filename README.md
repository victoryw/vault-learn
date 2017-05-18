# vault-learn
deploy and use the vault to save the secret, take the spring database connection as example

## what has done
- [x] setup up the vault server in dev mode by customize token
- [x] should auto import the security key/value pair into vault
- [x] spring application could use the security key/value pair in vault
- [x] use the vault to store the pg database connection string
- [x] spring application api test nowith vault and bootstrap.properties(分环境配置)
- [x] flyway migration with vault

## how to use

## tech note
### use the vagrant and ansible setup the vault sever
1. sh setup-server.sh to use vault in dev mode & export the tcp/ip
1. sh import secret to vault sever 
### spring integration with vault
[spring-cloud-vault](https://github.com/spring-cloud/spring-cloud-vault)
[spring-cloud-vault-doc](http://cloud.spring.io/spring-cloud-vault/spring-cloud-vault.html)
#### build.gradle
```
id "io.spring.dependency-management" version "1.0.2.RELEASE"
dependencyManagement {
    imports {
        mavenBom 'org.springframework.cloud:spring-cloud-vault-dependencies:1.0.1.RELEASE'
    }
}
compile 'org.springframework.cloud:spring-cloud-starter-vault-config'
```
#### bootstrap.properties(spring cloud config file #29)
```
spring.application.name=my-application
spring.cloud.vault.host=127.0.0.1
spring.cloud.vault.port=8201
spring.cloud.vault.scheme=http
spring.cloud.vault.authentication=token
spring.cloud.vault.token=devroot
```
### spring integration with database
[dynamic-example](https://dzone.com/articles/managing-your-database-secrets-with-vault)
[vault-pg](https://www.vaultproject.io/docs/secrets/postgresql/index.html)

```
spring.datasource.url=jdbc:postgresql://localhost:5436/vaultdb
spring.cloud.vault.postgresql.enabled=true
spring.cloud.vault.postgresql.role=operator
spring.cloud.vault.postgresql.backend=postgresql
spring.cloud.vault.postgresql.username-property=spring.datasource.username
spring.cloud.vault.postgresql.password-property=spring.datasource.password
```

### spring integration test with non-vault
In the test class should be with
`@ActiveProfiles(profiles = "test")`

In the bootstrap-test.properties should be with
```
spring.application.name=my-application
spring.config.enabled=false
spring.cloud.vault.enabled=false
spring.cloud.vault.postgresql.enabled=false
password=''
spring.datasource.url=jdbc:h2:mem:app
spring.datasource.password=
```

### flyway migrate database with vault
[use docker to run flyway with vault](https://github.com/orgsync/docker-flyway-vault)
```
a=`ifconfig | sed -En 's/127.0.0.1//;s/.*inet (addr:)?(([0-9]*\.){3}[0-9]*).*/\2/p' | sed -n '1p;1q'`
```
not use the dynamic secret 
for the pg not allow non-owner to remove/change the table


