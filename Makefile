# Healthcare Records Management System - Makefile

.PHONY: help build test docker-build docker-push k8s-deploy k8s-delete ansible-deploy clean

# Variables
DOCKER_USERNAME ?= pranavks28
BACKEND_IMAGE = $(DOCKER_USERNAME)/healthcare-backend
FRONTEND_IMAGE = $(DOCKER_USERNAME)/healthcare-frontend
TAG ?= latest

help: ## Show this help message
	@echo 'Usage: make [target]'
	@echo ''
	@echo 'Available targets:'
	@awk 'BEGIN {FS = ":.*?## "} /^[a-zA-Z_-]+:.*?## / {printf "  %-20s %s\n", $$1, $$2}' $(MAKEFILE_LIST)

# Development
dev-backend: ## Run backend in development mode
	cd backend && ./mvnw spring-boot:run

dev-frontend: ## Run frontend in development mode
	cd frontend && npm run dev

install-backend: ## Install backend dependencies
	cd backend && ./mvnw clean install

install-frontend: ## Install frontend dependencies
	cd frontend && npm install

# Testing
test-backend: ## Run backend tests
	cd backend && ./mvnw test

test-frontend: ## Run frontend tests
	cd frontend && npm run test

test: test-backend test-frontend ## Run all tests

# Build
build-backend: ## Build backend
	cd backend && ./mvnw clean package -DskipTests

build-frontend: ## Build frontend
	cd frontend && npm run build

build: build-backend build-frontend ## Build all

# Docker
docker-build-backend: ## Build backend Docker image
	docker build -t $(BACKEND_IMAGE):$(TAG) ./backend

docker-build-frontend: ## Build frontend Docker image
	docker build -t $(FRONTEND_IMAGE):$(TAG) ./frontend

docker-build: docker-build-backend docker-build-frontend ## Build all Docker images

docker-push-backend: ## Push backend image to Docker Hub
	docker push $(BACKEND_IMAGE):$(TAG)

docker-push-frontend: ## Push frontend image to Docker Hub
	docker push $(FRONTEND_IMAGE):$(TAG)

docker-push: docker-push-backend docker-push-frontend ## Push all images

docker-all: docker-build docker-push ## Build and push all Docker images

# Docker Compose
compose-up: ## Start services with docker-compose
	docker-compose up -d

compose-down: ## Stop services with docker-compose
	docker-compose down

compose-logs: ## View docker-compose logs
	docker-compose logs -f

compose-restart: compose-down compose-up ## Restart docker-compose services

# Kubernetes
k8s-deploy: ## Deploy to Kubernetes
	kubectl apply -f k8s/namespace.yaml
	kubectl apply -f k8s/configmap.yaml
	kubectl apply -f k8s/secrets.yaml
	kubectl apply -f k8s/postgres-deployment.yaml
	sleep 30
	kubectl apply -f k8s/backend-deployment.yaml
	kubectl apply -f k8s/frontend-deployment.yaml
	kubectl apply -f k8s/ingress.yaml

k8s-delete: ## Delete Kubernetes resources
	kubectl delete -f k8s/ || true

k8s-status: ## Check Kubernetes deployment status
	kubectl get all -n healthcare
	kubectl get pods -n healthcare

k8s-logs-backend: ## View backend logs
	kubectl logs -f -l app=backend -n healthcare

k8s-logs-frontend: ## View frontend logs
	kubectl logs -f -l app=frontend -n healthcare

k8s-restart-backend: ## Restart backend deployment
	kubectl rollout restart deployment/backend-deployment -n healthcare

k8s-restart-frontend: ## Restart frontend deployment
	kubectl rollout restart deployment/frontend-deployment -n healthcare

# Ansible
ansible-deploy: ## Deploy using Ansible
	cd ansible && ansible-playbook -i inventory.yml site.yml

ansible-quick-deploy: ## Quick deploy with Ansible
	cd ansible && ansible-playbook -i inventory.yml deploy.yml

ansible-rollback: ## Rollback deployment with Ansible
	cd ansible && ansible-playbook -i inventory.yml rollback.yml

ansible-ping: ## Test Ansible connectivity
	cd ansible && ansible -i inventory.yml all -m ping

# Clean
clean-backend: ## Clean backend build files
	cd backend && ./mvnw clean

clean-frontend: ## Clean frontend build files
	cd frontend && rm -rf dist node_modules

clean: clean-backend clean-frontend ## Clean all build files

# CI/CD
ci-test: test ## Run CI tests
	@echo "All tests passed!"

ci-build: build docker-build ## Build for CI
	@echo "Build completed!"

ci-deploy: docker-push k8s-deploy ## Full CI/CD pipeline
	@echo "Deployment completed!"

# Monitoring
monitor-setup: ## Setup monitoring stack
	kubectl create namespace monitoring || true
	helm repo add prometheus-community https://prometheus-community.github.io/helm-charts
	helm repo add grafana https://grafana.github.io/helm-charts
	helm repo update
	helm install prometheus prometheus-community/kube-prometheus-stack -n monitoring

monitor-port-forward: ## Port forward monitoring services
	@echo "Starting port forwarding..."
	@echo "Grafana will be available at http://localhost:3000"
	@echo "Prometheus will be available at http://localhost:9090"
	kubectl port-forward -n monitoring svc/prometheus-grafana 3000:80 &
	kubectl port-forward -n monitoring svc/prometheus-kube-prometheus-prometheus 9090:9090 &

# Utilities
port-forward: ## Port forward to local machine
	kubectl port-forward -n healthcare svc/frontend-service 8080:80

shell-backend: ## Get shell in backend pod
	kubectl exec -it $$(kubectl get pod -n healthcare -l app=backend -o jsonpath='{.items[0].metadata.name}') -n healthcare -- /bin/sh

shell-postgres: ## Get shell in postgres pod
	kubectl exec -it $$(kubectl get pod -n healthcare -l app=postgres -o jsonpath='{.items[0].metadata.name}') -n healthcare -- psql -U healthuser -d healthrecords

describe-backend: ## Describe backend deployment
	kubectl describe deployment/backend-deployment -n healthcare

describe-frontend: ## Describe frontend deployment
	kubectl describe deployment/frontend-deployment -n healthcare

# Complete workflows
local-dev: install-backend install-frontend ## Setup local development
	@echo "Local development setup complete!"

full-deploy: build docker-build docker-push k8s-deploy ## Complete deployment workflow
	@echo "Full deployment complete!"

reset: k8s-delete ## Reset everything
	docker-compose down -v
	@echo "Reset complete!"

