# Bloom Filter Demo

A Spring Boot application demonstrating the use of a Bloom Filter for efficient membership testing.

## What is a Bloom Filter?

A Bloom Filter is a space-efficient probabilistic data structure that is used to test whether an element is a member of a set. False positives are possible, but false negatives are not. In other words, a query returns either "possibly in set" or "definitely not in set".

## Features

- Web UI for interactive demonstration of the Bloom Filter
- RESTful API for programmatic interaction with the Bloom Filter
- Configurable Bloom Filter size and number of hash functions
- Persistent storage of the Bloom Filter state
- Efficient implementation using BitSet

## API Endpoints

### Check or Add a Name

```
POST /api/bloom-filter/check-name?name={name}
```

Checks if a name might exist in the Bloom Filter and adds it if it doesn't.

**Response:**
- If the name might already exist: "Name might already exist."
- If the name was added: "Name added."

### Clear the Bloom Filter

```
POST /api/bloom-filter/clear
```

Clears all entries from the Bloom Filter.

**Response:**
- "Bloom Filter cleared."

### Health Check

```
GET /api/bloom-filter/health
```

Checks if the service is running.

**Response:**
- "Bloom Filter service is running."

## Configuration

The following properties can be configured in `application.properties`:

```properties
# Size of the Bloom Filter bit array (default: 10000)
bloom.filter.size=10000

# Path to the file where the Bloom Filter bit array is stored (default: bitArray.txt)
bloom.filter.file.path=bitArray.txt

# Number of hash functions to use (default: 4)
bloom.filter.hash.functions=4
```

## Building and Running

### Prerequisites

- Java 17 or higher
- Gradle

### Build

```bash
./gradlew build
```

### Run

```bash
./gradlew bootRun
```

The application will be available at `http://localhost:8080`.

## Web UI

The application includes a web-based user interface for interacting with the Bloom Filter. The UI is built using Thymeleaf templates and Bootstrap for styling.

### Accessing the Web UI

After starting the application, navigate to `http://localhost:8080` in your web browser. You will be automatically redirected to the Bloom Filter UI.

### Features of the Web UI

- **Check/Add Names**: Enter a name in the input field and click the "Check/Add Name" button. The UI will display whether the name might already exist in the Bloom Filter or if it was added.
- **Clear Bloom Filter**: Click the "Clear Bloom Filter" button to remove all entries from the Bloom Filter.
- **Visual Feedback**: The UI provides visual feedback with different colors to indicate whether a name might already exist (yellow) or was added (green).

## Implementation Details

The Bloom Filter is implemented using Java's BitSet for efficient storage. It uses multiple hash functions to reduce the probability of false positives. The state of the Bloom Filter is persisted to a file, so it survives application restarts.

## Dependencies

The application uses the following dependencies:

- Spring Boot Web: For creating the RESTful API
- Spring Boot Thymeleaf: For server-side HTML templating
- Bootstrap (via WebJars): For styling the web UI
- jQuery (via WebJars): For JavaScript functionality in the web UI
