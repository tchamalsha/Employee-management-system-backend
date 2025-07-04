#!/bin/bash

# Database Management Script for EMS

case "$1" in
    "start")
        echo "Starting MySQL database..."
        docker-compose up -d
        echo "MySQL is running on localhost:3306"
        ;;
    "stop")
        echo "Stopping MySQL database..."
        docker-compose down
        echo "MySQL stopped"
        ;;
    "restart")
        echo "Restarting MySQL database..."
        docker-compose down
        docker-compose up -d
        echo "MySQL restarted"
        ;;
    "status")
        echo "Checking MySQL status..."
        docker-compose ps
        ;;
    "logs")
        echo "Showing MySQL logs..."
        docker-compose logs mysql
        ;;
    "shell")
        echo "Opening MySQL shell..."
        docker exec -it ems-mysql mysql -u root -p
        ;;
    "reset")
        echo "WARNING: This will delete all data!"
        read -p "Are you sure? (y/N): " -n 1 -r
        echo
        if [[ $REPLY =~ ^[Yy]$ ]]; then
            docker-compose down -v
            docker-compose up -d
            echo "Database reset complete"
        else
            echo "Reset cancelled"
        fi
        ;;
    *)
        echo "Usage: $0 {start|stop|restart|status|logs|shell|reset}"
        echo ""
        echo "Commands:"
        echo "  start   - Start MySQL database"
        echo "  stop    - Stop MySQL database"
        echo "  restart - Restart MySQL database"
        echo "  status  - Show database status"
        echo "  logs    - Show database logs"
        echo "  shell   - Open MySQL shell"
        echo "  reset   - Reset database (delete all data)"
        exit 1
        ;;
esac 