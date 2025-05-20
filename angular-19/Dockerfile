FROM node:20-alpine as builder

# Work directory
WORKDIR /app

# Install some dependencies
COPY package.json .
RUN npm install
COPY . .

# Build 
RUN npm run build

# Stage 2: Deploy stage
FROM nginx
COPY --from=builder /app/dist/angular-docker/browser /usr/share/nginx/html
