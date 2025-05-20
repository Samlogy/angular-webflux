CREATE TABLE IF NOT EXISTS tutorial (id SERIAL PRIMARY KEY, title VARCHAR(255), description VARCHAR(255), published BOOLEAN);


INSERT INTO tutorial (title, description, published) VALUES
  ('Introduction à PostgreSQL', 'Apprendre les bases de PostgreSQL', true),
  ('Spring WebFlux', 'Programmation réactive avec Spring WebFlux', true),
  ('Docker pour les développeurs', 'Conteneuriser vos applications Java', false),
  ('REST API avec Spring Boot', 'Créer des API RESTful en Java', true),
  ('Sécurité avec Spring Security', 'Protéger vos applications web', false),
  ('Tests unitaires avec JUnit', 'Écrire des tests efficaces en Java', true),
  ('CI/CD avec GitHub Actions', 'Automatiser vos déploiements', false),
  ('Bases de données relationnelles', 'Comprendre les modèles relationnels', true),
  ('Introduction à R2DBC', 'Accès réactif aux bases de données', true),
  ('Déploiement sur le cloud', 'Déployer avec Docker et Kubernetes', false);
