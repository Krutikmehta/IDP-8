import socket
import json
from _thread import * 
import threading
 
print_lock = threading.Lock()

def threaded(clientsocket,dict,address):
    while True:
        msg = clientsocket.recv(1024)
        if msg!='':
            jsonObject = json.loads(msg)
            
            # print the received message
            print_lock.acquire()
            for key in jsonObject:
                value = jsonObject[key]
                print(key + " : " + value)
            print_lock.release()
            
            # if is destination ip is specified then forward to destination ip else echo back
            if "ip" in jsonObject:
                if jsonObject["ip"] in dict:
                    # new socket is the destination clientsocket
                    clientsocket = dict[jsonObject["ip"]]

                    # change ip in the message to be of the sender
                    jsonObject["ip"] = address[0]
                    msg = (json.dumps(jsonObject) + "\n").encode()

                else:
                    print_lock.acquire()
                    print("ip not connected")
                    print_lock.release()
                    continue
            clientsocket.send(msg)

HOST = ''  # Standard loopback interface address (localhost)

PORT = 3389        # Port to listen on (non-privileged ports are > 1023)

# socket creation and bind to port
s =  socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
s.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEPORT, 1)
s.bind((HOST, PORT))
s.listen(5)
print("started")

# dictionary to store all the receiving connections
dict = {}

while True:
    # accept clients
    clientsocket, address = s.accept()
    dict[address[0]] = clientsocket

    print_lock.acquire()
    print(f"Connection from {address} has been established.")
    print_lock.release()

    #serve the client on a separate thread
    start_new_thread(threaded, (clientsocket,dict,address))
    
       