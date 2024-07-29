dev:
		./gradlew bootRun
clean:
		./gradlew clean
build:
		./gradlew clean build
install:
		./gradlew installBootDist
lint:
		./gradlew checkstyleMain
test:
		./gradlew test

report:
	./gradlew jacocoTestReport

.PHONY: build