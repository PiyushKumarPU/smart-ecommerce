# PowerShell Script: run-springboot.ps1

# Change to the project root (optional)
cd "smart-ecommerce"

# Get all subdirectories with pom.xml (Maven modules)
$modules = Get-ChildItem -Directory | Where-Object {
    Test-Path "$($_.FullName)\pom.xml"
} | Select-Object -ExpandProperty Name

# Prompt user to select a project
Write-Host "Select a Spring Boot module to run:" -ForegroundColor Cyan
for ($i = 0; $i -lt $modules.Count; $i++) {
    Write-Host "$($i + 1). $($modules[$i])"
}

# Read user selection
$selection = Read-Host "Enter the number of the project to run"
$index = [int]$selection - 1

# Validate selection
if ($index -lt 0 -or $index -ge $modules.Count) {
    Write-Host "Invalid selection. Exiting." -ForegroundColor Red
    exit 1
}

# Change directory to selected module
$selectedModule = $modules[$index]
Set-Location "$selectedModule"

# Run Spring Boot app
Write-Host "`nRunning Spring Boot module: $selectedModule" -ForegroundColor Green
mvn spring-boot:run
