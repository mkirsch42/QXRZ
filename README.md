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

1. Network Managers

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