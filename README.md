
# projeto-RabbitMQ

Nomes: 

```
  Haniel Roanes Silva RA: 2011550422
```

```
  João Otávio da Silva RA: 2011550318
```      
## Rodando localmente

O RabbitMQ se encontra no arquivo: 


`projeto-RabbitMQ/biblioteca/docker-compose.yml`

vá até o diretorio `projeto-RabbitMQ/biblioteca`

para subi-lô basta rodar o comando:
```bash
  sudo docker-compose up -d
```

após feito seu uso, para derruba-lô rode o comando:
```bash
  sudo docker-compose down --remove-orphans
```

Existe uma biblioteca que criei na aplicação do [Insomnia](https://insomnia.rest/download) e esta disponivel para importação no arquivo:
`projeto-RabbitMQ/biblioteca.json`




## URLs de acesso da aplicação:

 - [RabbitMQ](http://localhost:15672/)
 ```bash
  usuario: admin
  senha: 123
```
 - [BD da aplicação core](http://localhost:8080/biblioteca/core/h2-core)
  ```bash
  url: jdbc:h2:mem:core
  username: sa
  password:
```
 - [BD da aplicação de agenda](http://localhost:8081/biblioteca/agenda/h2-agenda)
  ```bash
  url: jdbc:h2:mem:agenda
  username: sa
  password:
```
