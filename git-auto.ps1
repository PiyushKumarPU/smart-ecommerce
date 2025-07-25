# git-auto.ps1

# Step 1: Pull latest changes
Write-Host "Running: git pull"
git pull

# Step 2: Stage all changes
Write-Host "Running: git add ."
git add .

# Step 3: Prompt for commit message
$commitMessage = Read-Host "Enter commit message"

# Step 4: Commit
Write-Host "Running: git commit -m `"$commitMessage`""
git commit -m "$commitMessage"

# Step 5: Push changes
Write-Host "Running: git push"
git push
