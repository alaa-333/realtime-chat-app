# Real-Time Chat Application

A full-stack real-time messaging platform built with Spring Boot and WebSocket (STOMP protocol) that enables instant bidirectional communication between multiple users.

## Features

- **Real-Time Messaging**: Instant message delivery using WebSocket and STOMP protocol
- **Public Chat Rooms**: Broadcast messages to all connected users
- **Private Direct Messaging**: Send private messages to specific users
- **User Presence Tracking**: See who's online and track join/leave events
- **Typing Indicators**: Real-time typing status for active users
- **Auto-Reconnection**: Automatic reconnection with exponential backoff on network interruptions
- **Modern UI**: Responsive dark-themed interface with smooth animations
- **Message History**: Persistent message display with timestamps and avatars
- **Unread Message Badges**: Visual indicators for unread direct messages

## Technology Stack

### Backend
- **Spring Boot 3.x** - Application framework
- **Spring WebSocket** - WebSocket support
- **STOMP Protocol** - Messaging protocol over WebSocket
- **SockJS** - WebSocket fallback for older browsers
- **Lombok** - Reduce boilerplate code
- **Maven** - Dependency management

### Frontend
- **Vanilla JavaScript** - No framework dependencies
- **SockJS Client** - WebSocket client with fallback
- **STOMP.js** - STOMP protocol client
- **HTML5 & CSS3** - Modern web standards

## Project Structure

```
websocket/
├── src/
│   ├── main/
│   │   ├── java/com/example/websocket/
│   │   │   ├── config/
│   │   │   │   └── WebsocketConfig.java          # WebSocket configuration
│   │   │   ├── controller/
│   │   │   │   └── ChatController.java           # Message handling endpoints
│   │   │   ├── dto/
│   │   │   │   ├── ChatMessage.java              # Public message model
│   │   │   │   ├── ChatNotification.java         # Public response model
│   │   │   │   ├── PrivateMessage.java           # Private message model
│   │   │   │   └── PrivateNotification.java      # Private response model
│   │   │   └── WebsocketApplication.java         # Main application class
│   │   └── resources/
│   │       ├── static/
│   │       │   └── index.html                    # Frontend UI
│   │       └── application.yaml                  # Application config
│   └── test/
└── pom.xml                                        # Maven dependencies
```

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6+
- Modern web browser (Chrome, Firefox, Safari, Edge)

### Installation

1. Clone the repository:
```bash
git clone https://github.com/yourusername/wasla-realtime-chat.git
cd wasla-realtime-chat
```

2. Build the project:
```bash
mvn clean install
```

3. Run the application:
```bash
mvn spring-boot:run
```

4. Open your browser and navigate to:
```
http://localhost:8080
```

### Configuration

The application runs on port 8080 by default. You can change this in `application.yaml`:

```yaml
server:
  port: 8080
```

## Usage

1. **Connect to Chat**:
   - Enter your username
   - Server URL is pre-filled as `http://localhost:8080/ws`
   - Click "Connect"

2. **Send Public Messages**:
   - Type your message in the input box
   - Press Enter or click the send button
   - Message broadcasts to all connected users

3. **Send Direct Messages**:
   - Click the DM icon (💬) in the input area
   - Enter the recipient's username
   - Type and send your private message
   - Only you and the recipient will see it

4. **Switch Channels**:
   - Click "# general" for public chat
   - Click "● direct messages" for private conversations

## API Endpoints

### WebSocket Endpoints

| Endpoint | Type | Description |
|----------|------|-------------|
| `/ws` | Connection | WebSocket handshake endpoint (with SockJS fallback) |

### STOMP Message Mappings

| Destination | Type | Description |
|-------------|------|-------------|
| `/app/chat.send` | Send | Send public message to all users |
| `/app/chat.join` | Send | Notify users when someone joins |
| `/app/chat.leave` | Send | Notify users when someone leaves |
| `/app/chat.typing` | Send | Broadcast typing indicator |
| `/app/chat.private` | Send | Send private message to specific user |
| `/topic/public` | Subscribe | Receive public messages and notifications |
| `/user/queue/messages` | Subscribe | Receive private messages |

## Message Types

### ChatMessage (Request)
```json
{
  "senderName": "Alice",
  "content": "Hello everyone!",
  "messageType": "CHAT"
}
```

### ChatNotification (Response)
```json
{
  "senderName": "Alice",
  "content": "Hello everyone!",
  "type": "CHAT",
  "timestamp": "2024-01-15T10:30:00"
}
```

### PrivateMessage (Request)
```json
{
  "senderName": "Alice",
  "recipientName": "Bob",
  "content": "Hi Bob!"
}
```

### PrivateNotification (Response)
```json
{
  "senderName": "Alice",
  "recipientName": "Bob",
  "content": "Hi Bob!",
  "type": "CHAT",
  "timestamp": "2024-01-15T10:30:00",
  "isRead": false
}
```

## Architecture

### Message Flow

1. **Public Messages**:
   ```
   Client → /app/chat.send → ChatController → /topic/public → All Clients
   ```

2. **Private Messages**:
   ```
   Client → /app/chat.private → ChatController → /user/{username}/queue/messages → Specific Client
   ```

3. **User Events**:
   ```
   Client → /app/chat.join → ChatController → /topic/public → All Clients
   ```

### WebSocket Configuration

- **Application Prefix**: `/app` - All client messages are prefixed with this
- **Broker Destinations**: `/topic` (broadcast), `/queue` (point-to-point)
- **STOMP Endpoint**: `/ws` - WebSocket connection endpoint
- **SockJS Fallback**: Enabled for browser compatibility

## Features in Detail

### Auto-Reconnection
- Exponential backoff strategy (1s, 2s, 4s, 8s, 16s, 30s max)
- Automatic retry on connection drops
- No reconnection on intentional disconnects
- Visual connection status indicator

### Typing Indicators
- Debounced typing events (300ms)
- Broadcast to all users in public chat
- Auto-hide after 3 seconds of inactivity

### User Presence
- Real-time online member count
- Join/leave notifications
- Color-coded user avatars

## Development

### Running Tests
```bash
mvn test
```

### Building for Production
```bash
mvn clean package
java -jar target/websocket-0.0.1-SNAPSHOT.jar
```

### Hot Reload (Development)
```bash
mvn spring-boot:run
```

## Troubleshooting

### Connection Issues
- Ensure the server is running on port 8080
- Check firewall settings
- Verify WebSocket support in your browser

### Messages Not Appearing
- Check browser console for errors
- Verify you're subscribed to the correct topics
- Ensure username is set correctly

### SockJS Fallback
If WebSocket is blocked, SockJS automatically falls back to:
1. XHR streaming
2. XHR polling
3. IFrame-based transports


