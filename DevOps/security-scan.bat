@echo off
echo Starting security scans for JDK 24 application...

if not exist security-reports mkdir security-reports

echo Scanning backend image...
trivy image devops-project_backend:latest > security-reports/backend-scan.txt

echo Scanning frontend image...
trivy image devops-project_frontend:latest > security-reports/frontend-scan.txt

echo Scanning prometheus image...
trivy image prom/prometheus:latest > security-reports/prometheus-scan.txt

echo Scanning grafana image...
trivy image grafana/grafana:latest > security-reports/grafana-scan.txt

echo.
echo Security scans completed. Check security-reports/ directory.
echo.
echo Summary:
type security-reports\backend-scan.txt | findstr "CRITICAL\|HIGH" | find /c /v ""
echo CRITICAL/HIGH vulnerabilities found in backend

pause