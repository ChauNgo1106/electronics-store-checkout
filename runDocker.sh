docker stop $(docker ps -aq) && docker rm $(docker ps -aq)
docker rmi $(docker images -a -q)
docker build -t electric-store .
docker run --name electric-store-container -p 8080:8080 electric-store