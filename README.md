# QXRZ
Pronounced kew ecks ar zed

## Dates
- Final design doc **29 May**
- Initial prototype **5 June**
- Playable prototype **8 June**
- Final project **15 June**

## TODO

*Bonus*

### Graphics & UI
- Must include instruction/tutorial sequence
- *Advanced animations*
    - Players
    - Health packs and weaponry
    - Background animations (align with storyline)
- *View others' health*
- poop (Palindromes!)

### Networking
- Choose server from list
- *In-game text chat*

### Sounds
- Soundtrack/background music
- Action sounds (gun firing, movement, explosions)

### Gameplay
- Random events: object appearance
- Player load-out & switching
- Upgrades for weapons (and ammo?)
- Win/lose
- Player classes (selection at beginning; information on each)
- *Extra upgrades*
- *More levels*

## Authors
- Eli Baum
- Dan Chen
- Mathew Kirschbaum
- Teddy Maranets
- Sergey Savelyev
- Renoj Varghese
- Kelvin Zhang

---

# How to Use the Network Interface

Abstractly, *Network Managers* send and receive *Network Objects* to *Network Nodes*.

### Network Managers

Clients use ClientNetworkManager; Servers use ServerNetwork Manager (duh). These managers should be created at the beginning of the `main()` method as they are rather essential. Here's how to use them.

  - `sendObject()` Send an object. It is **very** important that **all** objects you want to send over the network implement `Serializable`.
  - `attachEventListener()` Use this function to attach your callback function to the method. If you don't do this, you will not be able to receive data.
 
Specific implementations:
  
1. Client Network Manager
   - `broadcastQuery()` Run this when you are looking for servers. Servers will respond with their information, which you can receive through your `dataReceived()` handler.
   - `connect()` Connect to a server. The address of the server can either be specified as an actual address (if broadcast query was used), or by a "hostname" and port (if entered in manaully).
   - For the client, `sendObject()` sends the given object to the server.
2. Server Network Manager
   - To construct a server, pass in a name and a port to listen on.
   - Here, `sendObject()` sends to *all connected clients**.
   - `removeClient()` Remove a client from the list. This function will tell the client it has been disconnected so it will stop pestering the server. Note that when a client disconnects, it will be automatically removed.
   - `getSocket()` will return the server's local socket.
   - `getClients()` returns a Set of all connected clients. You may need to keep this updated as clients join and leave.
 
### Chat Messages

Maybe this should be moved *outside* of net. But anyway...

If you send chat messages to the server, it will echo it back to all clients. **Including you**. So it might be a good idea to check (before displaying a chat message) if you weren't the one to send it.

- The constructor takes a string: the message. You can then send this using `sendObject()`.
- `getSender()` tells you who sent it.
- `getMessage()` tells you want they sent.

### Event Listeners

There are two listeners:

- `dataReceived()` will be called when data is received (duh). It tells you who sent it (for the client, this will always be a server), as well as the object that was sent.
- `newNode(NetworkNode c)` is only used on the Server, for now. It's called when a new client connects. We're trying to implement client side too when a new server is found.

### Network Nodes

Not sure how much you'll have to interact with these. Nodes are either clients, or the server. You may need the `getAddress()` method to get a Node's IP address.

### Server Info

When servers respond to a broadcast query, they'll send their `ServerInfo`. This simple class only includes their name (`getName()`). The server's network address can be accessed via the incoming packet (*we may need to help you with this*)