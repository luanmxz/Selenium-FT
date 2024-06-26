# Selenium-FT

## Overview

Selenium-FT is a Selenium-based framework for functional testing of web applications. It provides a structured environment to automate web interactions and validate functionality across different browsers and platforms.

## Table of Contents

- [Getting Started](#getting-started)
- [Installation](#installation)
  - [Windows](#windows)
  - [Linux](#linux)
- [Usage](#usage)
- [Configuration](#configuration)
- [Development](#development)
- [Contributing](#contributing)
- [License](#license)

## Getting Started

To get started with Selenium-FT, clone the repository and ensure you have the necessary prerequisites installed.

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- Maven
- Firefox Browser
- Geckodriver

### Cloning the Repository

```bash
git clone https://github.com/luanmxz/Selenium-FT.git
cd Selenium-FT
```

## Installation

### Windows

1. **Install Firefox**: [Download Firefox](https://www.mozilla.org/en-US/firefox/new/)
2. **Install Geckodriver**: [Download Geckodriver](https://github.com/mozilla/geckodriver/releases)
   - Extract and add Geckodriver to the system PATH.
3. **Install Java**: [Download Java](https://www.oracle.com/java/technologies/javase-downloads.html)
4. **Build the Project**:
   ```bash
   mvn clean package
   ```

### Linux

#### Configuring the Virtual Machine

1. **Download VirtualBox**: [Download VirtualBox](https://www.virtualbox.org/)
2. **Download Ubuntu Server LTS (No GUI)**: [Download Ubuntu Server](https://ubuntu.com/download/server)
3. **Create a VM in VirtualBox**:
   - Set up user and password, select the downloaded Ubuntu ISO.
4. **Clone the Repository**:
   ```bash
   git clone https://github.com/luanmxz/Selenium-FT.git
   cd Selenium-FT
   ```
5. **Build the Project**:
   ```bash
   mvn clean package
   ```

#### Installing Required Software on Linux

1. **Install Firefox and Geckodriver**:
   ```bash
   sudo apt-get update
   sudo apt-get install firefox
   ```
2. **Install Java**:
   ```bash
   sudo apt update
   sudo apt install openjdk-17-jdk -y
   ```

## Usage

Run the built JAR file with the required parameters.

### Example Command

```bash
java -jar target/Selenium-FT.jar <AGENDA> <TIPO_PROCESSO> <IS_HEADLESS> <PATH_GECKODRIVER>
```

- **Parameters**:
  - `<AGENDA>`: Number of the agenda.
  - `<TIPO_PROCESSO>`: Type of the process.
  - `<IS_HEADLESS>`: TRUE or FALSE to indicate headless execution.
  - `<PATH_GECKODRIVER>`: Path to the Geckodriver (optional).

### Example Execution

```bash
java -jar target/Selenium-FT.jar 2276376 EXTRACAO_BILHETES TRUE /snap/bin/geckodriver
```

## Configuration

Configure the `.env` file in the project's root directory.

### .env File Example

```ini
API_USERNAME=<API_USERNAME>
API_PASSWORD=<API_PASSWORD>
API_DADOS_PROCESSAMENTO_URL=<URL>
PATH_GECKODRIVER=<GECKODRIVER_PATH>
DEFAULT_WAIT_TIMEOUT=30
DEFAULT_WAIT_POLLING=500
OTPAUTH_MIGRATION_URL=<QR_CODE_URL>
```

## Development

### Setting Up the Development Environment

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/luanmxz/Selenium-FT.git
   cd Selenium-FT
   ```
2. **Install Dependencies**:
   ```bash
   mvn install
   ```

## Contributing

Contributions are welcome! Please follow these steps to contribute:

1. Fork the repository.
2. Create a new branch (`git checkout -b feature-branch`).
3. Commit your changes (`git commit -m 'Add new feature'`).
4. Push to the branch (`git push origin feature-branch`).
5. Open a pull request.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
