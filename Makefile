setup:
	# npm install
	./gradlew wrapper --gradle-version 8.14.1
	./gradlew build

run:
	./gradlew run

test:
	./gradlew test

build:
	./gradlew build

clean:
	./gradlew clean

deps-update:
	./gradlew refreshVersions

check:
	./gradlew check

dev:
	./gradlew bootRun --args='--spring.profiles.active=dev'

prod:
	./gradlew bootRun --args='--spring.profiles.active=prod'

debug:
	./gradlew bootRun --debug-jvm

.PHONY: test
