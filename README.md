# VehiclesAPI

## How to run project

- Start Eureka (can use run.sh file)
- start maps, pricing, and vehicles apis 
`mvn clean spring-boot:run`

## Examples of usage
Create car:

curl --header "Content-Type: application/json" \
  --request POST \
  --data '{"condition":"NEW","details":{"body":"sedan","model":"Impala","manufacturer":{"code":101,"name":"Chevrolet"},"numberOfDoors":4,"fuelType":"Gasoline","engine":"3.6L V6","mileage":32280,"modelYear":2018,"productionYear":2018,"externalColor":"white"},"location":{"lat":40.73061,"lon":-73.935242,"address":null,"city":null,"state":null,"zip":null},"price":null}' \
  http://localhost:8080/cars
  
Get car:

curl --header "Content-Type: application/json" \
--request GET \
http://localhost:8080/cars/1

Get cars:

curl --header "Content-Type: application/json" \
--request GET \
http://localhost:8080/cars

Update car:

curl --header "Content-Type: application/json" \
  --request POST \
  --data '{"id":1,"condition":"USED","details":{"body":"sedan","model":"Impala","manufacturer":{"code":101,"name":"Chevrolet"},"numberOfDoors":4,"fuelType":"Gasoline","engine":"3.6L V6","mileage":32280,"modelYear":2018,"productionYear":2018,"externalColor":"white"},"location":{"lat":40.73061,"lon":-73.935242,"address":null,"city":null,"state":null,"zip":null},"price":null}' \
  http://localhost:8080/cars
  
Delete car:

curl --request DELETE \
http://localhost:8080/cars/1

  