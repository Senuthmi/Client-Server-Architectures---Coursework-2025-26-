# Coursework Report – Answers to Questions

## Part 1: Service Architecture & Setup

### 1. . In your report, explain the default lifecycle of a JAX-RS Resource class. Is a new instance instantiated for every incoming request, or does the runtime treat it as a singleton? Elaborate on how this architectural decision impacts the way you manage andsynchronize your in-memory data structures (maps/lists) to prevent data loss or race conditions

The lifecycle of a JAX-RS resource class is, by default, per-request scoped. This means that a new instance of the resource class is created for every incoming HTTP request. This design ensures thread safety at the object level, since no two requests share the same instance.

However, in this coursework, shared in-memory data structures such as `HashMap` used for storing rooms and sensors are declared as static fields. This makes them shared across all requests and all instances of the resource class. As a result, this introduces potential race conditions and data inconsistency issues when multiple requests modify the same data simultaneously.

To prevent this in production systems, thread-safe alternatives such as `ConcurrentHashMap` or synchronization mechanisms would be required.

---

### 2. Why is the provision of ”Hypermedia” (links and navigation within responses)considered a hallmark of advanced RESTful design (HATEOAS)? How does this approach benefit client developers compared to static documentation?

Hypermedia or HATEOAS means that API responses include links to resources. This allows clients to dynamically navigate the API. This is because it reduces the need for clients to rely on hardcoded URLs or external documentation. Instead, clients can discover actions directly from responses. This approach is good for flexibility, maintainability and scalability. Changes to endpoints can be communicated through links without breaking client applications.

This approach improves:
- **Flexibility** – API changes don’t easily break clients  
- **Discoverability** – Clients learn available actions from responses  
- **Maintainability** – Reduces dependency on static documentation  

Compared to traditional REST APIs, HATEOAS enables a more self-descriptive and evolvable system architecture.

---

## Part 2: Room Management

### 1. When returning a list of rooms, what are the implications of returning only IDs versus returning the full room objects? Consider network bandwidth and client side processing

Returning only IDs reduces network bandwidth usage. This is because less data is transmitted over the network. However, it requires the client to make requests to retrieve full details.
On the other hand, returning full room objects increases payload size. It provides complete information in a single request. This improves efficiency for the client.
So the choice depends on the use case. Do we want responses or fewer API calls? The Room Management system should be designed with this in mind.


---

### 2. Is the DELETE operation idempotent in your implementation? Provide a detailed justification by describing what happens if a client mistakenly sends the exact same DELETE request for a room multiple times.

Yes, the DELETE operation is idempotent.
If a client sends the DELETE request multiple times, the first request deletes the room successfully. Subsequent requests will return "Room not found".
Even though the response may differ, the final state of the Room Management system remains unchanged after the deletion. This satisfies the definition of idempotency.
 
---

## Part 3: Sensor Operations & Linking

### 1. We explicitly use the @Consumes (MediaType.APPLICATION_JSON) annotation on the POST method. Explain the technical consequences if a client attempts to send data in a different format, such as text/plain or application/xml. How does JAX-RS handle this mismatch?

The @Consumes(MediaType.APPLICATION_JSON) annotation specifies that the API endpoint only accepts requests with a Content-Type of application/json.

If a client sends data in an unsupported format such as text/plain or application/xml, the JAX-RS runtime will not attempt to process the request. Instead, it will automatically return an HTTP 415 (Unsupported Media Type) response.

This behavior ensures:

**Data consistency**, as the server processes only expected formats
**Input validation**, preventing malformed or incompatible data
**Clear communication** to clients about acceptable request formats

Additionally, JAX-RS uses message body readers to convert incoming JSON into Java objects. If the format does not match, deserialization cannot occur, reinforcing the importance of specifying the correct media type.

---

### 2. You implemented this filtering using @QueryParam. Contrast this with an alternative design where the type is part of the URL path (e.g.,/api/vl/sensors/type/CO2). Why is the query parameter approach generally considered superior for filtering and searching collections?

Using @QueryParam for filtering is more appropriate. For example /sensors?type=CO2 is a way to filter sensors.  This is because filtering does not represent a distinct resource, but rather a subset of a collection.
In contrast, using a path like /sensors/type/CO2 suggests a resource structure. This is less flexible. Query parameters allow multiple filters to be combined easily. They are better suited for search and filtering operations.
Advantages of query parameters include:

- Support for multiple filtering criteria
- Improved readability and flexibility
- Alignment with standard REST conventions

Therefore, query parameters provide a more scalable and semantically accurate approach for filtering and searching operations.

---

## Part 4: Sub-Resources

### 1. Discuss the architectural benefits of the Sub-Resource Locator pattern. How does delegating logic to separate classes help manage complexity in large APIs compared to defining every nested path (e.g., sensors/{id}/readings/{rid}) in one massive con-troller class?

The Sub-Resource Locator pattern allows a resource class to delegate handling of nested paths to separate classes, rather than managing all endpoints within a single controller.

For example, instead of handling /sensors/{id}/readings/{rid} in one large class, the main resource can delegate /readings to a dedicated sub-resource class.

This approach offers several benefits:

- Separation of concerns: Each class is responsible for a specific part of the API
- Improved readability: Smaller, focused classes are easier to understand
- Maintainability: Changes to one part of the API do not affect unrelated components
- Scalability: The API structure can grow without becoming overly complex

In contrast, a monolithic controller handling all routes can become difficult to manage, debug, and extend, especially as the application grows.

---

## Part 5: Error Handling & Logging

### 1.Why is HTTP 422 often considered more semantically accurate than a standard 404 when the issue is a missing reference inside a valid JSON payload?

HTTP 422 Unprocessable Entity is more semantically correct than 404 when the request structure is valid but the referenced data is invalid.
For example, when a sensor refers to a non-existent room ID, the endpoint exists, but the payload contains invalid logical data.
Therefore:

- **404** → endpoint not found  
- **422** → request understood but logically invalid  

---

### 2. . From a cybersecurity standpoint, explain the risks associated with exposing internal Java stack traces to external API consumers. What specific information could an attacker gather from such a trace?

Exposing stack traces can reveal internal details. These include class names and package structure, file paths, frameworks and libraries used.
Attackers can use this information to identify vulnerabilities. They can target parts of the system.
Therefore, generic error messages should be returned instead. This is a practice for security.

---

### 3. Why is it advantageous to use JAX-RS filters for cross-cutting concerns like logging, rather than manually inserting Logger.info() statements inside every single resource method?

Using JAX-RS filters for logging is advantageous. It centralizes logging logic in one place.
This avoids code in every resource method. It ensures consistency across all requests and responses.
It also makes the system easier to maintain and extend. The Error Handling and Logging system should be designed with this in mind.

