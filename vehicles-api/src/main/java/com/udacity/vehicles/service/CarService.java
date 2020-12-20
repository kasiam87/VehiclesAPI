package com.udacity.vehicles.service;

import com.udacity.vehicles.client.maps.MapsClient;
import com.udacity.vehicles.client.prices.PriceClient;
import com.udacity.vehicles.domain.Location;
import com.udacity.vehicles.domain.car.Car;
import com.udacity.vehicles.domain.car.CarRepository;
import java.util.List;

import com.udacity.vehicles.domain.manufacturer.Manufacturer;
import com.udacity.vehicles.domain.manufacturer.ManufacturerRepository;
import org.springframework.stereotype.Service;

/**
 * Implements the car service create, read, update or delete
 * information about vehicles, as well as gather related
 * location and price data when desired.
 */
@Service
public class CarService {

    private final CarRepository carRepository;
    private final ManufacturerRepository manufacturerRepository;
    private final MapsClient mapsClient;
    private final PriceClient pricesClient;

    public CarService(CarRepository carRepository, ManufacturerRepository manufacturerRepository, MapsClient mapsClient, PriceClient pricesClient) {
        /**
         * DONE: Add the Maps and Pricing Web Clients you create
         *   in `VehiclesApiApplication` as arguments and set them here.
         */
        this.carRepository = carRepository;
        this.manufacturerRepository = manufacturerRepository;
        this.mapsClient = mapsClient;
        this.pricesClient = pricesClient;
    }

    /**
     * Gathers a list of all vehicles
     * @return a list of all vehicles in the CarRepository
     */
    public List<Car> list() {
        return carRepository.findAll();
    }

    /**
     * Gets car information by ID (or throws exception if non-existent)
     * @param id the ID number of the car to gather information on
     * @return the requested car's information, including location and price
     */
    public Car findById(Long id) {
        /**
         * DONE: Find the car by ID from the `repository` if it exists.
         *   If it does not exist, throw a CarNotFoundException
         *   Remove the below code as part of your implementation.
         */

        Car car = carRepository.findById(id)
                .orElseThrow(() -> new CarNotFoundException("Cannot find car with id " + id));

        /**
         * DONE: Use the Pricing Web client you create in `VehiclesApiApplication`
         *   to get the price based on the `id` input'
         * DONE: Set the price of the car
         * Note: The car class file uses @transient, meaning you will need to call
         *   the pricing service each time to get the price.
         */

        String price = pricesClient.getPrice(id);
        car.setPrice(price);


        /**
         * DONE: Use the Maps Web client you create in `VehiclesApiApplication`
         *   to get the address for the vehicle. You should access the location
         *   from the car object and feed it to the Maps service.
         * DONE: Set the location of the vehicle, including the address information
         * Note: The Location class file also uses @transient for the address,
         * meaning the Maps service needs to be called each time for the address.
         */

        Location location = mapsClient.getAddress(car.getLocation());
        car.setLocation(location);

        return car;
    }

    /**
     * Either creates or updates a vehicle, based on prior existence of car
     * @param car A car object, which can be either new or existing
     * @return the new/updated car is stored in the repository
     */
    public Car save(Car car) {
        if (car.getId() != null) {
            checkIfManufacturerExists(car);
            return carRepository.findById(car.getId())
                    .map(carToBeUpdated -> {
                        carToBeUpdated.setDetails(car.getDetails());
                        carToBeUpdated.setLocation(car.getLocation());
                        return carRepository.save(carToBeUpdated);
                    }).orElseThrow(CarNotFoundException::new);
        }

        return carRepository.save(car);
    }

    private void checkIfManufacturerExists(Car car) {
        if (car.getDetails() != null && car.getDetails().getManufacturer() != null) {
            Manufacturer manufacturer = car.getDetails().getManufacturer();
            manufacturerRepository.findAll().stream()
                    .filter(m -> m.getCode().equals(manufacturer.getCode()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("manufacturer not found " + manufacturer.getCode()));
        }
    }

    /**
     * Deletes a given car by ID
     * @param id the ID number of the car to delete
     */
    public void delete(Long id) {
        /**
         * done: Find the car by ID from the `repository` if it exists.
         *   If it does not exist, throw a CarNotFoundException
         */

        Car car = carRepository.findById(id).orElseThrow(() -> new CarNotFoundException("Cannot find car with id " + id));
        carRepository.delete(car);

        /**
         * done: Delete the car from the repository.
         */


    }
}
