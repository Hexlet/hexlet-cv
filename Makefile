setup:
	# npm install
	./gradlew wrapper --gradle-version 8.14.1
	./gradlew build

run:
	./gradlew run

test:
	./gradlew test

deps-update:
	./gradlew refreshVersions

.PHONY: test
