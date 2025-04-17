# Vehicle Management System

This is a Java console application for managing vehicle rentals. It allows users to register, rent vehicles (cars, bikes, trucks), and manage their rentals.

## Features

- User registration and login
- Vehicle browsing and filtering
- Vehicle rental with date selection
- Rental management (view, complete, cancel)
- Payment processing
- Vehicle registration
- User profile management

## Vehicle Types

1. **Cars** - with details like seats, transmission type, AC availability
2. **Bikes** - with details like engine capacity and bike type
3. **Trucks** - with details like load capacity and refrigeration

## How to Run

1. Make sure you have Java JDK installed (version 8 or above)
2. Run the shell script located inside of src:
   ```
   sh run.sh
   ```

## Sample Workflow

1. Login or register a new account
2. Browse available vehicles
3. Rent a vehicle
4. Enter rental dates and payment information
5. Manage your rentals
6. Update your profile

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
