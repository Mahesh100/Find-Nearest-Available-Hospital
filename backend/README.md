# AvailIt Backend API

A Spring Boot application for managing hospital bed and oxygen availability information.

## Features

- RESTful API for hospital availability management
- PostgreSQL database integration
- Interactive API documentation with Swagger/OpenAPI
- Complete CRUD operations for hospital data
- Manage hospital bed, ICU, ventilator, and oxygen availability
- Support for multiple locations per hospital
- Input validation and user-friendly error messages
- Pagination and sorting for large hospital lists

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- PostgreSQL database

## Setup

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd AvailIt/backend
   ```

2. **Database Setup**
   - Create a PostgreSQL database named `availit_db`
   - Update database credentials in `src/main/resources/application.properties`

3. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

## API Documentation

### Swagger UI
Once the application is running, you can access the interactive API documentation at:

- **Swagger UI**: http://localhost:9090/swagger-ui.html
- **OpenAPI JSON**: http://localhost:9090/api-docs

### Available Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/hospitals/getAllHospitals` | Get all hospitals |
| GET | `/api/hospitals/{id}` | Get hospital by ID |
| POST | `/api/hospitals` | Create a new hospital |
| PUT | `/api/hospitals/{id}` | Update hospital information |
| DELETE | `/api/hospitals/{id}` | Delete a hospital |

### Sample Request/Response

#### Create Hospital (with Multiple Locations)
```bash
POST /api/hospitals
Content-Type: application/json

{
  "hospitalName": "City General Hospital",
  "totalBeds": 100,
  "availableBeds": 25,
  "oxygenAvailable": true,
  "address": "123 Main St, Springfield",
  "contactNumber": "+1-555-1234",
  "icuBeds": 10,
  "ventilators": 5,
  "locations": [
    {
      "address": "123 Main St, Springfield",
      "city": "Springfield",
      "state": "IL",
      "zipCode": "62701"
    },
    {
      "address": "456 Elm St, Springfield",
      "city": "Springfield",
      "state": "IL",
      "zipCode": "62702"
    }
  ]
}
```

#### Response
```json
{
  "id": 1,
  "hospitalName": "City General Hospital",
  "totalBeds": 100,
  "availableBeds": 25,
  "oxygenAvailable": true,
  "address": "123 Main St, Springfield",
  "contactNumber": "+1-555-1234",
  "icuBeds": 10,
  "ventilators": 5,
  "locations": [
    {
      "address": "123 Main St, Springfield",
      "city": "Springfield",
      "state": "IL",
      "zipCode": "62701"
    },
    {
      "address": "456 Elm St, Springfield",
      "city": "Springfield",
      "state": "IL",
      "zipCode": "62702"
    }
  ]
}
```

### Get All Hospitals (with Pagination & Sorting)
**GET** `/api/hospitals/getAllHospitals?page=0&size=10&sort=hospitalName,asc`

- `page`: Page number (0-based)
- `size`: Number of records per page
- `sort`: Field and direction (e.g., `hospitalName,asc`)

### Error Handling
- Validation errors return HTTP 400 with a JSON body describing the invalid fields.

## Testing the API

1. **Using Swagger UI**:
   - Open http://localhost:9090/swagger-ui.html
   - Click on any endpoint to expand it
   - Click "Try it out" to test the endpoint
   - Fill in the required parameters
   - Click "Execute" to see the response

2. **Using curl**:
   ```bash
   # Get all hospitals
   curl -X GET "http://localhost:9090/api/hospitals/getAllHospitals"

   # Create a hospital
   curl -X POST "http://localhost:9090/api/hospitals" \
     -H "Content-Type: application/json" \
     -d '{
       "hospitalName": "Test Hospital",
       "totalBeds": 50,
       "availableBeds": 10,
       "oxygenAvailable": true
     }'
   ```

## Configuration

The application can be configured through `src/main/resources/application.properties`:

- Database connection settings
- Swagger UI customization
- Server port (default: 9090)

## Development

### Project Structure
```
src/main/java/com/availit/backend/
├── BackendApplication.java          # Main application class
├── controller/
│   └── HospitalAvailabilityController.java  # REST controllers
├── model/
│   └── HospitalAvailability.java    # Entity models
├── repository/
│   └── HospitalAvailabilityRepository.java  # Data access layer
├── service/
│   └── HospitalAvailabilityService.java     # Business logic
└── config/
    └── OpenApiConfig.java           # Swagger configuration
```

### Adding New Endpoints

1. Add the endpoint method to the controller
2. Add OpenAPI annotations for documentation
3. Implement the corresponding service method
4. Test using Swagger UI

## Troubleshooting

- **Port already in use**: Change the server port in `application.properties`
- **Database connection issues**: Verify PostgreSQL is running and credentials are correct
- **Swagger UI not loading**: Ensure the application started successfully and check the console logs

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## License

This project is licensed under the MIT License. 