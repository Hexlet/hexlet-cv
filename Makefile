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

ci-setup:
	echo "Updating packages..."
	sudo apt-get update -y

	echo "Setting up Java..."
	sudo apt-get install -y openjdk-17-jdk

    # Установить Maven/Gradle (если проект на Gradle, можно пропустить)
	echo "Setting up Gradle..."
	sudo apt-get install -y gradle

.PHONY: test
