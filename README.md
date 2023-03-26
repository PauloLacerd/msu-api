# msu-api

### Api para listar as atualização de segurança da microsoft.
A api possue uma função agendada para ser executada a cada 5 minutos, ela busca todas as atualização de segurança da Microsoft desta URL: https://api.msrc.microsoft.com/cvrf/v2.0/updates. Após recuperar todas as atualizações ela verifica quais já estão salvas no banco de dados e quais são as novas atualizações para serem salvas.

Para iniciar o projeto é necessário criar o banco de dados: ```CREATE DATABASE "db-msu" WITH TEMPLATE=DEFAULT;``` <br/>
*Caso o banco de dados seja criado com um nome diferente, é necessário definir a variável de ambiente: ${DB_NAME}*

### Variáveis de ambiente:

- ${DB_URL} : jdbc:postgresql://localhost:5432/
- ${DB_NAME} : db-msu
- ${DB_USER} : postgres
- ${DB_PASSWORD} : postgres

***Obs: As variáveis de ambiente possuem valores default como demonstrado na lista acima.***
