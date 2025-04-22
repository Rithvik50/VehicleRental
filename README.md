# Vehicle Rental System

This is a Java console application for managing vehicle rentals. It allows users to register, rent vehicles (cars, bikes, trucks), and manage their rentals.

## Features

- User registration and login
- Vehicle rental with date selection
- Rental management (view, complete, cancel)
- Payment processing
- Vehicle registration

## Vehicle Types

1. **Cars** - with details like seats, transmission type, AC availability
2. **Bikes** - with details like engine capacity and bike type
3. **Trucks** - with details like load capacity and refrigeration

## How to Run

1. Make sure you have Java JDK installed (version 8 or above)
2. Install MySQL Connector/J jar file and place inside lib
3. Install Gson jar file and place inside lib
4. Run the shell script located inside of src:
   ```
   sh run.sh
   ```

## Database Integration

1. Download the RentalDatabase.sql code from the /database folder
2. Change the third argument of the DATABASE field in App.java to your localhost server password

## Sample Workflow

1. Login or register a new account
2. Rent available vehicles
3. Enter details for desired vehicle
3. Enter rental dates and registration number
4. Pay for rented vehicles
5. If admin, add vehicles

## System Requirements

- Java JDK 8 or above
- Console/Terminal for running the application
- MySQL

## Project Structure

- `User.java` - User class for managing user accounts
- `Insurance.java` - Represents the user's insurance
- `Vehicle.java` - Base class for all vehicles
- `Car.java`, `Bike.java`, `Truck.java` - Vehicle type classes
- `FuelType.java`, `TransmissionType.java` - Basic vehicle detail enumerators
- `Rental.java` - Rental page to access rental functions
- `Payment.java` - Payment processing
- `Cart.java` - View of the cart
- `VehicleHandler.java` - Handles vehicle inventory
- `Login.java` - Handles account login and registration
- `Window.java` - Main view of the rental system
- `App.java` - Main application entry point
