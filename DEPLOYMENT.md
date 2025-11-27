# Healthcare Records Management System - Deployment Guide

## Table of Contents
1. [Prerequisites](#prerequisites)
2. [Docker Setup](#docker-setup)
3. [Kubernetes Deployment](#kubernetes-deployment)
4. [GitHub Actions CI/CD](#github-actions-cicd)
5. [Ansible Automation](#ansible-automation)
6. [Monitoring](#monitoring)
7. [Troubleshooting](#troubleshooting)

## Prerequisites

### Required Tools
- Docker 20.10+
- Kubernetes 1.28+
- kubectl
- Helm 3.x
- Ansible 2.14+
- Python 3.8+
- Git

### Required Accounts
- Docker Hub account
- GitHub account
- Kubernetes cluster (local or cloud)

## Docker Setup

### Local Development with Docker Compose

1. **Start all services:**
```bash
docker-compose up -d
```

2. **Check service status:**
```bash
docker-compose ps
```

3. **View logs:**
```bash
docker-compose logs -f
```

4. **Stop services:**
```bash
docker-compose down
```

### Build Individual Docker Images

**Backend:**
```bash
cd backend
docker build -t healthcare-backend:latest .
```

**Frontend:**
```bash
cd frontend
docker build -t healthcare-frontend:latest .
```

### Push to Docker Hub

```bash
# Login to Docker Hub
docker login

# Tag images
docker tag healthcare-backend:latest YOUR_USERNAME/healthcare-backend:latest
docker tag healthcare-frontend:latest YOUR_USERNAME/healthcare-frontend:latest

# Push images
docker push YOUR_USERNAME/healthcare-backend:latest
docker push YOUR_USERNAME/healthcare-frontend:latest
```

## Kubernetes Deployment

### Setup Kubernetes Cluster

#### Option 1: Local (Minikube)
```bash
minikube start --cpus=4 --memory=8192
minikube addons enable ingress
```

#### Option 2: Local (Kind)
```bash
kind create cluster --config=k8s/kind-config.yaml
```

#### Option 3: Cloud (EKS/GKE/AKS)
Follow your cloud provider's documentation.

### Deploy to Kubernetes

1. **Apply all manifests:**
```bash
# Create namespace
kubectl apply -f k8s/namespace.yaml

# Apply configurations
kubectl apply -f k8s/configmap.yaml
kubectl apply -f k8s/secrets.yaml

# Deploy database
kubectl apply -f k8s/postgres-deployment.yaml

# Wait for PostgreSQL to be ready
kubectl wait --for=condition=ready pod -l app=postgres -n healthcare --timeout=300s

# Deploy backend
kubectl apply -f k8s/backend-deployment.yaml

# Deploy frontend
kubectl apply -f k8s/frontend-deployment.yaml

# Setup ingress
kubectl apply -f k8s/ingress.yaml
```

2. **Verify deployment:**
```bash
kubectl get all -n healthcare
kubectl get pods -n healthcare -w
```

3. **Access the application:**
```bash
# Get service URL
kubectl get svc -n healthcare

# Port forward for local access
kubectl port-forward svc/frontend-service 8080:80 -n healthcare
```

### Update Deployment

```bash
# Update backend image
kubectl set image deployment/backend-deployment backend=YOUR_USERNAME/healthcare-backend:v2 -n healthcare

# Update frontend image
kubectl set image deployment/frontend-deployment frontend=YOUR_USERNAME/healthcare-frontend:v2 -n healthcare

# Check rollout status
kubectl rollout status deployment/backend-deployment -n healthcare
kubectl rollout status deployment/frontend-deployment -n healthcare
```

### Rollback Deployment

```bash
# Rollback to previous version
kubectl rollout undo deployment/backend-deployment -n healthcare
kubectl rollout undo deployment/frontend-deployment -n healthcare

# Rollback to specific revision
kubectl rollout undo deployment/backend-deployment --to-revision=2 -n healthcare
```

## GitHub Actions CI/CD

### Setup GitHub Secrets

Go to your repository → Settings → Secrets and variables → Actions, and add:

1. **DOCKER_HUB_USERNAME**: Your Docker Hub username
2. **DOCKER_HUB_TOKEN**: Docker Hub access token
3. **KUBE_CONFIG**: Base64 encoded kubeconfig file
4. **SSH_PRIVATE_KEY**: SSH private key for Ansible (if using)

### Get Base64 Encoded Kubeconfig

```bash
cat ~/.kube/config | base64
```

### Workflow Triggers

The CI/CD pipeline automatically triggers on:
- Push to `main` branch
- Pull requests to `main` branch
- Manual workflow dispatch

### Pipeline Stages

1. **Test Backend**: Run unit tests and build
2. **Test Frontend**: Run build and checks
3. **Build & Push**: Build Docker images and push to Docker Hub
4. **Deploy**: Deploy to Kubernetes cluster
5. **Ansible**: Run Ansible automation tasks

### Manual Trigger

```bash
# Via GitHub UI
Go to Actions → CI/CD Pipeline → Run workflow

# Via GitHub CLI
gh workflow run ci-cd.yml
```

## Ansible Automation

### Configure Inventory

Edit `ansible/inventory.yml` with your server IPs:

```yaml
all:
  children:
    kubernetes:
      children:
        master:
          hosts:
            k8s-master:
              ansible_host: YOUR_MASTER_IP
        workers:
          hosts:
            k8s-worker-1:
              ansible_host: YOUR_WORKER1_IP
```

### Run Playbooks

1. **Full deployment:**
```bash
cd ansible
ansible-playbook -i inventory.yml site.yml
```

2. **Quick deployment update:**
```bash
cd ansible
export DOCKER_HUB_USERNAME=your_username
export IMAGE_TAG=latest
ansible-playbook -i inventory.yml deploy.yml
```

3. **Rollback:**
```bash
cd ansible
ansible-playbook -i inventory.yml rollback.yml
```

### Test Ansible Connection

```bash
cd ansible
ansible -i inventory.yml all -m ping
```

## Monitoring

### Access Grafana

```bash
# Port forward to access Grafana
kubectl port-forward -n monitoring svc/prometheus-grafana 3000:80

# Open browser at http://localhost:3000
# Username: admin
# Password: admin123
```

### Access Prometheus

```bash
kubectl port-forward -n monitoring svc/prometheus-kube-prometheus-prometheus 9090:9090
```

### View Logs

```bash
# Backend logs
kubectl logs -f deployment/backend-deployment -n healthcare

# Frontend logs
kubectl logs -f deployment/frontend-deployment -n healthcare

# All pods in namespace
kubectl logs -f -l tier=application -n healthcare
```

## Troubleshooting

### Common Issues

#### 1. Pods not starting

```bash
# Check pod status
kubectl describe pod POD_NAME -n healthcare

# Check logs
kubectl logs POD_NAME -n healthcare

# Check events
kubectl get events -n healthcare --sort-by='.lastTimestamp'
```

#### 2. Database connection issues

```bash
# Check PostgreSQL logs
kubectl logs -f deployment/postgres-deployment -n healthcare

# Test connection from backend pod
kubectl exec -it POD_NAME -n healthcare -- psql -h postgres-service -U healthuser -d healthrecords
```

#### 3. Image pull errors

```bash
# Check if images exist on Docker Hub
docker pull YOUR_USERNAME/healthcare-backend:latest

# Check image pull secrets
kubectl get secrets -n healthcare
```

#### 4. Service not accessible

```bash
# Check services
kubectl get svc -n healthcare

# Check endpoints
kubectl get endpoints -n healthcare

# Test service internally
kubectl run test --rm -it --image=busybox -n healthcare -- wget -O- http://backend-service:8080/actuator/health
```

### Debug Commands

```bash
# Get all resources
kubectl get all -n healthcare

# Describe deployment
kubectl describe deployment backend-deployment -n healthcare

# Get pod details
kubectl get pods -n healthcare -o wide

# Execute command in pod
kubectl exec -it POD_NAME -n healthcare -- /bin/sh

# Check resource usage
kubectl top pods -n healthcare
kubectl top nodes
```

### Reset Everything

```bash
# Delete namespace (removes all resources)
kubectl delete namespace healthcare

# Redeploy
kubectl apply -f k8s/
```

## Production Considerations

### Security
- [ ] Change default passwords in `k8s/secrets.yaml`
- [ ] Enable TLS/HTTPS with cert-manager
- [ ] Implement network policies
- [ ] Use sealed secrets or external secret managers
- [ ] Enable RBAC

### Performance
- [ ] Configure resource limits appropriately
- [ ] Set up horizontal pod autoscaling
- [ ] Implement caching strategies
- [ ] Use persistent volumes for database

### Reliability
- [ ] Set up backup and restore procedures
- [ ] Implement disaster recovery plan
- [ ] Configure monitoring and alerts
- [ ] Set up log aggregation
- [ ] Implement health checks

### Scalability
- [ ] Use managed Kubernetes service
- [ ] Implement database replication
- [ ] Set up CDN for static assets
- [ ] Configure load balancing

## Support

For issues and questions:
- Create an issue in the GitHub repository
- Check existing documentation
- Review logs and events

## License

This project is licensed under the MIT License.

