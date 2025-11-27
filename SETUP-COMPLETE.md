# 🔐 GitHub Secrets Setup - Your Credentials

## ✅ Your Docker Hub Information

**Docker Hub Username:** `pranavks28`  
**Docker Hub Password:** `PRANAVk@28`

---

## 📝 STEP-BY-STEP: Add GitHub Secrets

### Step 1: Login to Docker Hub and Create Access Token

1. **Go to Docker Hub:** https://hub.docker.com/settings/security

2. **Login with your credentials:**
   - Username: `pranavks28`
   - Password: `PRANAVk@28`

3. **Create Access Token:**
   - Click **"New Access Token"**
   - Description: `github-actions-cicd`
   - Access permissions: **Read, Write, Delete**
   - Click **"Generate"**
   - **COPY THE TOKEN** - it looks like: `dckr_pat_xxxxxxxxxxxxx`
   - ⚠️ **IMPORTANT:** Save it somewhere - you won't see it again!

---

### Step 2: Add Secrets to GitHub

1. **Login to GitHub:** https://github.com/login

2. **Go to Your Repository Secrets:**
   https://github.com/pranavks343/CICDPROJECT/settings/secrets/actions

3. **Add First Secret - DOCKER_HUB_USERNAME:**
   - Click **"New repository secret"**
   - Name: `DOCKER_HUB_USERNAME`
   - Secret: `pranavks28`
   - Click **"Add secret"**
   - ✅ Secret 1 added!

4. **Add Second Secret - DOCKER_HUB_TOKEN:**
   - Click **"New repository secret"**
   - Name: `DOCKER_HUB_TOKEN`
   - Secret: `paste the token you copied from Docker Hub`
   - Click **"Add secret"**
   - ✅ Secret 2 added!

---

### Step 3: Update Configuration Files

The Docker Hub username needs to be updated in several files. I'll do this automatically.

---

### Step 4: Trigger the CI/CD Pipeline

After adding both secrets to GitHub:

```bash
cd /Users/pranavks/CICDENDSEMHACKATHON
git pull  # Get latest changes
echo "" >> README.md
git add README.md
git commit -m "Trigger CI/CD pipeline"
git push
```

Then monitor the pipeline:
- **GitHub Actions:** https://github.com/pranavks343/CICDPROJECT/actions

---

## 🎯 Expected Results

Once the pipeline runs:

1. **Tests Run** - Backend and frontend tests execute
2. **Docker Images Build** - Multi-stage builds complete
3. **Images Push to Docker Hub:**
   - `pranavks28/healthcare-backend:latest`
   - `pranavks28/healthcare-frontend:latest`
4. **Deployment** - (If Kubernetes configured)

---

## 🔍 Verify on Docker Hub

After pipeline completes, check your images:
- Backend: https://hub.docker.com/r/pranavks28/healthcare-backend
- Frontend: https://hub.docker.com/r/pranavks28/healthcare-frontend

---

## ✅ Checklist

- [ ] Login to Docker Hub (https://hub.docker.com)
- [ ] Create Access Token
- [ ] Copy the token
- [ ] Login to GitHub
- [ ] Go to repository secrets page
- [ ] Add DOCKER_HUB_USERNAME = `pranavks28`
- [ ] Add DOCKER_HUB_TOKEN = `your_copied_token`
- [ ] Wait for me to update config files
- [ ] Trigger pipeline with git push
- [ ] Monitor at GitHub Actions
- [ ] Verify images on Docker Hub

---

## 🆘 Troubleshooting

### Can't Login to Docker Hub?
- Username: `pranavks28`
- Password: `PRANAVk@28`
- URL: https://hub.docker.com/login

### Can't Find Secrets Page?
Direct link: https://github.com/pranavks343/CICDPROJECT/settings/secrets/actions
(You must be logged in and have admin access)

### Pipeline Fails?
- Check both secrets are added correctly
- Verify Docker Hub token hasn't expired
- Check GitHub Actions logs for errors

---

## 📞 Next Steps

After adding the secrets:
1. I'll update all config files with your Docker Hub username
2. Commit and push the changes
3. The CI/CD pipeline will automatically trigger
4. Your images will be built and pushed to Docker Hub
5. Application will be deployed (if Kubernetes is configured)

---

**Let me know once you've added both secrets to GitHub, and I'll proceed with updating the configuration files!**

