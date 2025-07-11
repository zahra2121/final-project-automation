## Automation Test Framework

Framework pengujian otomatis untuk **Web UI (Demoblaze) & API (DummyAPI.io)** menggunakan **Java, Gradle, Selenium, RestAssured, Cucumber, dan GitHub Actions**.

## Fitur
1. Pengujian **Web UI & API** dalam satu repository.  
2. Menggunakan **Cucumber (Gherkin)** dan **Page Object Model (POM)**.  
3. **Gradle Tasks** untuk menjalankan pengujian berdasarkan tag.  
4. **GitHub Actions** untuk otomatisasi pengujian.  
5. **Laporan HTML & JSON**.

## Tools & Library

| Tool/Library | Versi  |
|-------------|--------|
| Java        | 24     |
| Gradle      | Latest |
| Cucumber    | Latest |
| Selenium WebDriver | Latest |
| RestAssured | Latest |
| JUnit       | 5+     |

## Cara Menjalankan Test

1️⃣ **Clone Repository**
```sh
git clone https://github.com/username/repository.git
cd repository
2️⃣ Menjalankan API Tests 
```sh
./gradlew test -Dcucumber.filter.tags="@api" atau ./gradlew clean testApi
3️⃣ Menjalankan Web UI Tests 
```sh
./gradlew test -Dcucumber.filter.tags="@web" atau ./gradlew clean testWeb
4️⃣ Menjalankan Semua Test  
```sh
./gradlew test

Struktur Folder

📦 finalproject-automation-test
 ┣ 📂 src
 ┃ ┣ 📂 main/java
 ┃ ┣ 📂 test/java
 ┃ ┃ ┣ 📂 api  # Pengujian API
 ┃ ┃ ┣ 📂 runners  # Cucumber Test API dan WEB
 ┃ ┃ ┣ 📂 web  # Pengujian Web UI
 ┃ ┃ ┃ ┣ 📂 pages  # Page Object Model
 ┃ ┃ ┃ ┗ 📂 stepdefinitions  # Step Definition Cucumber
 ┃ ┃ ┣ 📂 resources/features  # File .feature untuk skenario pengujian
 ┣ 📂 .github/workflows  # Konfigurasi GitHub Actions
 ┣ 📜 README.md
 ┗ 📜 build.gradle
