# Employee Management System Backend

## Docker MySQL Setup

### Prerequisites
- Docker and Docker Compose installed
- DataGrip (or any MySQL client)

### Starting MySQL with Docker

1. **Start the MySQL container:**
   ```bash
   docker-compose up -d
   ```

2. **Check if the container is running:**
   ```bash
   docker-compose ps
   ```

3. **View logs if needed:**
   ```bash
   docker-compose logs mysql
   ```

### Connecting DataGrip to MySQL

1. **Open DataGrip**

2. **Create a new data source:**
   - Click the '+' button → Data Source → MySQL

3. **Configure the connection:**
   - **Host:** `localhost`
   - **Port:** `3306`
   - **Database:** `ems`
   - **User:** `root`
   - **Password:** `root`
   - **URL:** `jdbc:mysql://localhost:3306/ems`

4. **Test the connection:**
   - Click "Test Connection" to verify it works

5. **Advanced settings (if needed):**
   - **Driver:** MySQL 8
   - **Use SSL:** Disabled (for local development)
   - **Server timezone:** UTC

### Application Configuration

The application is configured to connect to the MySQL database with these settings in `application.properties`:
- Database URL: `jdbc:mysql://localhost:3306/ems`
- Username: `root`
- Password: `root`

### Useful Docker Commands

```bash
# Start the database
docker-compose up -d

# Stop the database
docker-compose down

# Stop and remove volumes (WARNING: This will delete all data)
docker-compose down -v

# View running containers
docker ps

# Access MySQL CLI
docker exec -it ems-mysql mysql -u root -p
```

### Troubleshooting

1. **Port already in use:**
   - Check if MySQL is running locally: `sudo lsof -i :3306`
   - Stop local MySQL: `sudo service mysql stop` (Linux) or `brew services stop mysql` (macOS)

2. **Connection refused:**
   - Ensure Docker is running
   - Check container status: `docker-compose ps`
   - View logs: `docker-compose logs mysql`

3. **Authentication issues:**
   - Default credentials are root/root
   - You can also use ems_user/ems_password

### Database Schema

The application uses JPA/Hibernate with `spring.jpa.hibernate.ddl-auto=create`, which means:
- Tables will be automatically created based on your entity classes
- Data will be reset on each application restart (in development mode)
- For production, change to `update` or `validate` 