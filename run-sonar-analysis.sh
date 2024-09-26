#!/bin/bash

# Rodar a análise de qualidade do código
echo "Executando análise de qualidade do código com Maven..."

# 1. Rodar mvn clean install
mvn clean install

# 2. Rodar análise de vulnerabilidade com Dependency Check
mvn dependency-check:aggregate -PsonarReports

# 3. Executar a análise do SonarQube
mvn clean verify sonar:sonar \
  -Dsonar.projectKey=srv_email \
  -Dsonar.host.url=http://sonarqube:9000 \
  -Dsonar.login=sqp_8f434f195c0fc33dfedac1260c193252d5d8a0e5

echo "Análise de qualidade de código concluída com sucesso!"

# Certifique-se de que o script termine corretamente
exit 0