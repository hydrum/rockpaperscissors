docker-compose -f docker-compose.yml -p rockpaperscissors down --volumes --remove-orphans
docker-compose -f docker-compose.yml -p rockpaperscissors up --no-start --quiet-pull

docker container ls --all --quiet --filter label=com.docker.compose.project=rockpaperscissors --filter label=com.docker.compose.service=mysql8 > tmpFile
set /p mysql8_container= < tmpFile
docker container ls --all --quiet --filter label=com.docker.compose.project=rockpaperscissors --filter label=com.docker.compose.service=db-schema > tmpFile
set /p db-schema_container= < tmpFile
docker container ls --all --quiet --filter label=com.docker.compose.project=rockpaperscissors --filter label=com.docker.compose.service=tomcat > tmpFile
set /p tomcat_container= < tmpFile
del tmpFile

docker cp create_schema.sql %mysql8_container%:/docker-entrypoint-initdb.d/create_schema.sql
docker cp backend/src/main/resources/db/migration %db-schema_container%:/flyway/sql
docker-compose -f docker-compose.yml -p rockpaperscissors up -d mysql8
docker-compose -f docker-compose.yml -p rockpaperscissors up db-schema
docker cp backend/target/backend-1.0-SNAPSHOT.jar %tomcat_container%:/rockpaperscissors.jar
docker-compose -f docker-compose.yml -p rockpaperscissors up -d tomcat
