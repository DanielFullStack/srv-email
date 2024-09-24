#!/bin/bash

# Rodar a análise de qualidade do código
echo "Executando análise de qualidade do código com Maven..."

# 1. Rodar mvn clean install
mvn clean install

# 2. Rodar análise de vulnerabilidade com Dependency Check
mvn dependency-check:aggregate -PsonarReports

# 3. Executar a análise do SonarQube
mvn clean verify sonar:sonar \
  -Dsonar.projectKey=srv-email \
  -Dsonar.host.url=http://sonarqube:9000 \
  -Dsonar.login=sqp_ca71748542583fd0709ab1b2c1d7bd50b9b4ad79

echo "Análise de qualidade de código concluída com sucesso!"
