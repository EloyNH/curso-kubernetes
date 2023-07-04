## INSTRUCCIONES

Crear la red

```sh
docker network create spring
```

### Para levantar MySql:

```sh
docker run -d -p 3307:3306 --name mysql8 -e MYSQL_ROOT_PASSWORD=sasa -e MYSQL_DATABASE=msvc_usuarios mysql:8
docker run -d -p 3307:3306 --name mysql8 --network spring -e MYSQL_ROOT_PASSWORD=sasa -e MYSQL_DATABASE=msvc_usuarios mysql:8

docker run -d -p 3307:3306 --name mysql8 --network spring -e MYSQL_ROOT_PASSWORD=sasa -e MYSQL_DATABASE=msvc_usuarios -v mysql_data:/var/lib/mysql --restart=always mysql:8

docker run -it --rm --network spring mysql:8 bash
  bash-4.4# mysql -hmysql8 -uroot -p #enter
    mysql> show databases;
    mysql> use msvc_usuarios
    mysql> desc usuarios;
    mysql> select * from usuarios;
```

### Para levantar Postgres:

```sh
docker run -d -p 5532:5432 --name postgres14 -e POSTGRES_PASSWORD=sasa -e POSTGRES_DB=msvc_cursos -e POSTGRES_USER=postgres postgres:14-alpine
docker run -d -p 5532:5432 --name postgres14 --network spring -e POSTGRES_PASSWORD=sasa -e POSTGRES_DB=msvc_cursos -e POSTGRES_USER=postgres postgres:14-alpine

docker run -d -p 5532:5432 --name postgres14 --network spring -e POSTGRES_PASSWORD=sasa -e POSTGRES_DB=msvc_cursos -e POSTGRES_USER=postgres -v postgres_data:/var/lib/postgresql/data --restart=always postgres:14-alpine

docker run -it --rm --network spring postgres:14-alpine psql -h postgres14 -U postgres
  msvc_cursos=# \l
  msvc_cursos=# \l msvc_cursos
  msvc_cursos=# \dt
  msvc_cursos=# \d+ cursos
  msvc_cursos=# select * from cursos;
```

```sh
docker build -t usuarios . -f ./msvc-usuarios/Dockerfile
docker build -t usuarios . -f ./msvc-usuarios/Dockerfile --build-arg  PORT_APP=8080

docker build -t cursos . -f ./msvc-cursos/Dockerfile

docker run -p 8001:8001 -d --rm --name msvc-usuarios --network spring usuarios
docker run -p 8001:8090 --env PORT=8090 -d --rm --name msvc-usuarios --network spring usuarios
docker run -p 8001:8091 --env-file ./msvc-usuarios/.env -d --rm --name msvc-usuarios --network spring usuarios
docker run -p 8001:8001 --env-file ./msvc-usuarios/.env -d --rm --name msvc-usuarios --network spring usuarios

docker run -p 8002:8002 -d --rm --name msvc-cursos --network spring cursos
docker run -p 8002:8002 --env-file ./msvc-cursos/.env -d --rm --name msvc-cursos --network spring cursos

docker-compose up --build
```

```sh
chmod +x /home/eloy/IntellijIdea/curso-kubernetes/msvc-usuarios/mvnw
chmod +x /home/eloy/IntellijIdea/curso-kubernetes/msvc-cursos/mvnw
chmod +x /home/eloy/IntellijIdea/curso-kubernetes/msvc-gateway/mvnw
chmod +x /home/eloy/IntellijIdea/curso-kubernetes/msvc-auth/mvnw

docker build -t eloynh/usuarios . -f ./msvc-usuarios/Dockerfile
docker push eloynh/usuarios:latest

docker build -t eloynh/cursos . -f ./msvc-cursos/Dockerfile
docker push eloynh/cursos:latest

docker build -t eloynh/gateway . -f ./msvc-gateway/Dockerfile
docker push eloynh/gateway:latest

docker build -t eloynh/auth . -f ./msvc-auth/Dockerfile
docker push eloynh/auth:latest


```


```sh
minikube start --driver=docker

kubectl create deployment mysql8 --image=mysql:8 --port=3306 --dry-run=client -o yaml > deployment-mysql.yaml
kubectl apply -f ./deployment-mysql.yaml
kubectl describe pod mysql8-79445db559-8w2gm
kubectl logs mysql8-79445db559-8w2gm
kubectl expose deployment mysql8 --port=3306 --type=ClusterIP

kubectl create deployment msvc-usuarios --image=eloynh/usuarios:latest --port=8001
kubectl expose deployment msvc-usuarios --port=8001 --type=LoadBalancer

minikube service msvc-usuarios --url
en otro terminal kubectl port-forward service/msvc-usuarios 8001:8001

minikube service list

kubectl set image deployment msvc-usuarios usuarios=eloynh/usuarios:latest
kubectl scale deployment msvc-usuarios --replicas=3
kubectl scale deployment msvc-usuarios --replicas=1

kubectl get service mysql8 -o yaml > svc-mysql.yaml

kubectl create deployment msvc-usuarios --port=8001 --image=eloynh/usuarios:latest --dry-run=client -o yaml > deployment-usuarios.yaml


#CLASE 157 Aplicando cambios en K8s y probando en Postman
kubectl create clusterrolebinding admin --clusterrole=cluster-admin --serviceaccount=default:default

minikube dashboard
minikube service msvc-usuarios --url
minikube service msvc-cursos --url

```

PARA EXPONER UN POD DEL DEPLOYMENT EN CONSECUENCIA EL HOSTNAME QUE SE VA LLAMAR "mysql8"
Comunicacion interna kubectl expose deployment mysql8 --port=3306 --type=ClusterIP ok
Comunicacion externa kubectl expose deployment mysql8 --port=3306 --type=NodePort
Si existen pods en diferentes maquinas es mejor es kubectl expose deployment mysql8 --port=3306 --type=LoadBalancer


(hacerlo en una consola aparte para dejarlo corriendo):
kubectl port-forward service/msvc-usuarios 8001:8001

























