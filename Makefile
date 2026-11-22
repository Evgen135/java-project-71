build:
	.\gradlew clean build

install:
	.\gradlew clean install

run-dist:
	.\build\install\app\bin\app.bat


run-dist-h:
	.\build\install\app\bin\app.bat -h

run-dist-V:
	.\build\install\app\bin\app.bat -V

run-gendiff:
	.\build\install\app\bin\app.bat filesExample/file1.json filesExample/file2.json

format:
	./gradlew spotlessApply

.PHONY: build install