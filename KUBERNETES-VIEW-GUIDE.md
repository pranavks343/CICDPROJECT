# Kubernetes Deployment Viewing Guide

## 📍 Current Status

Your Kubernetes cluster is running and the `healthcare` namespace exists. Currently, only PostgreSQL is deployed.

## 🔍 Commands to View Your Kubernetes Deployments

### 1. **View All Deployments**
```bash
kubectl get deployments -n healthcare
```

### 2. **View Detailed Deployment Information**
```bash
# Backend deployment details
kubectl describe deployment backend-deployment -n healthcare

# Frontend deployment details
kubectl describe deployment frontend-deployment -n healthcare

# PostgreSQL deployment details
kubectl describe deployment postgres-deployment -n healthcare
```

### 3. **View All Pods (Running Containers)**
```bash
# List all pods
kubectl get pods -n healthcare

# Watch pods in real-time
kubectl get pods -n healthcare -w

# Get detailed pod information
kubectl get pods -n healthcare -o wide
```

### 4. **View Pod Logs**
```bash
# Backend logs
kubectl logs -n healthcare -l app=backend --tail=100

# Frontend logs
kubectl logs -n healthcare -l app=frontend --tail=100

# Follow logs in real-time
kubectl logs -n healthcare -l app=backend -f
```

### 5. **View Services**
```bash
# List all services
kubectl get services -n healthcare

# Detailed service information
kubectl describe service backend-service -n healthcare
kubectl describe service frontend-service -n healthcare
```

### 6. **View Ingress**
```bash
kubectl get ingress -n healthcare
kubectl describe ingress healthcare-ingress -n healthcare
```

### 7. **View ConfigMaps and Secrets**
```bash
# ConfigMaps
kubectl get configmaps -n healthcare
kubectl describe configmap backend-config -n healthcare

# Secrets (values are hidden)
kubectl get secrets -n healthcare
kubectl describe secret backend-secrets -n healthcare
```

### 8. **View Horizontal Pod Autoscalers**
```bash
kubectl get hpa -n healthcare
kubectl describe hpa backend-hpa -n healthcare
```

### 9. **View All Resources in Namespace**
```bash
# Get everything
kubectl get all -n healthcare

# Get all resources with labels
kubectl get all -n healthcare --show-labels
```

### 10. **Interactive Dashboard (if available)**
```bash
# Check if Kubernetes dashboard is installed
kubectl get pods -n kubernetes-dashboard

# Access dashboard (if installed)
kubectl proxy
# Then open: http://localhost:8001/api/v1/namespaces/kubernetes-dashboard/services/https:kubernetes-dashboard:/proxy/
```

## 🚀 Deploy Your Application

To deploy backend and frontend, you need to:

1. **Build Docker images** and push to a registry (or use local images)
2. **Update image names** in deployment files (replace `DOCKER_HUB_USERNAME` and `IMAGE_TAG`)
3. **Apply the configurations**:

```bash
# Apply all configurations
kubectl apply -f k8s/

# Or apply individually
kubectl apply -f k8s/namespace.yaml
kubectl apply -f k8s/configmap.yaml
kubectl apply -f k8s/secrets.yaml
kubectl apply -f k8s/postgres-deployment.yaml
kubectl apply -f k8s/backend-deployment.yaml
kubectl apply -f k8s/frontend-deployment.yaml
kubectl apply -f k8s/ingress.yaml
```

## 📊 Monitoring Commands

### Check Deployment Status
```bash
# Watch deployments
kubectl get deployments -n healthcare -w

# Check rollout status
kubectl rollout status deployment/backend-deployment -n healthcare
kubectl rollout status deployment/frontend-deployment -n healthcare
```

### Check Pod Health
```bash
# Get pod status
kubectl get pods -n healthcare -o jsonpath='{range .items[*]}{.metadata.name}{"\t"}{.status.phase}{"\t"}{.status.containerStatuses[0].ready}{"\n"}{end}'

# Check pod events
kubectl get events -n healthcare --sort-by='.lastTimestamp'
```

### Resource Usage
```bash
# Top pods by CPU/Memory
kubectl top pods -n healthcare
kubectl top nodes
```

## 🔧 Troubleshooting Commands

```bash
# Execute commands in a pod
kubectl exec -it <pod-name> -n healthcare -- /bin/sh

# Check pod logs with timestamps
kubectl logs <pod-name> -n healthcare --timestamps

# Get pod YAML for debugging
kubectl get pod <pod-name> -n healthcare -o yaml

# Check events
kubectl get events -n healthcare --field-selector involvedObject.kind=Pod
```

## 🌐 Access Your Application

### Get Service URLs
```bash
# Get service endpoints
kubectl get endpoints -n healthcare

# Port forward to access locally
kubectl port-forward -n healthcare service/backend-service 8080:8080
kubectl port-forward -n healthcare service/frontend-service 3000:80

# Get ingress external IP (if LoadBalancer)
kubectl get ingress -n healthcare
```

## 📝 Quick Reference

| Resource | Command |
|----------|---------|
| Deployments | `kubectl get deployments -n healthcare` |
| Pods | `kubectl get pods -n healthcare` |
| Services | `kubectl get services -n healthcare` |
| Ingress | `kubectl get ingress -n healthcare` |
| Logs | `kubectl logs -l app=backend -n healthcare` |
| Events | `kubectl get events -n healthcare` |
| All Resources | `kubectl get all -n healthcare` |

## 🎯 Current Deployment Status

Run this command to see everything at once:
```bash
kubectl get all -n healthcare
```

