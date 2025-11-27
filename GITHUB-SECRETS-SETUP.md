# GitHub Secrets Setup Guide

## ✅ Step-by-Step Instructions

Your code has been successfully pushed to: **https://github.com/pranavks343/CICDPROJECT.git**

Now you need to add GitHub secrets to enable the CI/CD pipeline.

## 📝 Required Secrets

### 1. DOCKER_HUB_USERNAME

**What it is:** Your Docker Hub username

**How to get it:**
- Your username is: `pranavks343`
- Or go to https://hub.docker.com/ and check your profile

**Value to enter:** `pranavks343`

---

### 2. DOCKER_HUB_TOKEN

**What it is:** An access token to push images to Docker Hub

**How to get it:**

1. Go to https://hub.docker.com/settings/security
2. Click **"New Access Token"**
3. Description: `github-actions-cicd`
4. Access permissions: **Read, Write, Delete**
5. Click **"Generate"**
6. **COPY THE TOKEN IMMEDIATELY** (you won't see it again!)

**Value to enter:** The token you just copied (looks like: `dckr_pat_abc123...`)

---

### 3. KUBE_CONFIG (Optional - for Kubernetes deployment)

**What it is:** Your Kubernetes cluster configuration file (base64 encoded)

**How to get it:**

```bash
cat ~/.kube/config | base64
```

**Value to enter:** The base64 encoded string

**Note:** Only needed if you want GitHub Actions to automatically deploy to your Kubernetes cluster.

---

## 🔧 How to Add Secrets to GitHub

### Method 1: Using GitHub Web Interface

1. Go to your repository: https://github.com/pranavks343/CICDPROJECT

2. Click on **"Settings"** (top menu)

3. In the left sidebar, click **"Secrets and variables"** → **"Actions"**

4. Click **"New repository secret"**

5. Add each secret:
   - Name: `DOCKER_HUB_USERNAME`
   - Secret: `pranavks343`
   - Click **"Add secret"**

6. Repeat for `DOCKER_HUB_TOKEN` with the token from Docker Hub

7. (Optional) Repeat for `KUBE_CONFIG` if you have a cluster

### Method 2: Using GitHub CLI

```bash
# Install GitHub CLI if not already installed
brew install gh

# Login to GitHub
gh auth login

# Add secrets
gh secret set DOCKER_HUB_USERNAME -b "pranavks343" -R pranavks343/CICDPROJECT
gh secret set DOCKER_HUB_TOKEN -b "YOUR_TOKEN_HERE" -R pranavks343/CICDPROJECT
gh secret set KUBE_CONFIG -b "$(cat ~/.kube/config | base64)" -R pranavks343/CICDPROJECT
```

---

## ✅ Verify Secrets Are Added

1. Go to: https://github.com/pranavks343/CICDPROJECT/settings/secrets/actions

2. You should see:
   - ✅ DOCKER_HUB_USERNAME
   - ✅ DOCKER_HUB_TOKEN
   - ✅ KUBE_CONFIG (optional)

---

## 🚀 Trigger the CI/CD Pipeline

Once secrets are added, trigger the pipeline:

### Option 1: Make a small change and push

```bash
cd /Users/pranavks/CICDENDSEMHACKATHON
echo "# CI/CD Pipeline Active" >> README.md
git add README.md
git commit -m "Trigger CI/CD pipeline"
git push
```

### Option 2: Manually trigger from GitHub

1. Go to: https://github.com/pranavks343/CICDPROJECT/actions
2. Click on **"CI/CD Pipeline"** workflow
3. Click **"Run workflow"** button
4. Select **"main"** branch
5. Click **"Run workflow"**

---

## 📊 Monitor the Pipeline

1. Go to: https://github.com/pranavks343/CICDPROJECT/actions

2. You'll see the workflow running with these stages:
   - ✅ Test Backend
   - ✅ Test Frontend
   - 🐳 Build and Push Backend Image
   - 🐳 Build and Push Frontend Image
   - ☸️ Deploy to Kubernetes (if KUBE_CONFIG is set)
   - 🤖 Run Ansible (if configured)

3. Click on any workflow run to see detailed logs

---

## 🔍 What Happens Next?

Once the pipeline runs successfully:

1. **Docker Images Built**: Backend and frontend images are built
2. **Images Pushed**: Images are pushed to Docker Hub
   - `pranavks343/healthcare-backend:latest`
   - `pranavks343/healthcare-frontend:latest`
3. **Kubernetes Deployment**: If KUBE_CONFIG is set, deploys to your cluster
4. **Notifications**: You'll get email notifications on success/failure

---

## 🐛 Troubleshooting

### Pipeline Fails at Docker Push

**Problem:** Cannot authenticate with Docker Hub

**Solution:**
1. Verify `DOCKER_HUB_TOKEN` is correct
2. Make sure token has Read, Write, Delete permissions
3. Check token hasn't expired

### Pipeline Fails at Kubernetes Deploy

**Problem:** Cannot connect to Kubernetes cluster

**Solution:**
1. Verify `KUBE_CONFIG` is base64 encoded correctly
2. Make sure your cluster is accessible from internet
3. Check if cluster credentials haven't expired

### No Workflow Runs Visible

**Problem:** GitHub Actions not triggering

**Solution:**
1. Check if `.github/workflows/ci-cd.yml` exists in your repo
2. Verify you pushed to `main` branch
3. Check Actions tab is enabled in repository settings

---

## 📚 Next Steps

After secrets are configured:

1. ✅ Verify pipeline runs successfully
2. ✅ Check Docker Hub for your images
3. ✅ Test the deployed application
4. ✅ Set up monitoring (Prometheus & Grafana)
5. ✅ Configure alerts for failures

---

## 🔐 Security Best Practices

- ✅ Never commit secrets to Git
- ✅ Rotate tokens regularly (every 90 days)
- ✅ Use minimal permissions for tokens
- ✅ Enable 2FA on Docker Hub and GitHub
- ✅ Monitor workflow logs for any exposed secrets
- ✅ Use environment-specific secrets for production

---

## 📞 Support

If you encounter issues:

1. Check GitHub Actions logs
2. Review this documentation
3. Check CICD-SETUP.md for more details
4. Verify all secrets are correctly added

---

## 🎉 Success Checklist

- [ ] Pushed code to GitHub
- [ ] Added DOCKER_HUB_USERNAME secret
- [ ] Added DOCKER_HUB_TOKEN secret
- [ ] (Optional) Added KUBE_CONFIG secret
- [ ] Triggered pipeline
- [ ] Pipeline ran successfully
- [ ] Images visible on Docker Hub
- [ ] Application deployed (if K8s configured)

---

**Repository URL:** https://github.com/pranavks343/CICDPROJECT

**Docker Hub Images:**
- https://hub.docker.com/r/pranavks343/healthcare-backend
- https://hub.docker.com/r/pranavks343/healthcare-frontend

**GitHub Actions:** https://github.com/pranavks343/CICDPROJECT/actions

