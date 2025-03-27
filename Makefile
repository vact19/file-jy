.PHONY: build push all help

DOCKER_USERNAME=calmdownman
PRJ_NAME=filejy-backend
VERSION:=$(shell git rev-parse --short HEAD)

build:
	docker buildx build --platform linux/amd64,linux/arm64 -t $(PRJ_NAME):$(VERSION) .

push:
	# Docker Hub 사용자명으로 태그 지정
	docker tag $(PRJ_NAME):$(VERSION) $(DOCKER_USERNAME)/$(PRJ_NAME):$(VERSION)
	docker tag $(PRJ_NAME):$(VERSION) $(DOCKER_USERNAME)/$(PRJ_NAME):latest
	# 푸시
	docker push $(DOCKER_USERNAME)/$(PRJ_NAME):$(VERSION)
	docker push $(DOCKER_USERNAME)/$(PRJ_NAME):latest

all: build push

help:
	@echo "사용 가능한 명령어:"
	@echo "  make build  - ARM64 && x86-64 이미지 빌드"
	@echo "  make push   - DockerHub 푸시"
	@echo "  make all    - build && push"