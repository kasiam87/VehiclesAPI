docker ps -a | grep eureka | awk '{print $1}' | xargs -I {} docker stop {}
docker ps -a | grep eureka | awk '{print $1}' | xargs -I {} docker rm {}
docker run -d -p 8761:8761 springcloud/eureka